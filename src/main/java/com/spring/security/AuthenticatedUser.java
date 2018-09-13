package com.spring.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class AuthenticatedUser implements Authentication {

    private String name;
    private boolean authenticated = true;

    AuthenticatedUser(String name){
        this.name = name;
    }

    
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    
    public Object getCredentials() {
        return null;
    }

    
    public Object getDetails() {
        return null;
    }

   
    public Object getPrincipal() {
        return null;
    }

    
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        this.authenticated = b;
    }

    
    public String getName() {
        return this.name;
    }
}
