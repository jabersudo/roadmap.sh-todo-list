package com.todolistapp.mapper;

import com.todolistapp.model.entity.Todo;
import com.todolistapp.model.payload.request.CreateTodoRequest;
import com.todolistapp.model.payload.request.UpdateTodoRequest;
import com.todolistapp.model.payload.response.GetTodoResponse;
import com.todolistapp.model.payload.response.UpdateTodoResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(
        builder = @Builder(disableBuilder = true),
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface TodoMapper {
    TodoMapper INSTANCE = Mappers.getMapper(TodoMapper.class);

    Todo todoMapper(CreateTodoRequest request);

    Todo todoMapper(UpdateTodoRequest request);

    UpdateTodoResponse todoMapper(Todo todo);

    GetTodoResponse.GetTodos dataMapper(Todo value);

    @Mappings({
            @Mapping(source = "totalElements", target = "total"),
            @Mapping(source = "numberOfElements", target = "limit"),
            @Mapping(source = "number", target = "page"),
            @Mapping(source = "content", target = "data")
    })
    GetTodoResponse todoMapper(Page<Todo> todos);

}
