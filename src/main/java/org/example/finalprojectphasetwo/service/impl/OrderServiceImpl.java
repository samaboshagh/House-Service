package org.example.finalprojectphasetwo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.entity.users.Customer;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.exception.NotFoundException;
import org.example.finalprojectphasetwo.repository.OrderRepository;
import org.example.finalprojectphasetwo.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    @Transactional
    @Override
    public void addOrder(SubService subService, Customer customer, Order order) {
        order.setStatus(OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_SPECIALIST);
        order.setCustomer(customer);
        order.setSubService(subService);
        repository.save(order);
    }

    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }

    //  this method will use in controller and list that returns going to use for addSuggestionToOrderBySpecialist() method
    @Override
    public List<Order> findOrderWithWaitingStatusBySpecialist(Specialist specialist) {
        if (repository.findOrdersBySpecialist(specialist).isEmpty())
            throw new NotFoundException("THIS SPECIALIST DOESN'T HAVE ANY ORDER ! ");
        return repository.findOrdersBySpecialist(specialist)
                .stream()
                .filter(
                        order -> order.getStatus()
                                         .equals(OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_SPECIALIST) ||
                                 order.getStatus()
                                         .equals(OrderStatus.WAITING_SPECIALIST_SELECTION)
                )
                .toList();
    }

    @Override
    @Transactional
    public void changeOrderStatus(Order order, OrderStatus status) {
        if (order != null && status != null) {
            order.setStatus(status);
            repository.save(order);
        }
    }

    @Override
    public Order findById(Integer orderId) {
        return repository.findById(orderId).orElseThrow(
                () -> new NotFoundException("ORDER NOT FOUND !")
        );
    }

    @Transactional
    @Override
    public Order save(Order order) {
        return repository.save(order);
    }

}