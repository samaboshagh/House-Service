package org.example.finalprojectphasetwo.service.impl;

import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.entity.users.Customer;
import org.example.finalprojectphasetwo.repository.UserRepository;
import org.example.finalprojectphasetwo.service.CustomerService;
import org.example.finalprojectphasetwo.dto.UserSingUpDto;
import org.example.finalprojectphasetwo.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl
        extends UserServiceImpl<Customer>
        implements CustomerService {
    private final OrderService orderService;

    public CustomerServiceImpl(UserRepository<Customer> repository, OrderService orderService) {
        super(repository);
        this.orderService = orderService;
    }

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
    public List<Order> findOrdersByCustomerAndOrderBySpecialistScore(Customer customer) {
        return orderService.findOrdersByCustomerAndOrderBySpecialistScore(customer);
    }

    @Override
    public List<Order> findOrdersByCustomerAndOrderBySuggestionPrice(Customer customer) {
        return orderService.findOrdersByCustomerAndOrderBySuggestionPrice(customer);
    }

    @Override
    public void chooseSuggestionByCustomer(Order order, Suggestion suggestion) {
        if (suggestion != null && order != null) {
            order.setSuggestion(suggestion);
            order.setStatus(OrderStatus.WAITING_FOR_THE_SPECIALIST_TO_COME_TO_YOUR_PLACE);
            orderService.save(order);
        } else throw new NullPointerException();
    }

    @Override
    public void changeOrderStatusToStarted(Order order) {
        if (order != null) {
            order.setStatus(OrderStatus.STARTED);
            orderService.save(order);
        } else throw new NullPointerException();
    }

    @Override
    public void changeOrderStatusToDone(Order order) {
        if (order != null) {
            order.setStatus(OrderStatus.DONE);
            orderService.save(order);
        } else throw new NullPointerException();
    }
}