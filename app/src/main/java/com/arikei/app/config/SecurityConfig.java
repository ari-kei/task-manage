package com.arikei.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import com.arikei.grpc.verify.lib.VerifyGrpc.VerifyBlockingStub;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.client.inject.GrpcClientBean;

@Configuration
@EnableWebMvc
@GrpcClientBean(clazz = VerifyBlockingStub.class, beanName = "verifyBlockingStub",
    client = @GrpcClient("verify"))
public class SecurityConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, VerifyBlockingStub blockingStub)
      throws Exception {
    http.addFilterBefore(new AuthFilter(blockingStub), BasicAuthenticationFilter.class)
        .authorizeHttpRequests(authz -> authz.requestMatchers("/**").permitAll())
        .csrf((csrf) -> csrf.disable());
    return http.build();
  }
}
