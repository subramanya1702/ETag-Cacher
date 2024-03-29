package com.imagine.etagcacher.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imagine.etagcacher.TestDto;
import com.imagine.etagcacher.annotation.ETagCacher;
import com.imagine.etagcacher.annotation.EnableETagCacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@EnableETagCacher
@RestController
@RequestMapping("/v1/one-piece")
public class OnePieceController {

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/yonkos")
    @ETagCacher(expiry = 1440)
    public Object getYonkosList(@RequestHeader HttpHeaders httpHeaders) {
        return List.of("Shanks", "Blackbeard", "Luffy", "Kid");
    }

    @GetMapping("/testJson/{testLength}")
    @ETagCacher(expiry = 1440, isWeakETag = true)
    public List<TestDto> getTestJson(@PathVariable("testLength") int testLength) throws IOException {
        final InputStream inputStream = new ClassPathResource("test.json").getInputStream();
        return this.objectMapper.readValue(inputStream, new TypeReference<ArrayList<TestDto>>() {
        });
    }
}
