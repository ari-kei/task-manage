package com.arikei.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@ConfigurationProperties(prefix = "key")
public class KeyPathConfig {
  private final String privatekey;
  private final String publickey;
}
