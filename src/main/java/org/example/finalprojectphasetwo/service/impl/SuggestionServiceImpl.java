package org.example.finalprojectphasetwo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.repository.SuggestionRepository;
import org.example.finalprojectphasetwo.service.SuggestionService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SuggestionServiceImpl implements SuggestionService {

    private final SuggestionRepository repository;

    @Override
    public Suggestion save(Suggestion suggestion) {
        return repository.save(suggestion);
    }

    @Override
    public Optional<Suggestion> findById(Integer id) {
        return repository.findById(id);
    }
}