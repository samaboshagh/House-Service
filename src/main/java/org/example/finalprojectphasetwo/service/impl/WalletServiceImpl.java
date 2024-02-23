package org.example.finalprojectphasetwo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.Wallet;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.exception.InvalidInputException;
import org.example.finalprojectphasetwo.repository.WalletRepository;
import org.example.finalprojectphasetwo.service.OrderService;
import org.example.finalprojectphasetwo.service.WalletService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository repository;

    private final OrderService orderService;

    @Transactional
    public Wallet saveWallet() {
        Wallet wallet = new Wallet();
        repository.save(wallet);
        return wallet;
    }

    @Override
    public Wallet save(Wallet wallet) {
        return repository.save(wallet);
    }

    @Override
    public void payWithWalletCredit(Order order, Suggestion suggestion) {
        payWithWalletCreditValidation(order, suggestion);
        Wallet wallet = order.getCustomer().getWallet();
        wallet.setCreditAmount(
                order.getCustomer().getWallet().getCreditAmount() - suggestion.getSuggestedPrice()
        );
        repository.save(wallet);
        order.setPaid(true);
        orderService.save(order);
        orderService.changeOrderStatus(order, OrderStatus.PAID);
    }

    private void payWithWalletCreditValidation(Order order, Suggestion suggestion) {
        if (order.isPaid())
            throw new InvalidInputException("ALREADY PAID FOR THIS ORDER !");

        if (order.getCustomer() == null && !suggestion.getOrder().equals(order))
            throw new InvalidInputException("YOU CAN NOT PAY FOR THIS ORDER !");

        assert order.getCustomer() != null;
        if (order.getCustomer().getWallet().getCreditAmount() <= suggestion.getSuggestedPrice())
            throw new InvalidInputException("YOU DONT HAVE ENOUGH CREDIT !");
    }
}