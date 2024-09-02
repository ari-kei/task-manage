package com.arikei.app.interfaces;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.arikei.app.domains.BoardService;
import com.arikei.app.domains.entities.Board;
import com.arikei.app.interfaces.request.CreateBoardRequest;
import jakarta.servlet.ServletRequest;

@RequestMapping("/board")
@Controller
public class BoardController {

  private BoardService boardService;

  public BoardController(BoardService bs) {
    this.boardService = bs;
  }

  @PostMapping("")
  public ResponseEntity<Board> Create(@RequestBody CreateBoardRequest request,
      ServletRequest servletRequest) {
    if (request.getName().isEmpty()) {
      return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(null);
    }
    Board board = this.boardService.create(new Board("", request.getName()));
    return ResponseEntity.ok().body(board);
  }

  @GetMapping("/{boardId}")
  public ResponseEntity<Board> Fetch(@PathVariable String boardId, ServletRequest servletRequest) {
    Board board;
    try {
      board = this.boardService.fetch(boardId);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(null);
    }
    return ResponseEntity.ok().body(board);
  }
}
