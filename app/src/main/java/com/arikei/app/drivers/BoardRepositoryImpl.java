package com.arikei.app.drivers;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import com.arikei.app.domains.entities.Board;
import com.arikei.app.domains.repositoryif.BoardRepositoryIF;

@Component
public class BoardRepositoryImpl implements BoardRepositoryIF {

  public void create(Board board) {
    // TODO 永続化処理実装
  }

  public List<Board> fetchList() {
    // TODO List取得処理
    return List.of(new Board("1", "boardName"));
  }

  public Optional<Board> fetch(String boardId) {
    // TODO ボード取得処理
    return Optional.of(new Board("1", "boardName"));
  }
}
