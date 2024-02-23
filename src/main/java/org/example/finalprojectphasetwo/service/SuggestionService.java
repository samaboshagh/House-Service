package org.example.finalprojectphasetwo.service;

import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.entity.users.Customer;
import org.example.finalprojectphasetwo.entity.users.Specialist;

import java.util.List;


public interface SuggestionService{

    Suggestion save(Suggestion suggestion);

    Suggestion findById(Integer id);

    void addSuggestion(Suggestion suggestion, Order order, Specialist specialist);

    List<Suggestion> findSuggestionsByCustomerAndOrderBySuggestionPrice(Customer customer);

    List<Suggestion> findSuggestionsByCustomerAndOrderBySpecialistScore(Customer customer);

    List<Suggestion> historyOfSuggestionForCurrentUser(String username);

}