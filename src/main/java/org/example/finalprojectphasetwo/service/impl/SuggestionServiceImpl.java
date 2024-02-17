package org.example.finalprojectphasetwo.service.impl;

import org.example.finalprojectphasetwo.dto.request.CreateSuggestionDto;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.entity.enumeration.SpecialistStatus;
import org.example.finalprojectphasetwo.entity.users.Customer;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.exception.InvalidInputException;
import org.example.finalprojectphasetwo.exception.NotFoundException;
import org.example.finalprojectphasetwo.exception.SpecialistQualificationException;
import org.example.finalprojectphasetwo.repository.SuggestionRepository;
import org.example.finalprojectphasetwo.service.OrderService;
import org.example.finalprojectphasetwo.service.SpecialistService;
import org.example.finalprojectphasetwo.service.SuggestionService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class SuggestionServiceImpl implements SuggestionService {

    private final SuggestionRepository suggestionRepository;

    private final OrderService orderService;

    private final SpecialistService specialistService;

    public SuggestionServiceImpl(SuggestionRepository suggestionRepository, OrderService orderService, @Lazy SpecialistService specialistService) {
        this.suggestionRepository = suggestionRepository;
        this.orderService = orderService;
        this.specialistService = specialistService;
    }

    @Transactional
    @Override
    public Suggestion save(Suggestion suggestion) {
        return suggestionRepository.save(suggestion);
    }

    @Override
    public Suggestion findById(Integer id) {
        return suggestionRepository.findById(id).orElseThrow(
                () -> new NotFoundException("SUGGESTION NOT FOUND !")
        );
    }

    public void addSuggestion(CreateSuggestionDto dto) {
        Specialist specialist = specialistService.findByUsername(dto.getSpecialistUsername());
        addSuggestionValidation(dto, specialist);
        Order order = orderService.findById(dto.getOrderId());
        if (!checkPrice(order, dto))
            throw new InvalidInputException("SUGGESTED PRICE IS LESS THAN BASE PRICE");
        Suggestion suggestion = Suggestion
                .builder()
                .suggestionCreationDate(LocalDate.now())
                .suggestedPrice(dto.getSuggestedPrice())
                .suggestedStartDate(dto.getSuggestedStartDate())
                .workDuration(dto.getWorkDuration())
                .order(order)
                .specialist(specialist)
                .build();
        List<Suggestion> suggestions = new ArrayList<>();
        suggestions.add(suggestion);
        suggestionRepository.save(suggestion);
        order.setSuggestions(suggestions);
        order.setStatus(OrderStatus.WAITING_SPECIALIST_SELECTION);
        orderService.save(order);
    }

    @Override
    public List<Suggestion> findSuggestionsByCustomerAndOrderBySuggestionPrice(Customer customer) {
        return suggestionRepository.findSuggestionsByCustomerAndOrderBySuggestionPrice(customer);
    }

    @Override
    public List<Suggestion> findSuggestionsByCustomerAndOrderBySpecialistScore(Customer customer) {
        return suggestionRepository.findSuggestionsByCustomerAndOrderBySpecialistScore(customer);
    }

    private static void addSuggestionValidation(CreateSuggestionDto dto, Specialist specialist) {
        if (specialist.getSpecialistStatus().equals(SpecialistStatus.NEW) ||
            specialist.getSpecialistStatus().equals(SpecialistStatus.WARNING))
            throw new SpecialistQualificationException("SPECIALIST NOT QUALIFIED");
        if (dto.getOrderId() == null)
            throw new NotFoundException("ODER ID CANT BE NULL !");
    }

    private boolean checkPrice(Order order, CreateSuggestionDto suggestion) {
        if (order.getSubService().getBasePrice() == null) throw new NotFoundException("SUB SERVICE IS NULL !");
        return suggestion.getSuggestedPrice() > order.getSubService().getBasePrice();
    }
}