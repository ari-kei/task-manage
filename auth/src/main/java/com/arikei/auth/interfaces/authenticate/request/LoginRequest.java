package com.arikei.auth.interfaces.authenticate.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LoginRequest {
  @JsonProperty("userId")
  public String userId;
  @JsonProperty("password")
  public String password;
}
