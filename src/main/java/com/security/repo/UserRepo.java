package com.security.repo;

import com.security.entity.User;
import com.security.exception.UserNotFound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email) throws UserNotFound;

    Optional<User> findByEmailAndPassword(String email, String password);
}
