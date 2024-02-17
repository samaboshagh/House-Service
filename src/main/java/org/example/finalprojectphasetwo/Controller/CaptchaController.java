package org.example.finalprojectphasetwo.Controller;

import lombok.AllArgsConstructor;
import org.example.finalprojectphasetwo.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@AllArgsConstructor
public class CaptchaController {

    private PaymentService paymentService;

    @PostMapping("/captcha")
    public void getCaptcha(HttpServletResponse response) throws Exception {
        paymentService.generateCaptcha(response);
    }
}