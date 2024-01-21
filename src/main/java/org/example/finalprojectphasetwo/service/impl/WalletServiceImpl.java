package org.example.finalprojectphasetwo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.entity.Wallet;
import org.example.finalprojectphasetwo.repository.WalletRepository;
import org.example.finalprojectphasetwo.service.WalletService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WalletServiceImpl implements WalletService {

  private final WalletRepository repository;

    public Wallet saveWallet(){
        Wallet wallet = new Wallet();
        repository.save(wallet);
        return wallet;
    }
}