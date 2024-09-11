package com.arikei.app.domains.repositoryif;

import java.util.List;
import com.arikei.app.domains.entities.Task;
import com.arikei.app.domains.entities.TaskCard;

public interface TaskRepositoryIF {
  public void create(TaskCard taskCard);

  public List<Task> fetchList(String boardId);
}
