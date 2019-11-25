package com.gmailatcj92robert.springsecuritylearning.repositories;

import com.gmailatcj92robert.springsecuritylearning.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
}
