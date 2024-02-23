package org.example.finalprojectphasetwo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.dto.request.PayWithCardDto;
import org.example.finalprojectphasetwo.entity.Card;
import org.example.finalprojectphasetwo.repository.CardRepository;
import org.example.finalprojectphasetwo.service.CardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    @Override
    public void payWithCard(PayWithCardDto cardDto) {
            Card card = Card
                    .builder()
                    .cardNumber(cardDto.getCardNumber())
                    .cvv2(cardDto.getCvv2())
                    .expirationDate(cardDto.getExpirationDate())
                    .build();
            cardRepository.save(card);
    }
}