package org.example.finalprojectphasetwo.repository;

import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.users.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface SuggestionRepository extends JpaRepository<Suggestion, Integer> {

    @Query("FROM Suggestion s WHERE s.order.customer = :customer ORDER BY s.suggestedPrice ASC")
    List<Suggestion> findSuggestionsByCustomerAndOrderBySuggestionPrice(Customer customer);

    @Query("FROM Suggestion s WHERE s.order.customer = :customer ORDER BY s.specialist.star ASC")
    List<Suggestion> findSuggestionsByCustomerAndOrderBySpecialistScore(Customer customer);
}