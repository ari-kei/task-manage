package com.arikei.auth.domains.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.Getter;

@Configuration
public class PasswordEncoder {

  @Getter
  public BCryptPasswordEncoder passwordEncoder;

  public PasswordEncoder() {
    this.passwordEncoder = new BCryptPasswordEncoder();
  }
}
