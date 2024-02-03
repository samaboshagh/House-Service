package org.example.finalprojectphasetwo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.dto.UserSingUpDto;
import org.example.finalprojectphasetwo.entity.users.User;
import org.example.finalprojectphasetwo.exception.DuplicateException;
import org.example.finalprojectphasetwo.exception.InvalidInputException;
import org.example.finalprojectphasetwo.exception.InvalidPasswordException;
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

    @Transactional
    @Override
    public void changePassword(T user, String password) {
        if (user != null && password != null) {
            if (!Objects.equals(user.getPassword(), password)) {
                user.setPassword(password);
                repository.save(user);
            } else throw new IllegalStateException("SAME PASSWORD ! ");
        } else throw new NullPointerException("USERNAME OR PASSWORD IS NULL !");
    }

    public void checkUsernameAndEmailForRegistration(UserSingUpDto registrationDTO) {
        if (repository.existsByUsername(registrationDTO.getUsername()))
            throw new DuplicateException(
                    "DUPLICATE USERNAME !"
            );
        if (repository.existsByEmailAddress(registrationDTO.getEmailAddress()))
            throw new DuplicateException(
                    "DUPLICATE EMAIL ADDRESS !"
            );
    }

    public void checkPassword(UserSingUpDto registrationDTO) {
        if (!registrationDTO.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8}$")) {
            throw new InvalidPasswordException("INVALID PASSWORD");
        }
    }

    @Override
    public void checkValidName(UserSingUpDto registrationDTO) {
        if (!registrationDTO.getFirstName().matches("^[A-Za-z]+$")
        && !registrationDTO.getLastName().matches("^[A-Za-z]+$")) {
            throw new InvalidInputException("INVALID FORMAT FOR NAME");
        }
    }
}