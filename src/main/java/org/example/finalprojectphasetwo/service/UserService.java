package org.example.finalprojectphasetwo.service;

import org.example.finalprojectphasetwo.dto.request.ChangePasswordRequest;
import org.example.finalprojectphasetwo.dto.request.SearchForUsers;
import org.example.finalprojectphasetwo.entity.users.User;

import java.util.List;
import java.util.Optional;

public interface UserService<T extends User> {

    T findByUsername(String username);

    Optional<T> findByUsernameOptional(String username);

    void changePassword(T user, ChangePasswordRequest password);

    void checkUsernameAndEmailForRegistration(T registration);

    List<T> search(SearchForUsers search);

    T findUserByEmailAddress(String emailAddress);

    T save(T user);

    void sendEmail(String emailAddress);

    void confirmEmail(String confirmationToken);
}