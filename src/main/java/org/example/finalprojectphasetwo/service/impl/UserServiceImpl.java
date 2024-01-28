package org.example.finalprojectphasetwo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.entity.users.User;
import org.example.finalprojectphasetwo.repository.UserRepository;
import org.example.finalprojectphasetwo.service.UserService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl<T extends User>
        implements UserService<T> {

    public final UserRepository<T> repository;

    @Override
    public T findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Transactional
    @Override
    public void changePassword(T user, String password) {
        if (user != null && password != null) {
            if (!Objects.equals(user.getPassword(), password)) {
                user.setPassword(password);
                repository.save(user);
            } else throw new IllegalStateException("SAME PASSWORD ! ");
        } else throw  new NullPointerException("USERNAME OR PASSWORD IS NULL !");
    }
}