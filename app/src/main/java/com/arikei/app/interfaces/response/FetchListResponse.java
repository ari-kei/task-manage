package com.arikei.app.interfaces.response;

import java.util.List;
import com.arikei.app.domains.entities.Board;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FetchListResponse {
  @JsonProperty("data")
  private List<Board> boards;

  @JsonProperty("error")
  private String error;

}
