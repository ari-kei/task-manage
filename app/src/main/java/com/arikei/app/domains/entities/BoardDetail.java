package com.arikei.app.domains.entities;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardDetail {
  public Board board;
  public List<TaskStatus> taskStatus;
}
