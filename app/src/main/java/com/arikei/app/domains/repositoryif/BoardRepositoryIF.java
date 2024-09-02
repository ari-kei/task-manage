package com.arikei.app.domains.repositoryif;

import java.util.List;
import java.util.Optional;
import com.arikei.app.domains.entities.Board;

public interface BoardRepositoryIF {
  public void create(Board board);

  public List<Board> fetchList();

  public Optional<Board> fetch(String board);
}
