package org.example.finalprojectphasetwo.service;

import org.example.finalprojectphasetwo.dto.request.PayWithCardDto;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;

public interface PaymentService {

    void payWithWalletCredit(Order order, Suggestion suggestion);

    void payWithCard(Order order, PayWithCardDto payWithCardDto, Suggestion suggestion);

    int generateCaptcha();

    boolean validateCaptcha(int fixedRandomNumber, int userInput);

}