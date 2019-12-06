package com.gmailatcj92robert.springsecuritylearning.controllers;


import com.gmailatcj92robert.springsecuritylearning.models.DtoUser;
import com.gmailatcj92robert.springsecuritylearning.models.User;
import com.gmailatcj92robert.springsecuritylearning.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<User> login(HttpServletRequest request) {
        Optional<User> logUser = userService.getLogedUser(request);
        if (logUser.isPresent()) {
            return new ResponseEntity<>(logUser.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping
    public ResponseEntity<User> register(@Valid @RequestBody DtoUser dtoUser) {

        User newUser = userService.createUser(dtoUser);
        return new ResponseEntity<>(newUser, HttpStatus.OK);

    }

}
