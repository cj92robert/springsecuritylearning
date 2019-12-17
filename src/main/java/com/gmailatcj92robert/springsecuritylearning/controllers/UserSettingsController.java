package com.gmailatcj92robert.springsecuritylearning.controllers;

import com.gmailatcj92robert.springsecuritylearning.models.DtoRegisterUser;
import com.gmailatcj92robert.springsecuritylearning.models.User;
import com.gmailatcj92robert.springsecuritylearning.services.UtilUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("/settings")
public class UserSettingsController {
    private UtilUserService utilUserService;

    public UserSettingsController(UtilUserService utilUserService) {
        this.utilUserService = utilUserService;
    }

    @PutMapping("/email/")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<User> updateMail(@Valid @RequestBody DtoRegisterUser dtoRegisterUser) {

        User user = utilUserService.updateUserMail(dtoRegisterUser);
        return new ResponseEntity<>(user, HttpStatus.OK);

    }


}
