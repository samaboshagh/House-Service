package org.example.finalprojectphasetwo.service;

import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.Wallet;

public interface WalletService  {

    Wallet saveWallet();

    Wallet save(Wallet wallet);

    void payWithWalletCredit(Order order, Suggestion suggestion);

}