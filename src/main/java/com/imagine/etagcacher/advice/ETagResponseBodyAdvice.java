package com.imagine.etagcacher.advice;

import com.imagine.etagcacher.annotation.ETagCacher;
import com.imagine.etagcacher.annotation.EnableETagCacher;
import com.imagine.etagcacher.cache.ETagCache;
import com.imagine.etagcacher.generator.ETagGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Objects;

@ControllerAdvice(annotations = {EnableETagCacher.class})
public class ETagResponseBodyAdvice extends AbstractMappingJacksonResponseBodyAdvice {

    @Autowired
    private ETagCache eTagCache;

    @Override
    protected void beforeBodyWriteInternal(final MappingJacksonValue bodyContainer,
                                           final MediaType contentType,
                                           final MethodParameter returnType,
                                           final ServerHttpRequest request,
                                           final ServerHttpResponse response) {
        if (Objects.isNull(bodyContainer.getValue())) {
            return;
        }

        try {
            if (!returnType.hasMethodAnnotation(ETagCacher.class)) {
                return;
            }

            final Annotation[] annotations = Objects.requireNonNull(returnType.getMethod()).getAnnotations();
            ETagCacher eTagCacher = null;

            for (final Annotation annotation : annotations) {
                if (annotation.annotationType().getName().equals(ETagCacher.class.getName())) {
                    eTagCacher = (ETagCacher) annotation;
                    break;
                }
            }

            if (eTagCacher == null) {
                return;
            }

            final String eTag = ETagGenerator.generateETag(bodyContainer.getValue(), eTagCacher.isWeakETag());

            this.eTagCache.put(request.getURI().getPath(), String.valueOf(returnType.getMethod().hashCode()), eTag, eTagCacher.expiry());

            response.getHeaders().add(HttpHeaders.ETAG, eTag);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
