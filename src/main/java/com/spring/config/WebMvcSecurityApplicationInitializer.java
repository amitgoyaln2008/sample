package com.spring.config;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;


@Order(2)
public class WebMvcSecurityApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

    public WebMvcSecurityApplicationInitializer() {
        super(WebSecurityConfig.class);
    }

}