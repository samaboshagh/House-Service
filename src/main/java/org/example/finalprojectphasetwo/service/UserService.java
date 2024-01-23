package org.example.finalprojectphasetwo.service;

import org.example.finalprojectphasetwo.entity.users.User;

public interface UserService<T extends User> {

    T findByUsername(String username);

    boolean existsByUsername(String username);


    void changePassword(T user, String password);

}
