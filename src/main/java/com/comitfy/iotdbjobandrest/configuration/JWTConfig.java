package com.comitfy.iotdbjobandrest.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JWTConfig {

    private String secret;
    // Diğer konfigürasyonlar, süreler vb. buraya eklenebilir

    private String expiration;


    public String getSecret() {
        return secret;
    }

    public String getExpiration() {
        return expiration;
    }


    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
}
