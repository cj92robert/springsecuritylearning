package com.gmailatcj92robert.springsecuritylearning.controllers;

import com.gmailatcj92robert.springsecuritylearning.models.Role;
import com.gmailatcj92robert.springsecuritylearning.models.User;
import com.gmailatcj92robert.springsecuritylearning.models.UserBuilder;
import com.gmailatcj92robert.springsecuritylearning.repositories.RoleRepository;
import com.gmailatcj92robert.springsecuritylearning.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    private UserRepository userRepository;
    private RoleRepository roleRepository;


    @Autowired
    public UserController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public ResponseEntity<String> test(HttpServletRequest request) {

        return new ResponseEntity<>(request.getRemoteUser(), HttpStatus.OK);
    }
}
