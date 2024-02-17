package org.example.finalprojectphasetwo.service.impl;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.dto.request.ChangePasswordRequest;
import org.example.finalprojectphasetwo.dto.request.SearchForUsers;
import org.example.finalprojectphasetwo.entity.users.User;
import org.example.finalprojectphasetwo.exception.DuplicateException;
import org.example.finalprojectphasetwo.exception.InvalidInputException;
import org.example.finalprojectphasetwo.exception.NotFoundException;
import org.example.finalprojectphasetwo.exception.NotMatchPasswordException;
import org.example.finalprojectphasetwo.repository.UserRepository;
import org.example.finalprojectphasetwo.service.UserService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public void changePassword(T user, ChangePasswordRequest password) {
        validPasswordCheck(user, password);
        user.setPassword(password.getPassword());
        userRepository.save(user);
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

    @Override
    public List<T> search(SearchForUsers search) {
        return userRepository.findAll(
                getUserSpecification(search)
        );
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

    private static <T extends User> void validPasswordCheck(T user, ChangePasswordRequest password) {
        if (user == null && password == null)
            throw new NotFoundException("USERNAME OR PASSWORD IS NULL !");
        assert user != null;
        if (Objects.equals(user.getPassword(), password.getPassword()))
            throw new InvalidInputException("SAME PASSWORD ! ");
        if (!password.getPassword().equals(password.getConfirmPassword()))
            throw new NotMatchPasswordException("NOT MATCH PASSWORD");
    }
}