package org.example.finalprojectphasetwo.service;

import org.example.finalprojectphasetwo.dto.UserSingUpDto;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.users.Customer;

import java.util.List;
@SuppressWarnings("unused")
public interface CustomerService {

    void customerSingUp(UserSingUpDto dto);

    List<Order> findOrdersByCustomerAndOrderBySpecialistScore(Customer customer);

    List<Order> findOrdersByCustomerAndOrderBySuggestionPrice(Customer customer);

    void chooseSuggestionByCustomer(Integer orderId, Integer suggestionId);

    void changeOrderStatusToStarted(Integer orderId);

    void changeOrderStatusToDone(Integer orderId);
}