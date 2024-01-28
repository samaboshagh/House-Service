package org.example.finalprojectphasetwo.service.impl;

import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.service.CustomerService;
import org.example.finalprojectphasetwo.service.SuggestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceImplTest {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private SuggestionService suggestionService;

    @Test
    void chooseSuggestionByCustomerCheckIfOrderIsNull() {
        assertThrows(NullPointerException.class, () -> customerService.chooseSuggestionByCustomer(null, null));
    }

    @Test
    void testChooseSuggestionByCustomer() {
        // given
        Suggestion suggestion = Suggestion
                .builder()
                .suggestedPrice(12.12)
                .build();
        suggestionService.save(suggestion);
        List<Suggestion> suggestions = new ArrayList<>();
        suggestions.add(suggestion);

        Order order = Order
                .builder()
                .status(OrderStatus.WAITING_FOR_THE_SPECIALIST_TO_COME_TO_YOUR_PLACE)
                .suggestions(suggestions)
                .build();
        orderService.save(order);
        // when
        customerService.chooseSuggestionByCustomer(order, suggestion);
        // then
        Order updatedOrder = orderService.findById(order.getId()).orElse(null);
        assertOrderStatusForChooseSuggestion(updatedOrder);
    }

    @Test
    void changeOrderStatusToStartedCheckIfOrderIsNull() {
        assertThrows(NullPointerException.class, () -> customerService.changeOrderStatusToStarted(null, null));
    }

    @Test
    void testChangeOrderStatusToStarted() {
        // given
        Order order = Order
                .builder()
                .status(OrderStatus.STARTED)
                .build();
        orderService.save(order);

        Suggestion suggestion = Suggestion
                .builder()
                .suggestedPrice(12.12)
                .suggestedStartDate(LocalDate.now().plusDays(1))
                .order(order)
                .build();
        suggestionService.save(suggestion);
        // when
        customerService.changeOrderStatusToStarted(order, suggestion);
        // then
        Order updatedOrder = orderService.findById(order.getId()).orElse(null);
        assertOrderStatusForChangeOrderStatusToStarted(updatedOrder);
    }

    @Test
    void changeOrderStatusToDoneCheckIfOrderIsNull() {
        assertThrows(NullPointerException.class, () -> customerService.changeOrderStatusToDone(null));
    }

    @Test
    void testChangeOrderStatusToDone() {
        // given
        Order order = new Order();
        order.setStatus(OrderStatus.DONE);
        orderService.save(order);
        // when
        customerService.changeOrderStatusToDone(order);
        // then
        Order updatedOrder = orderService.findById(order.getId()).orElse(null);
        assertOrderStatus(updatedOrder);
    }

    private void assertOrderStatusForChooseSuggestion(Order order) {
        assertNotNull(order);
        assertEquals(OrderStatus.WAITING_FOR_THE_SPECIALIST_TO_COME_TO_YOUR_PLACE, order.getStatus());
    }

    private void assertOrderStatusForChangeOrderStatusToStarted(Order order) {
        assertNotNull(order);
        assertEquals(OrderStatus.STARTED, order.getStatus());
    }

    private void assertOrderStatus(Order order) {
        assertNotNull(order);
        assertEquals(OrderStatus.DONE, order.getStatus());
    }
}