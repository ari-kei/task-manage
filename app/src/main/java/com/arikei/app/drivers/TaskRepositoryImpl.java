package com.arikei.app.drivers;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Component;
import com.arikei.app.domains.entities.Task;
import com.arikei.app.domains.entities.TaskCard;
import com.arikei.app.domains.repositoryif.TaskRepositoryIF;

@Component
public class TaskRepositoryImpl implements TaskRepositoryIF {

  public void create(TaskCard taskCard) {
    // TODO タスク(名称のみ)作成処理
  }

  public List<Task> fetchList(String boardId) {
    return List.of(new Task("1", "1-1", "タスク名称未着手1", "タスク説明", LocalDateTime.now(), 1),
        new Task("1", "1-1", "タスク名称未着手2", "タスク説明2", LocalDateTime.now(), 2),
        new Task("1", "1-2", "タスク名称今月着手1", "タスク説明今月着手1", LocalDateTime.now(), 1),
        new Task("1", "1-3", "タスク名称着手中1", "タスク説明着手中1", LocalDateTime.now(), 1));
  }
}
