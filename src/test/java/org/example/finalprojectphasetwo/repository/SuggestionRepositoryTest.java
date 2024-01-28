package org.example.finalprojectphasetwo.repository;

import org.apache.coyote.BadRequestException;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.enumeration.SpecialistStatus;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.entity.users.Customer;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.service.AdminService;
import org.example.finalprojectphasetwo.service.OrderService;
import org.example.finalprojectphasetwo.service.SuggestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class SuggestionRepositoryTest {

    @Autowired
    private SuggestionService underTest;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SpecialistRepository specialistRepository;
    @Autowired
    private AdminService adminService;

    private void suggestionInit(Specialist specialist, Order order) {
        underTest.save(Suggestion
                .builder()
                .specialist(specialist)
                .suggestedPrice(12.12)
                .order(order)
                .build());
    }

    private Order orderInit(Customer customer) {
        return orderService.save(
                Order
                        .builder()
                        .customer(customer)
                        .build()
        );
    }

    private Specialist specialistInit() {
        Specialist specialist = new Specialist();
        specialist.setFirstName("SpecialistFirstname");
        specialist.setLastName("SpecialistLastname");
        specialist.setEmailAddress("Specialist@gmail.com");
        specialist.setUsername("SpecialistUsername");
        specialist.setPassword("s1234567");
        specialist.setSpecialistStatus(SpecialistStatus.ACCEPTED);
        specialist.setStar(4);
        return specialistRepository.save(specialist);
    }

    private Customer customerInit() {
        return customerRepository.save(
                new Customer(
                        "CustomerFirstname",
                        "CustomerLastname",
                        "Customerr@gmail.com",
                        "Customer1",
                        "Custome1"
                )
        );
    }

    @Test
    void findSuggestionsByCustomerAndOrderBySuggestionPrice() {
//      given
        Customer customer = customerRepository.save(
                new Customer(
                        "CustomerFirstname",
                        "CustomerLastname",
                        "Customer@gmail.com",
                        "Customer",
                        "Custome1"
                )
        );

        Order order = orderInit(customer);

        underTest.save(Suggestion
                .builder()
                .suggestedPrice(12.12)
                .order(order)
                .build());
//      when
        boolean expected = underTest.findSuggestionsByCustomerAndOrderBySuggestionPrice(customer).isEmpty();
//      then
        assertThat(expected).isFalse();
    }

    @Test
    void findSuggestionsByCustomerAndOrderBySpecialistScore() throws BadRequestException {
//      given
        Customer customer = customerInit();

        Specialist specialist = specialistInit();
        Set<Specialist> specialists = new HashSet<>();
        specialists.add(specialist);

        Order order = orderInit(customer);

        suggestionInit(specialist, order);

        SubService subService = SubService
                .builder()
                .subServiceTitle("title")
                .specialists(specialists)
                .basePrice(12.12)
                .build();
        adminService.addSpecialistToSubServiceByAdmin(specialist, subService);
//      when
        boolean expected = underTest.findSuggestionsByCustomerAndOrderBySpecialistScore(customer).isEmpty();
//      then
        assertThat(expected).isFalse();
    }

}