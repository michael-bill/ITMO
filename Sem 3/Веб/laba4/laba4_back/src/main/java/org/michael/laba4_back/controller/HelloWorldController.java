package org.michael.laba4_back.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping(value = "/hello", produces = "application/json")
    public String hello() {
        return "{ \"message\": \"Hello World!\" }";
    }
}
