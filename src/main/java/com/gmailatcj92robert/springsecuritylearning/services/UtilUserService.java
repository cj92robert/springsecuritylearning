package com.gmailatcj92robert.springsecuritylearning.services;

import com.gmailatcj92robert.springsecuritylearning.errors.AccountException;
import com.gmailatcj92robert.springsecuritylearning.models.DtoUser;
import com.gmailatcj92robert.springsecuritylearning.models.Role;
import com.gmailatcj92robert.springsecuritylearning.models.User;
import com.gmailatcj92robert.springsecuritylearning.repositories.RoleRepository;
import com.gmailatcj92robert.springsecuritylearning.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UtilUserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private MailService mailService;

    @Autowired
    public UtilUserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, MailService mailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
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
        mailService.sendActivationKey(user.getId());
        return user;
    }

    public Optional<User> getLogedUser(HttpServletRequest request) {

        return userRepository.findByUsername(request.getRemoteUser());

    }

    public User updateUser(DtoUser dtoUser) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        user.setEmail(dtoUser.getEmail());
        user.setName(dtoUser.getName());
        user.setLastname(dtoUser.getLastname());

        userRepository.save(user);
        return user;
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        userRepository.findAll().forEach(usersList::add);
        return usersList;
    }


    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public boolean deleteUserById(long id) {

        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean updatePassword(String userName) {
        if (userRepository.findByUsername(userName).isPresent()) {

            User user = userRepository.findByUsername(userName).get();
            mailService.sendPasswordForgetKey(user.getId());
            return true;

        } else
            return false;

    }

    public boolean activateUser(long id, long activationKey) {
        if (userRepository.findById(id).isPresent()) {

            User user = userRepository.findById(id).get();
            if (user.getActivationKey().equals(activationKey)) {
                user.setEnabled(true);
                user.setActivationKey(null);
                userRepository.save(user);
                return true;
            } else
                return false;
        } else
            return false;
    }

    public boolean changePassword(long id, long key, String password) {
        if (userRepository.findById(id).isPresent()) {

            User user = userRepository.findById(id).get();
            if (user.getRemindKey().equals(key)) {
                user.setRemindKey(null);
                user.setPassword(passwordEncoder.encode(password));
                userRepository.save(user);
                return true;
            } else
                return false;
        } else
            return false;
    }

    public boolean changeIsEnabled(long id, boolean isEnabled) {
        if (userRepository.findById(id).isPresent()) {
            User user = userRepository.findById(id).get();
            user.setEnabled(isEnabled);
            userRepository.save(user);
            return true;
        } else
            return false;
    }

    public boolean setRoles(long id, String[] roles) {
        if (userRepository.findById(id).isPresent()) {

            User user = userRepository.findById(id).get();
            List<Role> roleList = new ArrayList<>();

            Arrays.stream(roles).forEach((role) -> {
                roleRepository.findByName(role).ifPresentOrElse(roleList::add, () -> {
                    roleList.add(roleRepository.save(new Role(role)));
                });
            });

            user.setRoles(roleList);
            userRepository.save(user);

            return true;
        } else
            return false;
    }
}

