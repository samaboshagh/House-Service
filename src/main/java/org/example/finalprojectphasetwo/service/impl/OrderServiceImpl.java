package org.example.finalprojectphasetwo.service.impl;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.exception.InvalidInputException;
import org.example.finalprojectphasetwo.exception.NotFoundException;
import org.example.finalprojectphasetwo.exception.WrongTimeException;
import org.example.finalprojectphasetwo.repository.OrderRepository;
import org.example.finalprojectphasetwo.service.CustomerService;
import org.example.finalprojectphasetwo.service.OrderService;
import org.example.finalprojectphasetwo.dto.request.OrderDto;
import org.example.finalprojectphasetwo.service.SubServiceService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Transactional(readOnly = true)
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final CustomerService customerService;
    private final SubServiceService subServiceService;
    private final Validator validator;

    public OrderServiceImpl(OrderRepository repository, @Lazy CustomerService customerService, SubServiceService subServiceService, Validator validator) {
        this.repository = repository;
        this.customerService = customerService;
        this.subServiceService = subServiceService;
        this.validator = validator;
    }

    @Transactional
    @Override
    public void addOrder(OrderDto orderDto) {
        Set<ConstraintViolation<OrderDto>> violations = validator.validate(orderDto);
        if (!violations.isEmpty()) {
            throw new InvalidInputException("INVALID INFORMATION !");
        }
        if (orderDto.getTimeOfOrder().isBefore(LocalDate.now())) throw new WrongTimeException("INVALID DATE !");
        Order order = Order.builder()
                .description(orderDto.getDescription())
                .suggestedPrice(orderDto.getSuggestedPrice())
                .timeOfOrder(orderDto.getTimeOfOrder())
                .address(orderDto.getAddress())
                .status(OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_SPECIALIST)
                .customer(customerService.findByUsername(orderDto.getCustomerUsername()))
                .subService(subServiceService.findBySubServiceTitle(orderDto.getSubServiceTitle()))
                .build();
        if (checkPrice(order, orderDto) && checkValidCustomer(orderDto)) {
            repository.save(order);
        } else throw new InvalidInputException("BASE PRICE IS MORE THAN SUGGESTED PRICE ! ");
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

    private boolean checkPrice(Order order, OrderDto orderDto) {
        if (order.getSubService().getBasePrice() == null) throw new NotFoundException("SUB SERVICE IS NULL !");
        return order.getSubService().getBasePrice() < orderDto.getSuggestedPrice();
    }

    boolean checkValidCustomer(OrderDto orderDto) {
        if (orderDto.getCustomerUsername() != null) return true;
        else throw new NotFoundException("CUSTOMER IS NULL !");
    }
}