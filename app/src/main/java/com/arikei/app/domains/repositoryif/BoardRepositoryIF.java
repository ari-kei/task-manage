package com.arikei.app.domains.repositoryif;

import java.util.List;
import com.arikei.app.domains.entities.Board;

public interface BoardRepositoryIF {
  public void create(Board board);

  public List<Board> fetchList();
}
