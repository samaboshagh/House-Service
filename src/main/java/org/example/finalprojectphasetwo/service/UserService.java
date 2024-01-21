package org.example.finalprojectphasetwo.service;

import org.example.finalprojectphasetwo.entity.users.User;

public interface UserService<T extends User> {

    T findByUsername(String username);

    boolean existsByUsernameAndPassword(String username, String password);


    void changePassword(T user, String password);

}
