package com.arikei.app.domains;

import org.springframework.stereotype.Service;
import com.arikei.app.domains.entities.TaskCard;
import com.arikei.app.domains.repositoryif.TaskRepositoryIF;
import com.fasterxml.uuid.Generators;

@Service
public class TaskService {

  private TaskRepositoryIF taskRepositoryIF;

  public TaskService(TaskRepositoryIF tRepositoryIF) {
    this.taskRepositoryIF = tRepositoryIF;
  }

  public TaskCard create(TaskCard taskCard) {
    taskCard.setTaskId(Generators.timeBasedEpochGenerator().generate().toString());
    this.taskRepositoryIF.create(taskCard);
    return taskCard;
  }
}