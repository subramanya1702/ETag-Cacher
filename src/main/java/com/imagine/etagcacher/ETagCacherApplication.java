package com.imagine.etagcacher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ETagCacherApplication {

    public static void main(String[] args) {
        SpringApplication.run(ETagCacherApplication.class, args);
    }

}
