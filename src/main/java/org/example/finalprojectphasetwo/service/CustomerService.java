package org.example.finalprojectphasetwo.service;

import org.example.finalprojectphasetwo.dto.request.OrderDto;
import org.example.finalprojectphasetwo.dto.request.PayWithCardDto;
import org.example.finalprojectphasetwo.entity.Comment;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.services.MainService;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.entity.users.Customer;

import java.util.List;

public interface CustomerService {

    void customerSingUp(Customer customer);

    void changePassword(String username, String password);

    Customer findByUsername(String username);

    List<MainService> showAllMainServices();

    List<SubService> showAllSubServices();

    void addOrder(OrderDto orderDto);

    List<Suggestion> findSuggestionByCustomerAndOrderBySpecialistScore(String customerUsername);

    List<Suggestion> findSuggestionsByCustomerAndOrderBySuggestionPrice(String customerUsername);

    void chooseSuggestionByCustomer(Integer SuggestionId);

    void changeOrderStatusToStarted(Integer suggestionId);

    void changeOrderStatusToDone(Integer orderId);

    void payWithWalletCredit(Integer suggestionId);

    void payWithCard(PayWithCardDto payWithCardDto);

    void addComment(Comment comment, Integer orderId);
}