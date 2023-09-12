package com.cronocurso.api.controllers;

import com.cronocurso.api.models.UserModel;
import com.cronocurso.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<List<UserModel>> index() {
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }
}
