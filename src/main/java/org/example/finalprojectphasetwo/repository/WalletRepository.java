package org.example.finalprojectphasetwo.repository;

import org.example.finalprojectphasetwo.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
}
