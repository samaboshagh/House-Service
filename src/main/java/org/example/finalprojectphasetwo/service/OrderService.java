package org.example.finalprojectphasetwo.service;

import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.users.Customer;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.dto.OrderDto;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public interface OrderService {

    void addOrder(OrderDto orderDto);

    List<Order> findOrderWithWaitingStatusBySpecialist(Specialist specialist);

    Optional<Order> findById(Integer orderId);

    Order save(Order order);

}