package org.example.finalprojectphasetwo.service.impl;

import org.example.finalprojectphasetwo.dto.request.PayWithCardDto;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.exception.InvalidCaptchaException;
import org.example.finalprojectphasetwo.service.CardService;
import org.example.finalprojectphasetwo.service.PaymentService;
import org.example.finalprojectphasetwo.service.SpecialistService;
import org.example.finalprojectphasetwo.service.WalletService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final WalletService walletService;
    private final CardService cardService;
    private final SpecialistService specialistService;

    public PaymentServiceImpl(WalletService walletService, CardService cardService, SpecialistService specialistService) {
        this.walletService = walletService;
        this.cardService = cardService;
        this.specialistService = specialistService;
    }

    @Override
    public void payWithWalletCredit(Order order, Suggestion suggestion) {
        walletService.payWithWalletCredit(order, suggestion);
        specialistGetPayment(suggestion);
    }

    @Override
    public void payWithCard(Order order, PayWithCardDto payWithCardDto, Suggestion suggestion) {
        cardService.payWithCard(order, payWithCardDto);
        specialistGetPayment(suggestion);
    }

    @Override
    public int generateCaptcha() {
        // specific random number within the range 100000 and 999999
        return 100000 + (int) (Math.random() * (999999 - 100000 + 1));
    }

    @Override
    public boolean validateCaptcha(int fixedRandomNumber, int userInput) {
        if (fixedRandomNumber == userInput) return true ;
        else throw new InvalidCaptchaException("NOT MATCH !");
    }

    private void specialistGetPayment(Suggestion suggestion) {
        specialistService.specialistGetPayment(suggestion);
    }
}