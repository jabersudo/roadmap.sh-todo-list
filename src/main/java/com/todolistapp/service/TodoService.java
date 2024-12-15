package com.todolistapp.service;

import com.todolistapp.exception.NotFoundException;
import com.todolistapp.model.entity.Todo;
import com.todolistapp.model.entity.Users;
import com.todolistapp.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    private void saveOrUpdate(Todo todo) {
        try {
            todoRepository.save(todo);
        } catch (Exception e) {
            log.error("can not save or update todo |---> {}", e.getMessage());
        }
    }

    private Optional<Todo> getById(Long id) {
        return todoRepository.findById(id);
    }

    private void deleteById(Long id) {
        try {
            todoRepository.deleteById(id);
        } catch (Exception e) {
            log.error("can not delete todo |---> {}", e.getMessage());
        }
    }

    public Long create(Todo todo, Users user) {
        todo.setUser(user);
        saveOrUpdate(todo);
        return todo.getId();
    }

    public Todo update(Todo todo, Users user) {
        Todo todoFromDb = getById(todo.getId()).orElseThrow(() -> new NotFoundException("there is no Todo with this id"));
        if (!todoFromDb.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("Forbidden");
        }
        todoFromDb.setTitle(todo.getTitle());
        todoFromDb.setDescription(todo.getDescription());
        saveOrUpdate(todoFromDb);
        return todoFromDb;
    }

    public Page<Todo> getByUser(Users user, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return todoRepository.findByUser(user, pageable);
    }

    public void deleteById(Long todoId, Users user) {
        Todo todo = getById(todoId).orElseThrow(() -> new NotFoundException("there is no Todo with this id"));
        if (!todo.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("Forbidden");
        }
        deleteById(todoId);
    }
}
