package org.example.finalprojectphasetwo.service.impl;

import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.entity.users.Customer;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.repository.OrderRepository;
import org.example.finalprojectphasetwo.service.OrderService;
import org.example.finalprojectphasetwo.dto.OrderDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addOrder(OrderDto orderDto) {
        if (orderDto.getTimeOfOrder().isBefore(LocalDate.now())) throw new IllegalStateException();
        Order order = Order.builder()
                .description(orderDto.getDescription())
                .suggestedPrice(orderDto.getSuggestedPrice())
                .timeOfOrder(orderDto.getTimeOfOrder())
                .address(orderDto.getAddress())
                .status(OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_SPECIALIST)
                .customer(orderDto.getCustomer())
                .subService(orderDto.getSubService())
                .build();
        if (orderDto.getSuggestedPrice() < order.getSubService().getBasePrice()) throw new IllegalStateException();
        repository.save(order);

    }

    //  this method will use in controller and list that returns going to use for addSuggestionToOrderBySpecialist()
    @Override
    public List<Order> findOrderWithWaitingStatusBySpecialist(Specialist specialist) {
        return repository.findOrdersBySpecialist(specialist)
                .stream()
                .filter(order -> order.getStatus()
                                         .equals(OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_SPECIALIST) ||
                                 order.getStatus()
                                         .equals(OrderStatus.WAITING_SPECIALIST_SELECTION))
                .toList();
    }

    @Override
    public Optional<Order> findById(Integer orderId) {
        return repository.findById(orderId);
    }

    @Override
    public Order save(Order order) {
        return repository.save(order);
    }

    @Override
    public List<Order> findOrdersByCustomerAndOrderBySuggestionPrice(Customer customer) {
        return repository.findOrdersByCustomerAndOrderBySuggestionPrice(customer);
    }

    @Override
    public List<Order> findOrdersByCustomerAndOrderBySpecialistScore(Customer customer) {
        return repository.findOrdersByCustomerAndOrderBySpecialistScore(customer);
    }
}