package com.arikei.app.interfaces;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.arikei.app.domains.BoardService;
import com.arikei.app.domains.entities.Board;
import com.arikei.app.interfaces.response.FetchListResponse;

@RequestMapping("/boards")
@Controller
public class BoardsController {

  private final BoardService boardService;

  public BoardsController(BoardService bs) {
    this.boardService = bs;
  }

  @GetMapping("")
  public ResponseEntity<FetchListResponse> fetchList() {
    // TODO アプリ内部でエラーが起きることの対応
    List<Board> boards = this.boardService.fetchList();
    return ResponseEntity.ok().body(new FetchListResponse(boards, ""));
  }

}
