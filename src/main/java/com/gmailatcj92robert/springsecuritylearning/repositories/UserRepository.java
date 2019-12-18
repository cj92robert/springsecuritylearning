package com.gmailatcj92robert.springsecuritylearning.repositories;

import com.gmailatcj92robert.springsecuritylearning.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Override
    Page<User> findAll(Pageable pageable);


    long getUserIdByUsername(String username);

    boolean existsUserById(long id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update User u set u.email =?2 where u.id = ?1")
    User updateEmailById(long id, String email);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update User u set u.password =?2 where u.id = ?1 ")
    User updatePasswordById(long id, String password);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update  User u set u.name = ?2 , u.lastname = ?3 where u.id = ?1")
    User updateFirstNameAndLastNameById(long id, String firstname, String lastname);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update User u set u.remindKey =?2 where u.id = ?1")
    void setRemindPasswordKey(long id, long key);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update User u set u.activationKey =?2 where u.id =?1")
    void setActivationKey(long id, long activationKey);
}
