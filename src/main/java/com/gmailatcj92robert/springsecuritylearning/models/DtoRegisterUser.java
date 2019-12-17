package com.gmailatcj92robert.springsecuritylearning.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;

public class DtoRegisterUser {
    @NotNull(message = "Nazwa użytkownika nie może być pusta")
    // @Pattern(regexp = Constants.LOGIN_REGEX)
    final private String username;
    @NotNull(message = "Email wymagany.")
    @Email(message = "Niepoprawny email")
    final private String email;
    @NotNull(message = "Wpisz hasło")
    @Size(min = 6, max = 16, message = "Haslo musi mieć pomiedzy 6 a 16 znaków")
    final private String password;

    public DtoRegisterUser(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }


    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }


    public User toUser() {
        return new User(username, "", "", email, password, Collections.EMPTY_LIST, false);
    }
}
