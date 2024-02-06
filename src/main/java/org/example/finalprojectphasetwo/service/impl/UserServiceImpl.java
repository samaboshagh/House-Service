package org.example.finalprojectphasetwo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.entity.users.User;
import org.example.finalprojectphasetwo.exception.DuplicateException;
import org.example.finalprojectphasetwo.exception.NotFoundException;
import org.example.finalprojectphasetwo.repository.UserRepository;
import org.example.finalprojectphasetwo.service.UserService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Transactional
@RequiredArgsConstructor
public class UserServiceImpl<T extends User, R extends UserRepository<T>>
        implements UserService<T> {

   protected final R userRepository;

    @Override
    public T findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundException("USER NOT FOUND !")
        );
    }

    @Transactional
    @Override
    public void changePassword(T user, String password) {
        if (user != null && password != null) {
            if (!Objects.equals(user.getPassword(), password)) {
                user.setPassword(password);
                userRepository.save(user);
            } else throw new IllegalStateException("SAME PASSWORD ! ");
        } else throw new NullPointerException("USERNAME OR PASSWORD IS NULL !");
    }

    public void checkUsernameAndEmailForRegistration(T registration) {
        if (userRepository.existsByUsername(registration.getUsername()))
            throw new DuplicateException(
                    "DUPLICATE USERNAME !"
            );
        if (userRepository.existsByEmailAddress(registration.getEmailAddress()))
            throw new DuplicateException(
                    "DUPLICATE EMAIL ADDRESS !"
            );
    }
}