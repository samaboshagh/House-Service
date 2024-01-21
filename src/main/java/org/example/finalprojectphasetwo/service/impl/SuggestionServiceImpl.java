package org.example.finalprojectphasetwo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.repository.SuggestionRepository;
import org.example.finalprojectphasetwo.service.SuggestionService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SuggestionServiceImpl implements SuggestionService {

    private final SuggestionRepository repository;

}