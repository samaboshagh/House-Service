package org.example.finalprojectphasetwo.service;

import org.example.finalprojectphasetwo.entity.users.User;

public interface UserService<T extends User> {

    T findByUsername(String username);

    void changePassword(T user, String password);

    void checkUsernameAndEmailForRegistration(T registration);

}