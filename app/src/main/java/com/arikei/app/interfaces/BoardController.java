package com.arikei.app.interfaces;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.arikei.app.domains.BoardService;
import com.arikei.app.domains.entities.Board;
import com.arikei.app.interfaces.request.CreateBoardRequest;

@RequestMapping("/board")
@Controller
public class BoardController {

  private BoardService boardService;

  public BoardController(BoardService bs) {
    this.boardService = bs;
  }

  @PostMapping("")
  public ResponseEntity<Board> Create(@RequestBody CreateBoardRequest request) {
    if (request.getName().isEmpty()) {
      return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(null);
    }
    Board board = this.boardService.create(new Board("", request.getName()));
    return ResponseEntity.ok().body(board);
  }
}
