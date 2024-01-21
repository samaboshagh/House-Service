package org.example.finalprojectphasetwo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.repository.OrderRepository;
import org.example.finalprojectphasetwo.service.OrderService;
import org.example.finalprojectphasetwo.service.dto.OrderDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    @Override
    public void addOrder(OrderDto orderDto) {

        Order order = new Order();
        order.setDescription(orderDto.getDescription());
        order.setSuggestedPrice(orderDto.getSuggestedPrice());
        if (orderDto.getTimeOfOrder().isBefore(LocalDate.now())) throw new IllegalStateException();
        order.setTimeOfOrder(orderDto.getTimeOfOrder());
        order.setAddress(orderDto.getAddress());
        order.setStatus(OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_SPECIALIST);
        order.setCustomer(orderDto.getCustomer());
        order.setSubService(orderDto.getSubService());
        if (orderDto.getSuggestedPrice() < order.getSubService().getBasePrice()) throw new IllegalStateException();
        repository.save(order);

    }
}