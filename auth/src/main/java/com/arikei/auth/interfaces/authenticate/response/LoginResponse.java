package com.arikei.auth.interfaces.authenticate.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
  @JsonProperty("token")
  private String accessToken;
  @JsonProperty("error")
  private String error;
}
