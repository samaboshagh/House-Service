package org.example.finalprojectphasetwo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.entity.users.User;
import org.example.finalprojectphasetwo.repository.UserRepository;
import org.example.finalprojectphasetwo.service.UserService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
public class UserServiceImpl<T extends User>
        implements UserService<T> {

    public final UserRepository<T> repository;

    @Override
    public T findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public boolean existsByUsernameAndPassword(String username, String password) {
        return repository.existsByUsernameAndPassword(username, password);
    }

    @Override
    public void changePassword(T user, String password) {
        user.setPassword(password);
        repository.save(user);
    }
}