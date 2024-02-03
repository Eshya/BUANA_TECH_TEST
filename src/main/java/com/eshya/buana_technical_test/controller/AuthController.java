package com.eshya.buana_technical_test.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/")
public class AuthController {
    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
//        System.out.println("Reached /user endpoint");
        if(principal == null)
            return Collections.singletonMap("name", null);
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }
    @GetMapping("/error")
    public String error(HttpServletRequest request) {
        String message = (String) request.getSession().getAttribute("error.message");
        request.getSession().removeAttribute("error.message");
        return message;
    }

    @GetMapping("/access-token")
    public String getAccessToken(Authentication authentication) {
        System.out.println("Reached /access-token endpoint");
        System.out.println(authentication);
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2AccessToken) {
            OAuth2AccessToken accessToken = (OAuth2AccessToken) authentication.getPrincipal();
            return accessToken.getTokenValue();
        } else {
            return "No access token available";
        }
    }
}
