package com.todolistapp.service;

import com.todolistapp.exception.DuplicateUserException;
import com.todolistapp.exception.NotFoundException;
import com.todolistapp.exception.PasswordException;
import com.todolistapp.model.entity.Users;
import com.todolistapp.model.enumeration.UserRole;
import com.todolistapp.repository.UserRepository;
import com.todolistapp.service.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public void saveOrUpdate(Users user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error("can not save or update user |---> {}", e.getMessage());
        }
    }

    public Optional<Users> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public String register(Users user) {
        user.setRole(UserRole.ROLE_USER);
        if (getByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateUserException("this email has already been used");
        }
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName("");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String jwt = jwtService.generateToken(user);
        saveOrUpdate(user);
        return jwt;
    }

    public String login(Users user) {
        Optional<Users> optionalUser = getByEmail(user.getEmail());
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("user not found");
        }
        if (!passwordEncoder.matches(user.getPassword(), optionalUser.get().getPassword())) {
            throw new PasswordException("wrong password");
        }
        return jwtService.generateToken(user);
    }
}
