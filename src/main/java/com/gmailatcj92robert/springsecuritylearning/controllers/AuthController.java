package com.gmailatcj92robert.springsecuritylearning.controllers;


import com.gmailatcj92robert.springsecuritylearning.models.DtoRegisterUser;
import com.gmailatcj92robert.springsecuritylearning.models.User;
import com.gmailatcj92robert.springsecuritylearning.services.UtilUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    private UtilUserService utilUserService;

    public AuthController(UtilUserService utilUserService) {
        this.utilUserService = utilUserService;
    }

    @GetMapping
    public ResponseEntity<User> login(HttpServletRequest request) {

        Optional<User> logUser = utilUserService.getLogedUser(request);

        if (logUser.isPresent()) {
            return new ResponseEntity<>(logUser.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody DtoRegisterUser dtoRegisterUser) {

        User newUser = utilUserService.createUser(dtoRegisterUser);
        return new ResponseEntity<>(newUser, HttpStatus.OK);

    }


    @PutMapping("/password/")
    public ResponseEntity resetPassword(@RequestBody String userName) {

        if (utilUserService.resetPassword(userName))
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);

    }


    @GetMapping("/activate/{id}/{token}")
    public ResponseEntity activateUser(@PathVariable long token, @PathVariable long id) {

        if (utilUserService.activateUser(id, token))
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

    @PostMapping("/password/")
    public ResponseEntity changePassword(@RequestBody String password, @RequestBody long id, @RequestBody long token) {

        if (utilUserService.changePassword(id, token, password)) {
            return new ResponseEntity(HttpStatus.OK);
        } else
            return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

}
