package com.arikei.app.drivers;

import org.springframework.stereotype.Component;
import com.arikei.app.domains.entities.Board;
import com.arikei.app.domains.repositoryif.BoardRepositoryIF;

@Component
public class BoardRepositoryImpl implements BoardRepositoryIF {

  public void create(Board board) {
    // TODO 永続化処理実装
  }
}
