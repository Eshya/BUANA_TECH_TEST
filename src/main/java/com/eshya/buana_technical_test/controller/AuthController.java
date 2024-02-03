package com.eshya.buana_technical_test.controller;

import com.eshya.buana_technical_test.payload.MessageResponse;
import com.eshya.buana_technical_test.payload.auth.AuthRes;
import com.eshya.buana_technical_test.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/")
public class AuthController {
    private final JwtUtils jwtUtils;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);


    @Autowired
    public AuthController(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

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

    @PostMapping("/auth/access-token")
    public Object getAccessToken(Authentication authentication) {
        logger.info("get token for user :{} and authorities: {}", authentication.getName(), authentication.getAuthorities());

        if(authentication instanceof OAuth2AuthenticationToken) {

            String token = jwtUtils.generateJWTTokenOAuth2(authentication);

            AuthRes authRes = new AuthRes();
            authRes.setUsername(authentication.getName());
            authRes.setAccessToken(token);
            authRes.setExpiresAt(jwtUtils.getExpirationDateFromToken(token));
            authRes.setIssuedAt(jwtUtils.getIssuedAtFromToken(token));
            return new ResponseEntity<>(authRes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageResponse("Unauthorized"), HttpStatus.OK);
        }

    }
}
