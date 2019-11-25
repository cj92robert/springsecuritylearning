package com.gmailatcj92robert.springsecuritylearning.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserBuilder {
    private String username;
    private String name = "";
    private String lastname = "";
    private String email;
    private String password;
    private List<Role> roles = List.of(new Role("USER"));
    private boolean enabled = true;

    public UserBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public UserBuilder setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public User createUser() {
        return new User(username, name, lastname, email, password, roles, enabled);
    }
}
