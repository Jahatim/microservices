package com.tim.gatewayserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @RequestMapping("/fallback")
    public String serviceFallback() {
        return "Service is not available!!! Please try again later or contact the admin";
    }
}
