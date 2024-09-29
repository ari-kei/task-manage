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
    return List.of(
        new Task("1", "a", "1-1", "タスク名称未着手1", "タスク説明", LocalDateTime.now(), 1),
        new Task("1", "b", "1-1", "タスク名称未着手2", "タスク説明2", LocalDateTime.now(), 2),
        new Task("1", "g", "1-1", "タスク名称未着手7", "タスク説明7", LocalDateTime.now(), 7),
        new Task("1", "c", "1-1", "タスク名称未着手3", "タスク説明3", LocalDateTime.now(), 3),
        new Task("1", "d", "1-1", "タスク名称未着手4", "タスク説明4", LocalDateTime.now(), 4),
        new Task("1", "e", "1-1", "タスク名称未着手5", "タスク説明5", LocalDateTime.now(), 5),
        new Task("1", "h", "1-1", "タスク名称未着手8", "タスク説明8", LocalDateTime.now(), 8),
        new Task("1", "f", "1-1", "タスク名称未着手6", "タスク説明6", LocalDateTime.now(), 6),
        new Task("1", "j", "1-2", "タスク名称今月着手1", "タスク説明今月着手1", LocalDateTime.now(), 1),
        new Task("1", "k", "1-3", "タスク名称着手中1", "タスク説明着手中1", LocalDateTime.now(), 1));
  }

  public Task fetchOne(String boardId, String taskId) {
    return new Task("1", "a", "1-1", "タスク名称", "タスク説明", LocalDateTime.now(), 1);
  }
}
