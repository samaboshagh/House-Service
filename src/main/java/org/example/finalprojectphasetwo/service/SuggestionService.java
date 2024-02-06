package org.example.finalprojectphasetwo.service;

import org.example.finalprojectphasetwo.dto.request.CreateSuggestionDto;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.users.Customer;

import java.util.List;


public interface SuggestionService{

    Suggestion save(Suggestion suggestion);

    Suggestion findById(Integer id);

    void addSuggestion(CreateSuggestionDto dto);

    List<Suggestion> findSuggestionsByCustomerAndOrderBySuggestionPrice(Customer customer);

    List<Suggestion> findSuggestionsByCustomerAndOrderBySpecialistScore(Customer customer);

}