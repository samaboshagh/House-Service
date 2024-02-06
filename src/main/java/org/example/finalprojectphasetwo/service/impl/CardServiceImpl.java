package org.example.finalprojectphasetwo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.dto.request.PayWithCardDto;
import org.example.finalprojectphasetwo.entity.Card;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.exception.NotFoundException;
import org.example.finalprojectphasetwo.repository.CardRepository;
import org.example.finalprojectphasetwo.service.CardService;
import org.example.finalprojectphasetwo.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private final OrderService orderService;

    @Override
    public void payWithCard(Order order, PayWithCardDto cardDto) {
        if (order.getCustomer().getOrders().contains(order)) {
            Card card = Card
                    .builder()
                    .cardNumber(cardDto.getCardNumber())
                    .cvv2(cardDto.getCvv2())
                    .expirationDate(cardDto.getExpirationDate())
                    .customer(order.getCustomer())
                    .build();
            cardRepository.save(card);
            orderService.changeOrderStatus(order, OrderStatus.PAID);
        } else throw new NotFoundException("ORDER NOT FOUND !" + order.getId());
    }
}