package org.example.finalprojectphasetwo.repository;

import org.example.finalprojectphasetwo.entity.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserRepository<T extends User> extends JpaRepository<T, Integer> {

    T findByUsername(String userName);
    Boolean existsByUsernameAndPassword(String username, String password);

}