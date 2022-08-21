package com.imagine.etagcacher.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ETagCacher {

    // Accept a parameter to specify the cache expiry time. Accept in minutes
    int expiry() default 60;

    boolean isWeakETag() default true;
}
