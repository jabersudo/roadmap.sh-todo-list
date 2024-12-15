package com.todolistapp.controller;

import com.todolistapp.mapper.TodoMapper;
import com.todolistapp.model.entity.Todo;
import com.todolistapp.model.entity.Users;
import com.todolistapp.model.payload.request.CreateTodoRequest;
import com.todolistapp.model.payload.request.UpdateTodoRequest;
import com.todolistapp.model.payload.response.CreateTodoResponse;
import com.todolistapp.model.payload.response.GetTodoResponse;
import com.todolistapp.model.payload.response.UpdateTodoResponse;
import com.todolistapp.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/todos")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<CreateTodoResponse> create(@RequestBody CreateTodoRequest request) {
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Todo todo = TodoMapper.INSTANCE.todoMapper(request);
        Long todoId = todoService.create(todo, user);
        return ResponseEntity.ok(new CreateTodoResponse(todoId, todo.getTitle(), todo.getDescription()));
    }

    @PutMapping("{todoId}")
    public ResponseEntity<UpdateTodoResponse> update(@RequestBody UpdateTodoRequest request, @PathVariable Long todoId) {
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Todo todo = TodoMapper.INSTANCE.todoMapper(request);
        todo.setId(todoId);
        UpdateTodoResponse response = TodoMapper.INSTANCE.todoMapper(todoService.update(todo, user));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<GetTodoResponse> get(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                               @RequestParam(name = "limit", defaultValue = "10") Integer limit) {
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<Todo> todos = todoService.getByUser(user, page - 1, limit);
        GetTodoResponse getTodoResponse = TodoMapper.INSTANCE.todoMapper(todos);
        return ResponseEntity.ok(getTodoResponse);
    }

    @DeleteMapping("{todoId}")
    public ResponseEntity<Void> delete(@PathVariable Long todoId) {
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        todoService.deleteById(todoId, user);
        return ResponseEntity.noContent().build();
    }

}
