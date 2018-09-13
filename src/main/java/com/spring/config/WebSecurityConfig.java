package com.spring.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.spring.security.JWTAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // disable caching
        http.headers().cacheControl();

        http.csrf().disable() // disable csrf for our requests.
        .authorizeRequests()
        .antMatchers("/forget_passsword").permitAll()
        .antMatchers("/verify").permitAll()
        .antMatchers("/addPerson").permitAll()
        .antMatchers("/user").permitAll()
        .antMatchers("/test").permitAll()
        .antMatchers(HttpMethod.POST,"/login").permitAll()
        .anyRequest().authenticated()
        .and()
        .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
