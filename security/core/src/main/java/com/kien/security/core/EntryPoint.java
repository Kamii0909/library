package com.kien.security.core;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.kien.security.core.spi.Request;

public interface EntryPoint {
    Evaluator forRequest(Request request);
    
    @Bean
    static SecurityFilterChain configurer(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.securityMatchers(matcher -> {}).build();
    }
}
