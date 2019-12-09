package com.gmailatcj92robert.springsecuritylearning.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;

public class DtoUser {
    @NotNull(message = "Nazwa użytkownika nie może być pusta")
    // @Pattern(regexp = Constants.LOGIN_REGEX)
    private String username;
    @NotNull(message = "Imie jest wymagane")
    private String name;
    @NotNull(message = "Nazwisko jest wymagane")
    private String lastname;
    @NotNull(message = "Email wymagany.")
    @Email(message = "Niepoprawny email")
    private String email;
    @NotNull(message = "Wpisz hasło")
    @Size(min = 6, max = 16, message = "Haslo musi mieć pomiedzy 6 a 16 znaków")
    private String password;

    public DtoUser(String username, String name, String lastname, String email, String emailRepeat, String password, String passwordRepeat) {
        this.username = username;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public DtoUser() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User toUser() {
        return new User(username, name, lastname, email, password, Collections.EMPTY_LIST, false);
    }
}
