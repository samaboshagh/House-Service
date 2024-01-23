package org.example.finalprojectphasetwo.service;

import org.example.finalprojectphasetwo.entity.Suggestion;

import java.util.Optional;


public interface SuggestionService{

    Suggestion save(Suggestion suggestion);

    Optional<Suggestion> findById(Integer id);
}
