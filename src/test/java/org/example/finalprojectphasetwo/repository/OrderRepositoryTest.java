package org.example.finalprojectphasetwo.repository;

import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


import java.util.HashSet;
import java.util.Set;


@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository underTest;
    @Autowired
    private SpecialistRepository specialistRepository;
    @Autowired
    private SubServiceRepository subServiceRepository;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void findOrdersBySpecialist() {
//      given
        Specialist specialist = new Specialist(
                "SpecialistFirstname",
                "SpecialistLastname",
                "Specialist@gmail.com",
                "SpecialistUsername",
                "s1234567"
                , 5
        );
        Set<Specialist> specialists = new HashSet<>();
        specialists.add(specialist);

        SubService subService = SubService
                .builder()
                .specialists(specialists)
                .build();

        Order order = Order
                .builder()
                .subService(subService)
                .build();

        specialistRepository.save(specialist);
        subServiceRepository.save(subService);
        underTest.save(order);
//      when
        boolean expected = underTest.findOrdersBySpecialist(specialist).isEmpty();
//      then
        assertThat(expected).isFalse();
    }
}