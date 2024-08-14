package com.arikei.app.interfaces.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CreateBoardRequest {
  @JsonProperty("name")
  public String name;
}
