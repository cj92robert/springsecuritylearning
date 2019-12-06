package com.gmailatcj92robert.springsecuritylearning.services;

import com.gmailatcj92robert.springsecuritylearning.errors.AccountException;
import com.gmailatcj92robert.springsecuritylearning.models.DtoUser;
import com.gmailatcj92robert.springsecuritylearning.models.Role;
import com.gmailatcj92robert.springsecuritylearning.models.User;
import com.gmailatcj92robert.springsecuritylearning.repositories.RoleRepository;
import com.gmailatcj92robert.springsecuritylearning.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User createUser(DtoUser dtoUser) {
        userRepository.findByUsername(dtoUser.getUsername())
                .ifPresent((x) -> {
                    throw new AccountException("Użytkownik o podane nazwie już istnieje");
                });


        User user = dtoUser.toUser();
        user.setPassword(passwordEncoder.encode(dtoUser.getPassword()));
        List<Role> rolesNewUser = new ArrayList<>();
        roleRepository.findByName("USER").ifPresent(rolesNewUser::add);
        user.setRoles(rolesNewUser);
        user = userRepository.save(user);
        return user;
    }

    public Optional<User> getLogedUser(HttpServletRequest request) {

        return userRepository.findByUsername(request.getRemoteUser());

    }
}
