package com.arikei.app.drivers;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import com.arikei.app.domains.entities.Board;
import com.arikei.app.domains.entities.BoardDetail;
import com.arikei.app.domains.entities.TaskStatus;
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

  public Optional<BoardDetail> fetchDetail(String boardId) {
    // TODO ボード取得処理
    return Optional.of(new BoardDetail(new Board("1", "boardName"),
        List.of(new TaskStatus("1", "1-1", "未着手", 1), new TaskStatus("1", "1-2", "今月着手", 2),
            new TaskStatus("1", "1-3", "着手中", 3), new TaskStatus("1", "1-4", "完了", 4))));
  }
}
