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
       //test szybki...dane do logowania z frontendu
        Role rola= new Role("USER");
        roleRepository.save(rola);
        List<Role> rols=new ArrayList<Role>();
        rols.add(rola);
        User user = new UserBuilder().createUser();
        user.setUsername("cj92");
        user.setPassword(new BCryptPasswordEncoder().encode("cj"));
        user.setRoles(rols);
        user.setEnabled(true);
        User user2=new UserBuilder().setName("cj").
                setEmail("a@a.pl").
                setPassword(new BCryptPasswordEncoder().encode("test")).
                setUsername("rob").
                setRoles(rols).
                createUser();
        userRepository.save(user2);
        userRepository.save(user);

    }

    @GetMapping
    public ResponseEntity<String[]> test(){
        String a[] ={ "test","test2"};
        return new ResponseEntity<>(a, HttpStatus.OK);
    }
}
