package com.arikei.app.domains;

import java.util.List;
import org.springframework.stereotype.Service;

import com.arikei.app.domains.entities.Board;
import com.arikei.app.domains.repositoryif.BoardRepositoryIF;
import com.fasterxml.uuid.Generators;

@Service
public class BoardService {

  private final BoardRepositoryIF boardRepositoryIF;

  public BoardService(BoardRepositoryIF bRepositoryIF) {
    this.boardRepositoryIF = bRepositoryIF;
  }

  public Board create(Board board) {
    board.setId(Generators.timeBasedEpochGenerator().generate().toString());
    this.boardRepositoryIF.create(board);
    return board;
  }

  public List<Board> fetchList() {
    List<Board> boards = this.boardRepositoryIF.fetchList();
    return boards;
  }
}
