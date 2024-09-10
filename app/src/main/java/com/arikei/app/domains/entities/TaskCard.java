package com.arikei.app.domains.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TaskCard {
  public String boardId;
  public String taskId;
  public String taskName;
  public String taskStatus;
}
