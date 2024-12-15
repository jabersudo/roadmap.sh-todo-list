package com.todolistapp.model.payload.response;

public record CreateTodoResponse(
        Long id,
        String title,
        String description) {
}
