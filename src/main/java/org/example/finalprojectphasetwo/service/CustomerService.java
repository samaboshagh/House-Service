package org.example.finalprojectphasetwo.service;

import org.example.finalprojectphasetwo.dto.UserSingUpDto;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.users.Customer;

import java.util.List;

@SuppressWarnings("unused")
public interface CustomerService {

    void customerSingUp(UserSingUpDto dto);

    List<Suggestion> findOrdersByCustomerAndOrderBySpecialistScore(Customer customer);

    List<Suggestion> findOrdersByCustomerAndOrderBySuggestionPrice(Customer customer);

    void chooseSuggestionByCustomer(Order order, Suggestion suggestion);

    void changeOrderStatusToStarted(Order order, Suggestion suggestion);

    void changeOrderStatusToDone(Order order);
}