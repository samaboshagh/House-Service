package org.example.finalprojectphasetwo.service.impl;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.dto.request.EditPriceAndDescriptionRequest;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.enumeration.Role;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.entity.users.Customer;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.exception.NotFoundException;
import org.example.finalprojectphasetwo.repository.SubServiceRepository;
import org.example.finalprojectphasetwo.service.SearchUsersService;
import org.example.finalprojectphasetwo.service.SubServiceService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class SubServiceServiceImpl implements SubServiceService {

    private final SubServiceRepository repository;

    private final SearchUsersService userService;

    @Transactional
    @Override
    public SubService save(SubService subService) {
        return repository.save(subService);
    }

    @Override
    public List<SubService> findAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public void editDescriptionAndPrice(EditPriceAndDescriptionRequest request) {
        SubService subService = findBySubServiceTitle(request.getSubServiceTitle());
        subService.setDescription(request.getDescription());
        subService.setBasePrice(request.getBasePrice());
        repository.save(subService);
    }

    @Override
    public Boolean existsBySubServiceTitle(String serviceTitle) {
        return repository.existsBySubServiceTitle(serviceTitle);
    }

    @Override
    public SubService findBySubServiceTitle(String serviceTitle) {
        return repository.findBySubServiceTitle(serviceTitle).orElseThrow(
                () -> new NotFoundException("SUB SERVICE NOT FOUND !")
        );
    }

    @Override
    public List<SubService> historyOfSubServicesForCurrentUser(String username) {
        if (Objects.equals(username, "admin")) return null;
        Specification<SubService> subServiceByUser = findSubServiceByUser(username);
        return repository.findAll(subServiceByUser);
    }

    private Specification<SubService> findSubServiceByUser(String username) {

        return (root, query, criteriaBuilder) -> {

            if (userService.findByUsername(username).getRole().equals(Role.ROLE_SPECIALIST)) {
                Join<SubService, Specialist> specialistJoin = root.join("specialists", JoinType.INNER);
                return criteriaBuilder.equal(specialistJoin.get("username"), username);

            } else if (userService.findByUsername(username).getRole().equals(Role.ROLE_CUSTOMER)) {
                Join<SubService, Order> subServiceOrderJoin = root.join("order", JoinType.INNER);
                Join<Order, Customer> customerJoin = subServiceOrderJoin.join("customer", JoinType.INNER);
                return criteriaBuilder.equal(customerJoin.get("username"), username);

            } else
                return null;
        };
    }
}