package org.example.finalprojectphasetwo.service;

import org.example.finalprojectphasetwo.dto.request.SearchForUsers;
import org.example.finalprojectphasetwo.entity.users.User;

import java.util.List;

@SuppressWarnings("unused")
public interface SearchUsersService extends UserService<User> {

    List<User> searchForUsers(SearchForUsers search);
}