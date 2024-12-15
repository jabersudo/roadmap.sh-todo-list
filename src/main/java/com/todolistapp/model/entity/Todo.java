package com.todolistapp.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "todo_with_user_fk"))
    private Users user;
}
