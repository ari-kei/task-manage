package com.arikei.app.domains.entities;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Task {
  public String boardId;
  public String taskStatusId;
  public String name;
  public String description;
  public LocalDateTime dueDate;
  public int order;
}
