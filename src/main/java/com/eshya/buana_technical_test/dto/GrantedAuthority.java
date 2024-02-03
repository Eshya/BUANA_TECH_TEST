package com.eshya.buana_technical_test.dto;

public class GrantedAuthority implements org.springframework.security.core.GrantedAuthority {
    private String authority;

    public GrantedAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
