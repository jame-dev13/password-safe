package com.jame.safepassword.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(request ->
                    request.requestMatchers("/api/v1/**")
                            .permitAll()
                            .requestMatchers("/", "/tests", "/css/**", "/js/**", "/img/**").permitAll())
                .csrf(csrf -> csrf.disable());
        return http.build();
    }
}
