package com.hpe.apppulse.openapi.v1.bl.beans;

/**
 * Created by Meir Ron on 7/22/2015.
 *
 * This is the bean that contains the return Token details.
 */
public class TokenBean {
    private Long expirationTime;
    private String token;

    public TokenBean() {
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
