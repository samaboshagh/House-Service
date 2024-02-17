package org.example.finalprojectphasetwo.service.impl;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.dto.request.PayWithCardDto;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.exception.InvalidCaptchaException;
import org.example.finalprojectphasetwo.service.CardService;
import org.example.finalprojectphasetwo.service.PaymentService;
import org.example.finalprojectphasetwo.service.SpecialistService;
import org.example.finalprojectphasetwo.service.WalletService;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final WalletService walletService;
    private final CardService cardService;
    private final SpecialistService specialistService;
    private final DefaultKaptcha captchaProducer;


    @Override
    public void payWithWalletCredit(Order order, Suggestion suggestion) {
        walletService.payWithWalletCredit(order, suggestion);
        specialistGetPayment(suggestion);
    }

    @Override
    public void payWithCard(PayWithCardDto payWithCardDto, Suggestion suggestion) {
        cardService.payWithCard(payWithCardDto);
        specialistGetPayment(suggestion);
    }

    @Override
    public void generateCaptcha(HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");
        String capText = captchaProducer.createText();
        BufferedImage bi = captchaProducer.createImage(capText);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bi, "jpg", byteArrayOutputStream);
        byte[] captchaBytes = byteArrayOutputStream.toByteArray();
        response.getOutputStream().write(captchaBytes);
        response.getOutputStream().flush();
        response.getOutputStream().close();
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