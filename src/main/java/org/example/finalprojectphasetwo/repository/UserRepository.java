package org.example.finalprojectphasetwo.repository;

import org.example.finalprojectphasetwo.entity.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//@NoRepositoryBean
public interface UserRepository<T extends User> extends JpaRepository<T, Integer> {

    Optional<T> findByUsername(String userName);

    Boolean existsByUsername(String username);

    Boolean existsByEmailAddress(String emailAddress);

}