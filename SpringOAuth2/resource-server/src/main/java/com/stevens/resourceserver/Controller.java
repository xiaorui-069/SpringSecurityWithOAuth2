package com.stevens.resourceserver;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/hello")
    public String hello(@AuthenticationPrincipal Jwt jwt) {
        return "This is a OAuth2 demo. The jwt is " + jwt.getSubject();
    }
}
