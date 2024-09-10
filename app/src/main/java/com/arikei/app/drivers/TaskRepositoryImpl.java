package com.arikei.app.drivers;

import org.springframework.stereotype.Component;
import com.arikei.app.domains.entities.TaskCard;
import com.arikei.app.domains.repositoryif.TaskRepositoryIF;

@Component
public class TaskRepositoryImpl implements TaskRepositoryIF {

  public void create(TaskCard taskCard) {
    // TODO タスク(名称のみ)作成処理
  }
}
