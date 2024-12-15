package com.todolistapp.model.payload.response;

import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionDto {
    private String timeStamp;
    private Integer statusCode;
    private String error;
    private List<String> messages;
}