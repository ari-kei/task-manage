package com.arikei.app.domains;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.arikei.app.domains.entities.Board;
import com.arikei.app.domains.entities.BoardDetail;
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

  public BoardDetail fetchDetail(String boardId) throws IllegalArgumentException {
    Optional<BoardDetail> boardDetailOptional = this.boardRepositoryIF.fetchDetail(boardId);
    if (boardDetailOptional.isEmpty()) {
      throw new IllegalArgumentException("Invalid boardId");
    }
    return boardDetailOptional.get();
  }
}
