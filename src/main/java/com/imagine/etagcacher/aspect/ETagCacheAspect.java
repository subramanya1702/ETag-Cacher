package com.imagine.etagcacher.aspect;

import com.imagine.etagcacher.annotation.ETagCacher;
import com.imagine.etagcacher.cache.ETagCache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ETagCacheAspect {

    private final ETagCache eTagCache;

    @Autowired
    public ETagCacheAspect(final ETagCache eTagCache) {
        this.eTagCache = eTagCache;
    }

    @Around("execution(* com.imagine.etagcacher..*(..)) && @annotation(eTagCacher)")
    public Object eTagCacher(final ProceedingJoinPoint proceedingJoinPoint,
                             final ETagCacher eTagCacher) throws Throwable {
        final MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        final String eTag = eTagCache.get("TemporaryCacheName", methodSignature.getMethod().getName());

        HttpHeaders httpHeaders = new HttpHeaders();
        for (Object object : proceedingJoinPoint.getArgs()) {
            if (object instanceof HttpHeaders) {
                httpHeaders = (HttpHeaders) object;
                break;
            }
        }

        if (httpHeaders.getIfNoneMatch().contains(eTag)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_MODIFIED)
                    .eTag(eTag)
                    .body(null);
        }

        final Object response = proceedingJoinPoint.proceed();
        log.info("Test");

        return response;
    }
}

