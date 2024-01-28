package org.example.finalprojectphasetwo.service.impl;

import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.repository.SubServiceRepository;
import org.example.finalprojectphasetwo.service.SubServiceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SubServiceServiceImplTest {

    @Autowired
    SubServiceRepository subServiceRepository;
    @Autowired
    SubServiceService subServiceService;

    @AfterEach
    void tearDown() {
        subServiceRepository.deleteAll();
    }

    @Test
    @Order(1)
    void testEditDescriptionAndPrice() {
        // given
        SubService subService = SubService
                .builder()
                .description("Old Description")
                .basePrice(100.0)
                .build();
        subServiceRepository.save(subService);
        // when
        subServiceService.editDescriptionAndPrice(subService.getId(), "New Description", 150.0);
        // then
        SubService updatedSubService = subServiceRepository.findById(subService.getId()).orElse(null);
        assertNotNull(updatedSubService);
        assertEquals("New Description", updatedSubService.getDescription());
        assertEquals(150.0, updatedSubService.getBasePrice());
    }

    @Test
    @Order(2)
    void testEditDescriptionAndPriceWithNonexistentSubService() {
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> subServiceService.editDescriptionAndPrice(0, "New Description", 150.0));
        assertEquals("SUB SERVICE NOT FOUND", exception.getMessage());
        assertEquals(0, subServiceRepository.count());
    }
}