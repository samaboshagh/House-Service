package org.example.finalprojectphasetwo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.users.Customer;
import org.example.finalprojectphasetwo.repository.SuggestionRepository;
import org.example.finalprojectphasetwo.service.SuggestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class SuggestionServiceImpl implements SuggestionService {

    private final SuggestionRepository repository;
    @Transactional
    @Override
    public Suggestion save(Suggestion suggestion) {
        return repository.save(suggestion);
    }

    @Override
    public Optional<Suggestion> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Suggestion> findSuggestionsByCustomerAndOrderBySuggestionPrice(Customer customer) {
        return repository.findSuggestionsByCustomerAndOrderBySuggestionPrice(customer);
    }

    @Override
    public List<Suggestion> findSuggestionsByCustomerAndOrderBySpecialistScore(Customer customer) {
        return repository.findSuggestionsByCustomerAndOrderBySpecialistScore(customer);
    }
}