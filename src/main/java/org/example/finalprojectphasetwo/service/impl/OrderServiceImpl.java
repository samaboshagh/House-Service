package org.example.finalprojectphasetwo.service.impl;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.dto.request.OrderHistoryDto;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.entity.enumeration.Role;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.entity.users.Customer;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.exception.NotFoundException;
import org.example.finalprojectphasetwo.repository.OrderRepository;
import org.example.finalprojectphasetwo.service.OrderService;
import org.example.finalprojectphasetwo.service.SearchUsersService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    private final SearchUsersService userService;

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
    public List<Order> findAllByCustomer(Customer customer, OrderStatus status) {
        if (repository.findAllByCustomer(customer).isEmpty())
            throw new NotFoundException("THIS CUSTOMER DOESN'T HAVE ANY ORDER ! ");
        return repository.findAllByCustomer(customer)
                .stream()
                .filter(
                        order ->
                                order.getStatus().equals(status)
                )
                .toList();
    }

    @Override
    public List<Order> findAllBySpecialist(Specialist specialist, OrderStatus status) {
        if (repository.findOrdersBySpecialist(specialist).isEmpty())
            throw new NotFoundException("THIS SPECIALIST DOESN'T HAVE ANY ORDER ! ");
        return repository.findOrdersBySpecialist(specialist)
                .stream()
                .filter(
                        order ->
                                order.getStatus().equals(status)
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

    @Override
    public List<Order> historyOfOrdersForUser(OrderHistoryDto dto) {
        return repository.findAll(findFilteredOrdersForUser(dto));
    }

    private Specification<Order> findFilteredOrdersForUser(OrderHistoryDto dto) {
        return (root, query, cb) -> {

            Predicate predicate = cb.conjunction();

            if (dto.getStartDate() != null && dto.getEndDate() != null) {
                predicate = cb.and(predicate, cb.between(root.get("timeOfOrder"), dto.getStartDate(), dto.getEndDate()));
            }

            if (dto.getStatus() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), dto.getStatus()));
            }

            if (dto.getMainServiceTitle() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("subService").get("mainService").get("title"), dto.getMainServiceTitle()));
            }

            if (dto.getSubServiceTitle() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("subService").get("subServiceTitle"), dto.getSubServiceTitle()));
            }
            return predicate;
        };
    }

    @Override
    public Long countOfOrders(String username) {
        if (Objects.equals(username, "admin")) return null;
        Specification<Order> spec = countOrdersForSpecialist(username);
        return repository.count(spec);
    }

    private Specification<Order> countOrdersForSpecialist(String username) {
        return (root, query, criteriaBuilder) -> {

            if (userService.findByUsername(username).getRole().equals(Role.ROLE_SPECIALIST)) {
                Join<Order, Suggestion> suggestionJoin = root.join("suggestions", JoinType.INNER);
                return criteriaBuilder.equal(suggestionJoin.get("specialist").get("username"), username);

            } else if (userService.findByUsername(username).getRole().equals(Role.ROLE_CUSTOMER)) {
                return criteriaBuilder.equal(root.get("customer").get("username"), username);

            } else
                return null;
        };
    }

}