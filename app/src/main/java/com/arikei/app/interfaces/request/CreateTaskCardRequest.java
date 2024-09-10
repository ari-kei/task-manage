package com.arikei.app.interfaces.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateTaskCardRequest {
  @JsonProperty("boardId")
  public String boardId;

  @JsonProperty("name")
  public String taskName;

  @JsonProperty("status")
  public String taskStatus;
}
