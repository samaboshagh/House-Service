package org.example.finalprojectphasetwo.service.impl;

import org.apache.coyote.BadRequestException;
import org.example.finalprojectphasetwo.dto.OrderDto;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.entity.enumeration.SpecialistStatus;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.entity.users.Customer;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.repository.CustomerRepository;
import org.example.finalprojectphasetwo.repository.OrderRepository;
import org.example.finalprojectphasetwo.repository.SubServiceRepository;
import org.example.finalprojectphasetwo.service.SpecialistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SubServiceRepository subServiceRepository;
    @Autowired
    private SpecialistService specialistService;
    @Autowired
    private AdminServiceImpl adminService;

    private Customer initializeCustomer() {
        Customer customer = new Customer
                (
                        "customerFirstname",
                        "customerLastname",
                        "customer@gmail.com",
                        "customer",
                        "custom12"
                );
        return customerRepository.save(customer);
    }

    private SubService initializeService() {
        SubService subService = SubService
                .builder()
                .basePrice(15.0)
                .build();
        return subServiceRepository.save(subService);
    }

    private void initialOrders(SubService subService) {
        Order orderSecond = Order
                .builder()
                .status(OrderStatus.WAITING_SPECIALIST_SELECTION)
                .subService(subService)
                .build();
        orderRepository.save(orderSecond);

        Order orderFirst = Order
                .builder()
                .status(OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_SPECIALIST)
                .subService(subService)
                .build();
        orderRepository.save(orderFirst);
    }


    @BeforeEach
    void shotDown() {
        orderRepository.deleteAll();
    }

    @Test
    void testAddOrder() {
        // given
        Customer customer = new Customer
                (
                        "customerFirstname",
                        "customerLastname",
                        "customer1@gmail.com",
                        "customer",
                        "custom12"
                );
        customerRepository.save(customer);
        OrderDto orderDto = OrderDto.builder()
                .description("Test Order")
                .suggestedPrice(150.0)
                .timeOfOrder(LocalDate.now().plusDays(1))
                .address("Test Address")
                .customer(customer)
                .subService(initializeService())
                .build();
        // when
        orderService.addOrder(orderDto);
        // then
        Order savedOrder = orderRepository.findAll().get(0);
        assertNotNull(savedOrder);
        assertEquals("Test Order", savedOrder.getDescription());
        assertEquals(150.0, savedOrder.getSuggestedPrice());
        assertEquals(LocalDate.now().plusDays(1), savedOrder.getTimeOfOrder());
        assertEquals("Test Address", savedOrder.getAddress());
        assertEquals(OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_SPECIALIST, savedOrder.getStatus());
        assertNotNull(savedOrder.getCustomer());
        assertNotNull(savedOrder.getSubService());
    }

    @Test
    void testAddOrderWithPastTimeOfOrder() {
        // given
        OrderDto orderDto = OrderDto
                .builder()
                .suggestedPrice(12.12)
                .subService(initializeService())
                .timeOfOrder(LocalDate.now().minusDays(1))
                .build();
        // when and then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> orderService.addOrder(orderDto));
        assertEquals(0, orderRepository.count());
        assertEquals("INVALID DATE !", exception.getMessage());
    }

    @Test
    void testAddOrderWithInvalidSuggestedPrice() {
        // given
        OrderDto orderDto = OrderDto
                .builder()
                .timeOfOrder(LocalDate.now().plusDays(1))
                .subService(initializeService())
                .customer(initializeCustomer())
                .suggestedPrice(11.0)
                .build();
        // when and then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> orderService.addOrder(orderDto));
        assertEquals("BASE PRICE IS MORE THAN SUGGESTED PRICE ! ", exception.getMessage());
        assertEquals(0, orderRepository.count());
    }

    @Test
    public void testCheckValidCustomerNull() {
        // given
        OrderDto orderDto = OrderDto
                .builder()
                .timeOfOrder(LocalDate.now().plusDays(1))
                .subService(initializeService())
                .suggestedPrice(11.0)
                .build();
        // Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> orderService.checkValidCustomer(orderDto));
        assertEquals("CUSTOMER IS NULL !", exception.getMessage());
    }

    @Test
    public void testFindOrderWithWaitingStatusBySpecialist() {
        // given
        Specialist specialist = new Specialist();
        specialist.setFirstName("SpecialistFirstname");
        specialist.setLastName("SpecialistLastname");
        specialist.setEmailAddress("Specialistt@gmail.com");
        specialist.setUsername("Specialist");
        specialist.setActive(true);
        specialist.setPassword("Spe12345");
        specialist.setSpecialistStatus(SpecialistStatus.ACCEPTED);
        specialistService.save(specialist);

        Set<Specialist> specialists = new HashSet<>();
        specialists.add(specialist);
        SubService subService = SubService
                .builder()
                .specialists(specialists)
                .build();
        subServiceRepository.save(subService);
        adminService.addSpecialistToSubServiceByAdmin(specialist, subService);
        initialOrders(subService);
        // when
        List<Order> result = orderService.findOrderWithWaitingStatusBySpecialist(specialist);
        // then
        assertEquals(2, result.size());
    }

    @Test
    public void testFindOrderWithWaitingStatusBySpecialistNoOrders() {
        // given
        Specialist specialist = new Specialist(
                "SpecialistFirstname",
                "SpecialistLastname",
                "Specialist@gmail.com",
                "Specialist",
                "Spe12345",
                4
        );
        specialistService.save(specialist);
        //when and then
        NullPointerException exception = assertThrows(NullPointerException.class, () ->
                orderService.findOrderWithWaitingStatusBySpecialist(specialist));
        assertEquals("THIS SPECIALIST DOESN'T HAVE ANY ORDER ! ", exception.getMessage());
    }
}