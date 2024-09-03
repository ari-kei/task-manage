package com.arikei.app.domains.repositoryif;

import java.util.List;
import java.util.Optional;
import com.arikei.app.domains.entities.Board;
import com.arikei.app.domains.entities.BoardDetail;

public interface BoardRepositoryIF {
  public void create(Board board);

  public List<Board> fetchList();

  public Optional<BoardDetail> fetchDetail(String board);
}
