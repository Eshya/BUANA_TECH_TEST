package com.eshya.buana_technical_test.payload.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AuthRes {
    private String username;

    private String accessToken;

    private Date expiresAt;

    private Date issuedAt;
}
