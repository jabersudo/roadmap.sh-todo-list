package com.todolistapp.controller;

import com.todolistapp.mapper.UserMapper;
import com.todolistapp.model.entity.Users;
import com.todolistapp.model.payload.request.UserLoginRequest;
import com.todolistapp.model.payload.request.UserRegistrationRequest;
import com.todolistapp.model.payload.response.UserLoginResponse;
import com.todolistapp.model.payload.response.UserRegistrationResponse;
import com.todolistapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> register(@RequestBody @Valid UserRegistrationRequest request) {
        Users user = UserMapper.INSTANCE.userMapper(request);
        String token = userService.register(user);
        return ResponseEntity.ok(new UserRegistrationResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest request) {
        Users user = UserMapper.INSTANCE.userMapper(request);
        String token = userService.login(user);
        return ResponseEntity.ok(new UserLoginResponse(token));
    }
}
