package org.example.finalprojectphasetwo.repository;

import org.example.finalprojectphasetwo.entity.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SuggestionRepository extends JpaRepository<Suggestion, Integer> {
}
