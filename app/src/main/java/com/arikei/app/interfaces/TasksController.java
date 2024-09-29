package com.arikei.app.interfaces;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arikei.app.domains.TaskService;
import com.arikei.app.domains.entities.Task;

@RequestMapping("/tasks")
@RestController
public class TasksController {

  private TaskService taskService;

  public TasksController(TaskService tService) {
    this.taskService = tService;
  }

  @GetMapping("")
  public ResponseEntity<List<Task>> fetchList(@RequestParam(value = "boardId") String boardId) {
    List<Task> tasks = taskService.fetchList(boardId);
    return ResponseEntity.ok().body(tasks);
  }
}
