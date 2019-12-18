package com.gmailatcj92robert.springsecuritylearning.controllers;


import com.gmailatcj92robert.springsecuritylearning.jwt.JwtUtil;
import com.gmailatcj92robert.springsecuritylearning.models.DtoRegisterUser;
import com.gmailatcj92robert.springsecuritylearning.models.User;
import com.gmailatcj92robert.springsecuritylearning.services.UtilUserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private JwtUtil jwtUtil;

    @Autowired
    public AuthController(UtilUserService utilUserService, JwtUtil jwtUtil) {
        this.utilUserService = utilUserService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public ResponseEntity<String> login(HttpServletRequest request) {
        Optional<User> user = utilUserService.getLogedUser(request);
        if (user.isPresent()) {
            return new ResponseEntity<>(jwtUtil.generateJwtToken(user.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    @GetMapping("/islogin")
    public ResponseEntity<User> isLogged(HttpServletRequest request) {
        Optional<User> user = utilUserService.getLogedUser(request);
        if (user.isPresent()) {
            return new ResponseEntity<User>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody DtoRegisterUser dtoRegisterUser) {

        User newUser = utilUserService.createUser(dtoRegisterUser);
        return new ResponseEntity<User>(newUser, HttpStatus.OK);

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
