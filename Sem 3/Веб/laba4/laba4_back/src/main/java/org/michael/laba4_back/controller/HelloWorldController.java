package org.michael.laba4_back.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.michael.laba4_back.Utils.getMessage;

@RestController
public class HelloWorldController {
    @RequestMapping(value = "/hello", produces = "application/json")
    public String hello() {
        return getMessage("Hello, World!");
    }
}
