package com.spring.security;

import com.spring.model.User;
import com.spring.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public final class TokenHandler {

    private final UserService userService;
    private String secret = "ThisIsASecret";

    public TokenHandler(UserService userService) {
        this.userService = userService;
    }

    public User parseUserFromToken(String token) {
        String username = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return userService.findByEmail(username);
    }

    public String createTokenForUser(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}