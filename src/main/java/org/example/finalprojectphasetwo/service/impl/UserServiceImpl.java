package org.example.finalprojectphasetwo.service.impl;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.dto.request.ChangePasswordRequest;
import org.example.finalprojectphasetwo.dto.request.SearchForUsers;
import org.example.finalprojectphasetwo.entity.ConfirmationToken;
import org.example.finalprojectphasetwo.entity.users.User;
import org.example.finalprojectphasetwo.exception.*;
import org.example.finalprojectphasetwo.repository.ConfirmationTokenRepository;
import org.example.finalprojectphasetwo.repository.UserRepository;
import org.example.finalprojectphasetwo.service.UserService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
public class UserServiceImpl<T extends User, R extends UserRepository<T>>
        implements UserService<T> {

    protected final R userRepository;

    protected final EmailService emailService;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    protected final BCryptPasswordEncoder passwordEncoder;

    @Override
    public T findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundException(String.format("USER %s NOT FOUND !", username))
        );
    }

    @Override
    public Optional<T> findByUsernameOptional(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    @Override
    public void changePassword(T user, ChangePasswordRequest password) {
        validPasswordCheck(user, password);
        user.setPassword(passwordEncoder.encode(
                password.getPassword()
        ));
        userRepository.save(user);
    }

    public void checkUsernameAndEmailForRegistration(T registration) {
        if (userRepository.existsByUsername(registration.getUsername()))
            throw new DuplicateException(
                    String.format(
                            "DUPLICATE %s !", registration.getUsername())
            );
        if (userRepository.existsByEmailAddress(registration.getEmailAddress()))
            throw new DuplicateException(
                    String.format(
                            "DUPLICATE %s !", registration.getEmailAddress())
            );
    }

    @Override
    public List<T> search(SearchForUsers search) {
        return userRepository.findAll(
                getUserSpecification(search)
        );
    }

    @Override
    public T findUserByEmailAddress(String emailAddress) {
        return userRepository.findByEmailAddress(emailAddress).orElse(null);
    }

    @Override
    public T save(T user) {
        return userRepository.save(user);
    }

    @Override
    public void sendEmail(String emailAddress) {
        T user = findUserByEmailAddress(emailAddress);

        ConfirmationToken confirmationToken = new ConfirmationToken(user,true);

        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("samaboshagh1380@gmail.com");
        mailMessage.setTo(emailAddress);
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
                            + "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());

        emailService.sendEmail(mailMessage);

    }

    @Override
    public void confirmEmail(String confirmationToken) {

        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        confirmEmailValidation(token);

        token.setActive(false);
        confirmationTokenRepository.save(token);

        T user = findUserByEmailAddress(token.getUser().getEmailAddress());
        user.setEnabled(true);
        save(user);
    }

    private static void confirmEmailValidation(ConfirmationToken token) {
        if (token == null)
            throw new ConfirmEmailException("EMAIL NOT CONFIRMED");

        if (!token.isActive())
            throw new ConfirmEmailException("THIS TOKEN IS ALREADY USED");
    }

    private static <T extends User> void validPasswordCheck(T user, ChangePasswordRequest password) {
        if (user == null && password == null)
            throw new NotFoundException("USERNAME OR PASSWORD IS NULL !");
        assert user != null;
        if (Objects.equals(user.getPassword(), password.getPassword()))
            throw new InvalidInputException("SAME PASSWORD ! ");
        if (!Objects.equals(password.getPassword(), password.getConfirmPassword()))
            throw new NotMatchPasswordException("NOT MATCH PASSWORD");
    }

    private Specification<T> getUserSpecification(SearchForUsers search) {
        return (root, query, criteriaBuilder) -> {

            Predicate predicate = criteriaBuilder.conjunction();

            if (search.getFirstName() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("firstName"), search.getFirstName()));
            }

            if (search.getLastName() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("lastName"), search.getLastName()));
            }

            if (search.getEmailAddress() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("emailAddress"), search.getEmailAddress()));
            }

            if (search.getSpecialization() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("specialization"), search.getSpecialization()));
            }

            if (search.getStar() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("star"), search.getStar()));
            }

            if (search.getUsername() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("username"), search.getUsername()));
            }

            if (search.getRole() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("role"), search.getRole()));
            }
            return predicate;
        };
    }
}