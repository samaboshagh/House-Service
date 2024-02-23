package org.example.finalprojectphasetwo.service;

import org.example.finalprojectphasetwo.dto.request.OrderHistoryDto;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.entity.users.Customer;
import org.example.finalprojectphasetwo.entity.users.Specialist;

import java.util.List;

public interface OrderService {

    void addOrder(SubService subService, Customer customer, Order order);

    List<Order> findAll();

    List<Order> findOrderWithWaitingStatusBySpecialist(Specialist specialist);

    void changeOrderStatus(Order order, OrderStatus status);

    Order findById(Integer orderId);

    Order save(Order order);

    List<Order> historyOfOrdersForUser(OrderHistoryDto dto);

    Long countOfOrders(String username);

    List<Order> findAllByCustomer(Customer customer, OrderStatus status);

    List<Order> findAllBySpecialist(Specialist specialist, OrderStatus status);

}