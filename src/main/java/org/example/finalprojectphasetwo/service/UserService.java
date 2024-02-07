package org.example.finalprojectphasetwo.service;

import org.example.finalprojectphasetwo.dto.request.SearchForUsers;
import org.example.finalprojectphasetwo.entity.users.User;

import java.util.List;

public interface UserService<T extends User> {

    T findByUsername(String username);

    void changePassword(T user, String password);

    void checkUsernameAndEmailForRegistration(T registration);

    List<T>  search(SearchForUsers search);

}