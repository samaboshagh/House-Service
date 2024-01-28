package org.example.finalprojectphasetwo.service;

import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.users.Customer;

import java.util.List;
import java.util.Optional;


public interface SuggestionService{

    Suggestion save(Suggestion suggestion);

    Optional<Suggestion> findById(Integer id);

    List<Suggestion> findSuggestionsByCustomerAndOrderBySuggestionPrice(Customer customer);
    List<Suggestion> findSuggestionsByCustomerAndOrderBySpecialistScore(Customer customer);
}
