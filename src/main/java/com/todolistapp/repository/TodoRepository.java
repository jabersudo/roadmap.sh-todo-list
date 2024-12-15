package com.todolistapp.repository;

import com.todolistapp.model.entity.Todo;
import com.todolistapp.model.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    Page<Todo> findByUser(Users user, Pageable pageable);
}
