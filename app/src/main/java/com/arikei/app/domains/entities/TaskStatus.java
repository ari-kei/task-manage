package com.arikei.app.domains.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskStatus {
  public String boardId;
  public String statusId;
  public String statusName;
  public int order;

}
