package com.gmailatcj92robert.springsecuritylearning.repositories;

import com.gmailatcj92robert.springsecuritylearning.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
