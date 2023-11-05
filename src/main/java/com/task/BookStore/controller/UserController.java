package com.task.BookStore.controller;

import com.task.BookStore.filter.JwtUtil;
import com.task.BookStore.models.LoginRequest;
import com.task.BookStore.models.user.UserEntity;
import com.task.BookStore.repository.RoleRepository;
import com.task.BookStore.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtUtil jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register/student")
    public ResponseEntity<UserEntity> registerStudent(@RequestBody UserEntity user) {
        user.setRoles(Collections.singleton(roleRepository.findByName("STUDENT")));
        UserEntity registeredUser = userService.registerUser(user);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/register/author")
    public ResponseEntity<UserEntity> registerAuthor(@RequestBody UserEntity user) {
        user.setRoles(Collections.singleton(roleRepository.findByName("AUTHOR")));
        UserEntity registeredUser = userService.registerAuthor(user);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(token);
    }
}
