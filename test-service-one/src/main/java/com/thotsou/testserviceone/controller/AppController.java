package com.thotsou.testserviceone.controller;

import com.thotsou.testserviceone.model.Quote;
import com.thotsou.testserviceone.service.RandomQuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AppController {

    private final RandomQuoteService randomQuoteService;

    @GetMapping("/test")
    public String returnTestMessage() {
        return "Microservice one is up";
    }

    @GetMapping("/quote/random-quote")
    public ResponseEntity<Quote> getRandomQuoteByCategory(@RequestParam String category) {
        return randomQuoteService.generateRandomQuote(category);
    }

    @GetMapping("/quote/categories")
    public ResponseEntity<List<String>> getQuotesCategories() {
        return randomQuoteService.getQuotesCategories();
    }

}
