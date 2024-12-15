package com.todolistapp.model.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetTodoResponse {
    private List<GetTodos> data = new ArrayList<>();
    private Integer page;
    private Integer limit;
    private Long total;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetTodos {
        private String id;
        private String title;
        private String description;
    }
}
