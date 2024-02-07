package org.example.finalprojectphasetwo.service;

import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.dto.request.OrderDto;

import java.util.List;

@SuppressWarnings("unused")
public interface OrderService {

    void addOrder(OrderDto orderDto);

    List<Order> findAll();

    List<Order> findOrderWithWaitingStatusBySpecialist(Specialist specialist);

    void changeOrderStatus(Order order, OrderStatus status);

    Order findById(Integer orderId);

    Order save(Order order);

}