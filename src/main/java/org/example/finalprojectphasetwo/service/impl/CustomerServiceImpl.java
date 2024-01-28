package org.example.finalprojectphasetwo.service.impl;

import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.entity.users.Customer;
import org.example.finalprojectphasetwo.repository.UserRepository;
import org.example.finalprojectphasetwo.service.CustomerService;
import org.example.finalprojectphasetwo.dto.UserSingUpDto;
import org.example.finalprojectphasetwo.service.OrderService;
import org.example.finalprojectphasetwo.service.SuggestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class CustomerServiceImpl
        extends UserServiceImpl<Customer>
        implements CustomerService {
    private final SuggestionService suggestionService;
    private final OrderService orderService;

    public CustomerServiceImpl(UserRepository<Customer> repository, SuggestionService suggestionService, OrderService orderService) {
        super(repository);
        this.suggestionService = suggestionService;
        this.orderService = orderService;
    }

    @Transactional
    @Override
    public void customerSingUp(UserSingUpDto dto) {
        Customer customer = new Customer();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmailAddress(dto.getEmailAddress());
        customer.setUsername(dto.getUsername());
        customer.setPassword(dto.getPassword());
        customer.setActive(true);
        customer.setHasPermission(false);
        customer.setWallet(dto.getWallet());
        repository.save(customer);
    }

    @Override
    public List<Suggestion> findOrdersByCustomerAndOrderBySpecialistScore(Customer customer) {
        return suggestionService.findSuggestionsByCustomerAndOrderBySpecialistScore(customer);
    }

    @Override
    public List<Suggestion> findOrdersByCustomerAndOrderBySuggestionPrice(Customer customer) {
        return suggestionService.findSuggestionsByCustomerAndOrderBySuggestionPrice(customer);
    }

    @Override
    public void chooseSuggestionByCustomer(Order order, Suggestion suggestion) {
        if (suggestion != null && order != null && order.getSuggestions().contains(suggestion)) {
            order.setStatus(OrderStatus.WAITING_FOR_THE_SPECIALIST_TO_COME_TO_YOUR_PLACE);
            orderService.save(order);
        } else throw new NullPointerException("INVALID INFORMATION !");
    }

    @Override
    public void changeOrderStatusToStarted(Order order, Suggestion suggestion) {
        if (changeOrderStatusToStartedValidation(order, suggestion)) {
            order.setStatus(OrderStatus.STARTED);
            orderService.save(order);
        } else throw new NullPointerException("INVALID INFORMATION !");
    }

    private static boolean changeOrderStatusToStartedValidation(Order order, Suggestion suggestion) {
        if (order != null && suggestion != null) {
            if (suggestion.getOrder().equals(order)) {
                return suggestion.getSuggestedStartDate().isAfter(LocalDate.now());
            }
        }
        return false;
    }

    @Override
    public void changeOrderStatusToDone(Order order) {
        if (order != null) {
            order.setStatus(OrderStatus.DONE);
            orderService.save(order);
        } else throw new NullPointerException();
    }
}