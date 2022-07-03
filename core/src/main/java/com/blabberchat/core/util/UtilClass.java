package com.blabberchat.core.util;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

public class UtilClass {

    public static final String LOGIN_URI = "/api/login";
    public static final String CONTENT_TYPE = "application/json";
    public static final String AUTHORIZATION_KEY = "Authorization";

    public static Algorithm getAlgorithm() {
        String SECRET_KEY = "secret";
        return Algorithm.HMAC256(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public static URI getUri(String uriPath) {
        return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(uriPath).toUriString());
    }
}
