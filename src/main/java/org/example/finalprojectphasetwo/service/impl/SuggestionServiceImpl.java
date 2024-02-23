package org.example.finalprojectphasetwo.service.impl;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.entity.enumeration.Role;
import org.example.finalprojectphasetwo.entity.users.Customer;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.exception.NotFoundException;
import org.example.finalprojectphasetwo.repository.SuggestionRepository;
import org.example.finalprojectphasetwo.service.OrderService;
import org.example.finalprojectphasetwo.service.SearchUsersService;
import org.example.finalprojectphasetwo.service.SuggestionService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Transactional(readOnly = true)
@Service
public class SuggestionServiceImpl implements SuggestionService {

    private final SuggestionRepository suggestionRepository;
    private final OrderService orderService;
    private final SearchUsersService userService;

    public SuggestionServiceImpl(SuggestionRepository suggestionRepository, OrderService orderService, SearchUsersService userService) {
        this.suggestionRepository = suggestionRepository;
        this.orderService = orderService;
        this.userService = userService;
    }

    @Transactional
    @Override
    public Suggestion save(Suggestion suggestion) {
        return suggestionRepository.save(suggestion);
    }

    @Override
    public Suggestion findById(Integer id) {
        return suggestionRepository.findById(id).orElseThrow(
                () -> new NotFoundException("SUGGESTION NOT FOUND !")
        );
    }

    public void addSuggestion(Suggestion suggestion, Order order, Specialist specialist) {
        suggestion.setSpecialist(specialist);
        suggestion.setOrder(order);
        suggestion.setSuggestionCreationDate(LocalDate.now());
        List<Suggestion> suggestions = new ArrayList<>();
        suggestions.add(suggestion);
        suggestionRepository.save(suggestion);
        order.setSuggestions(suggestions);
        order.setStatus(OrderStatus.WAITING_SPECIALIST_SELECTION);
        orderService.save(order);
    }

    @Override
    public List<Suggestion> findSuggestionsByCustomerAndOrderBySuggestionPrice(Customer customer) {
        return suggestionRepository.findSuggestionsByCustomerAndOrderBySuggestionPrice(customer);
    }

    @Override
    public List<Suggestion> findSuggestionsByCustomerAndOrderBySpecialistScore(Customer customer) {
        return suggestionRepository.findSuggestionsByCustomerAndOrderBySpecialistScore(customer);
    }

    @Override
    public List<Suggestion> historyOfSuggestionForCurrentUser(String username) {
        if (Objects.equals(username, "admin")) return null;
        return suggestionRepository.findAll(findSuggestionByUser(username));
    }

    private Specification<Suggestion> findSuggestionByUser(String username) {

        return (root, query, criteriaBuilder) -> {

            Predicate predicate = criteriaBuilder.conjunction();

            if (userService.findByUsername(username).getRole().equals(Role.ROLE_SPECIALIST)) {
                return criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("specialist").get("username"), username));

            } else if (userService.findByUsername(username).getRole().equals(Role.ROLE_CUSTOMER)) {
                Join<Suggestion, Order> subServiceOrderJoin = root.join("order", JoinType.INNER);
                Join<Order, Customer> customerJoin = subServiceOrderJoin.join("customer", JoinType.INNER);
                return criteriaBuilder.equal(customerJoin.get("username"), username);

            } else
                return null;
        };
    }

}