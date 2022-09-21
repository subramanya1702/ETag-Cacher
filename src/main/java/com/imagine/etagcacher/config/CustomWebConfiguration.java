package com.imagine.etagcacher.config;

import com.imagine.etagcacher.interceptor.ETagHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomWebConfiguration implements WebMvcConfigurer {

    @Bean
    public ETagHandlerInterceptor eTagHandlerInterceptor() {
        return new ETagHandlerInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(eTagHandlerInterceptor());
    }
}
