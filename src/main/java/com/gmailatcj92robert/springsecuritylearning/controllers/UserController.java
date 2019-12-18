package com.gmailatcj92robert.springsecuritylearning.controllers;

import com.gmailatcj92robert.springsecuritylearning.models.User;
import com.gmailatcj92robert.springsecuritylearning.repositories.RoleRepository;
import com.gmailatcj92robert.springsecuritylearning.repositories.UserRepository;
import com.gmailatcj92robert.springsecuritylearning.services.UtilUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UtilUserService utilUserService;


    public UserController(UtilUserService utilUserService) {
        this.utilUserService = utilUserService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<User>> getAllUsers(Pageable pageable, HttpServletRequest request) {
        System.out.println(pageable);
        return new ResponseEntity<>(utilUserService.getAllUsers(pageable), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        Optional<User> user = utilUserService.findById(id);
        if (user.isPresent())
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/{id}/enabled")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity setEnabledUser(@PathVariable long id, @RequestBody boolean isEnabled) {
        if (utilUserService.changeIsEnabled(id, isEnabled)) {
            return new ResponseEntity(HttpStatus.OK);
        } else
            return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

    @PutMapping("/{id}/roles")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> setRoles(@PathVariable long id, @RequestBody String roles[]) {

        if (utilUserService.setRoles(id, roles)) {

            Optional<User> user = utilUserService.findById(id);

            if (user.isPresent())
                return new ResponseEntity<>(user.get(), HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity deleteUserById(@PathVariable long id) {

        if (utilUserService.deleteUserById(id))
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

}
