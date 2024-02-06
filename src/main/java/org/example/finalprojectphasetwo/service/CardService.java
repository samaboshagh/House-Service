package org.example.finalprojectphasetwo.service;


import org.example.finalprojectphasetwo.dto.request.PayWithCardDto;
import org.example.finalprojectphasetwo.entity.Order;

public interface CardService {

    void payWithCard(Order order, PayWithCardDto cardDto);

}