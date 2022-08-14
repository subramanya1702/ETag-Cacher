package com.imagine.etagcacher.controller;

import com.imagine.etagcacher.annotation.ETagCacher;
import com.imagine.etagcacher.annotation.EnableETagCacher;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@EnableETagCacher
@RestController
@RequestMapping("/v1/one-piece")
public class OnePieceController {

    @GetMapping("/yonkos")
    @ETagCacher(cacheExpiry = 1440)
    public Object getYonkosList(@RequestHeader HttpHeaders httpHeaders) {
        return List.of("Shanks", "Blackbeard", "Luffy", "Kid");
    }
}
