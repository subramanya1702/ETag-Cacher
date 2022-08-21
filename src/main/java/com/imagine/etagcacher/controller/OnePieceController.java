package com.imagine.etagcacher.controller;

import com.imagine.etagcacher.annotation.ETagCacher;
import com.imagine.etagcacher.annotation.EnableETagCacher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@EnableETagCacher
@RestController
@RequestMapping("/v1/one-piece")
public class OnePieceController {

    @GetMapping("/yonkos/{warlord}")
    @ETagCacher(expiry = 1440)
    public List<String> getYonkosList(@RequestParam("emperor") String emperor,
                                      @PathVariable("warlord") String warlord) {
        return List.of("Shanks", "Blackbeard", "Luffy", "Kid");
    }
}