package org.example.finalprojectphasetwo.repository;

import org.example.finalprojectphasetwo.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Integer> {

    ConfirmationToken findByConfirmationToken(String confirmationToken);

    Boolean existsByConfirmationToken(String confirmationToken);

}