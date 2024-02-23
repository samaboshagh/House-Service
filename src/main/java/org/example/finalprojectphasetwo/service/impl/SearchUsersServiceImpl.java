package org.example.finalprojectphasetwo.service.impl;

import org.example.finalprojectphasetwo.dto.request.SearchForUsers;
import org.example.finalprojectphasetwo.entity.users.User;
import org.example.finalprojectphasetwo.repository.ConfirmationTokenRepository;
import org.example.finalprojectphasetwo.repository.UserRepository;
import org.example.finalprojectphasetwo.service.SearchUsersService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class SearchUsersServiceImpl
        extends UserServiceImpl<User, UserRepository<User>>
        implements SearchUsersService {


    public SearchUsersServiceImpl(UserRepository<User> userRepository, EmailService emailService, ConfirmationTokenRepository confirmationTokenRepository, BCryptPasswordEncoder passwordEncoder) {
        super(userRepository, emailService, confirmationTokenRepository, passwordEncoder);
    }

    public List<User> searchForUsers(SearchForUsers search) {
        return search(search);
    }
}