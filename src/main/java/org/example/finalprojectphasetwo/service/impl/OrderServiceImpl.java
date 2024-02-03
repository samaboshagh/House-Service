package org.example.finalprojectphasetwo.service.impl;

import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.exception.InvalidInputException;
import org.example.finalprojectphasetwo.exception.NotFoundException;
import org.example.finalprojectphasetwo.exception.WrongTimeException;
import org.example.finalprojectphasetwo.repository.OrderRepository;
import org.example.finalprojectphasetwo.service.OrderService;
import org.example.finalprojectphasetwo.dto.OrderDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public void addOrder(OrderDto orderDto) {
        if (orderDto.getTimeOfOrder().isBefore(LocalDate.now())) throw new WrongTimeException("INVALID DATE !");
        Order order = Order.builder()
                .description(orderDto.getDescription())
                .suggestedPrice(orderDto.getSuggestedPrice())
                .timeOfOrder(orderDto.getTimeOfOrder())
                .address(orderDto.getAddress())
                .status(OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_SPECIALIST)
                .customer(orderDto.getCustomer())
                .subService(orderDto.getSubService())
                .build();
        if (checkPrice(order, orderDto) && checkValidCustomer(orderDto)) {
            repository.save(order);
        } else throw new InvalidInputException("BASE PRICE IS MORE THAN SUGGESTED PRICE ! ");
    }

    private boolean checkPrice(Order order, OrderDto orderDto) {
        if (order.getSubService().getBasePrice() == null) throw new NotFoundException("SUB SERVICE IS NULL !");
        return order.getSubService().getBasePrice() < orderDto.getSuggestedPrice();
    }

    boolean checkValidCustomer(OrderDto orderDto) {
        if (orderDto.getCustomer() != null) return true;
        else throw new NotFoundException("CUSTOMER IS NULL !");
    }


    //  this method will use in controller and list that returns going to use for addSuggestionToOrderBySpecialist() method
    @Override
    public List<Order> findOrderWithWaitingStatusBySpecialist(Specialist specialist) {
        if (!repository.findOrdersBySpecialist(specialist).isEmpty()) {
            return repository.findOrdersBySpecialist(specialist)
                    .stream()
                    .filter(order -> order.getStatus()
                                             .equals(OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_SPECIALIST) ||
                                     order.getStatus()
                                             .equals(OrderStatus.WAITING_SPECIALIST_SELECTION))
                    .toList();
        } else throw new NotFoundException("THIS SPECIALIST DOESN'T HAVE ANY ORDER ! ");
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
    public Optional<Order> findById(Integer orderId) {
        return repository.findById(orderId);
    }

    @Transactional
    @Override
    public Order save(Order order) {
        return repository.save(order);
    }
}