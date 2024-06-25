package com.thotsou.testserviceone.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @GetMapping("/test")
    public String returnTestMessage() {
        return "Microservice one is up";
    }

}
