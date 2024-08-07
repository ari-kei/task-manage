package com.arikei.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class SecurityConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.addFilterBefore(new AuthFilter(), BasicAuthenticationFilter.class)
        .authorizeHttpRequests(authz -> authz.requestMatchers("/**").permitAll())
        .csrf((csrf) -> csrf.disable());
    return http.build();
  }
}
