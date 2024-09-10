package com.arikei.app.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.arikei.app.domains.TaskService;
import com.arikei.app.domains.entities.TaskCard;
import com.arikei.app.interfaces.request.CreateTaskCardRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/task")
@Controller
public class TaskController {

  private TaskService taskService;

  public TaskController(TaskService ts) {
    this.taskService = ts;
  }

  @PostMapping("")
  public ResponseEntity<TaskCard> createTaskCard(@RequestBody CreateTaskCardRequest request) {
    TaskCard taskCard = this.taskService.create(
        new TaskCard(request.getBoardId(), null, request.getTaskName(), request.getTaskStatus()));
    return ResponseEntity.ok().body(taskCard);
  }
}
