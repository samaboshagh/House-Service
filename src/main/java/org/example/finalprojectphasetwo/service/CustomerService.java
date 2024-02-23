package org.example.finalprojectphasetwo.service;

import org.example.finalprojectphasetwo.dto.request.ChangePasswordRequest;
import org.example.finalprojectphasetwo.dto.request.PayWithCardDto;
import org.example.finalprojectphasetwo.entity.Comment;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.entity.services.MainService;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.entity.users.Customer;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface CustomerService {

    Customer customerSingUp(Customer customer);

    void changePassword(ChangePasswordRequest password);

    Customer findByUsername(String username);

    List<MainService> showAllMainServices();

    List<SubService> showAllSubServices();

    void addOrder(Double suggestedPrice, String customerUsername, String subServiceTitle, LocalDate timeOfOrder, Order order);

    List<Suggestion> findSuggestionByCustomerAndOrderBySpecialistScore(String customerUsername);

    List<Suggestion> findSuggestionsByCustomerAndOrderBySuggestionPrice(String customerUsername);

    void chooseSuggestionByCustomer(Integer SuggestionId);

    void changeOrderStatusToStarted(Integer suggestionId);

    void changeOrderStatusToDone(Integer orderId);

    void payWithWalletCredit(Integer suggestionId);

    void payWithCard(PayWithCardDto payWithCardDto);

    void addComment(Comment comment, Integer suggestionId);

    List<Order> findAllOrdersByCustomer(String username, OrderStatus status);

    Double seeCredit(String username);

    void increaseCredit(Double amount, String username);
}