package org.example.finalprojectphasetwo.service;

import jakarta.servlet.http.HttpServletResponse;
import org.example.finalprojectphasetwo.dto.request.PayWithCardDto;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;

import java.io.IOException;

public interface PaymentService {

    void payWithWalletCredit(Order order, Suggestion suggestion);

    void payWithCard(PayWithCardDto payWithCardDto, Suggestion suggestion);

    void generateCaptcha(HttpServletResponse response) throws IOException;

    boolean validateCaptcha(int fixedRandomNumber, int userInput);

}