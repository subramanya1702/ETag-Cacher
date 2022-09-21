package com.imagine.etagcacher.interceptor;

import com.imagine.etagcacher.annotation.ETagCacher;
import com.imagine.etagcacher.cache.ETagCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ETagHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    private ETagCache eTagCache;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) {
        if ((handler instanceof HandlerMethod)) {
            final HandlerMethod handlerMethod = (HandlerMethod) handler;

            if (handlerMethod.hasMethodAnnotation(ETagCacher.class)) {
                final String eTag = eTagCache.get(request.getRequestURI(), String.valueOf(handlerMethod.getMethod().hashCode()));

                if (eTag != null && request.getHeader(HttpHeaders.IF_NONE_MATCH) != null &&
                        request.getHeader(HttpHeaders.IF_NONE_MATCH).equals(eTag)) {
                    response.setStatus(HttpStatus.NOT_MODIFIED.value());
                    response.addHeader(HttpHeaders.ETAG, eTag);
                    response.setContentLength(0);
                    return false;
                }
            }
        }
        return true;
    }

}
