package org.example.finalprojectphasetwo.service.impl;

import org.example.finalprojectphasetwo.dto.AddCommentDto;
import org.example.finalprojectphasetwo.dto.PayWithCardDto;
import org.example.finalprojectphasetwo.entity.Comment;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.Wallet;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.entity.users.Customer;
import org.example.finalprojectphasetwo.exception.NotFoundException;
import org.example.finalprojectphasetwo.repository.UserRepository;
import org.example.finalprojectphasetwo.service.*;
import org.example.finalprojectphasetwo.dto.UserSingUpDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)

@Service
public class CustomerServiceImpl
        extends UserServiceImpl<Customer>
        implements CustomerService {

    private final SuggestionService suggestionService;
    private final OrderService orderService;
    private final SpecialistService specialistService;
    private final WalletService walletService;
    private final CommentService commentService;
    private final PaymentService paymentService;

    private LocalDate chengOrderStatusToDoneDate;

    public CustomerServiceImpl(UserRepository<Customer> repository, SuggestionService suggestionService, OrderService orderService, SpecialistService specialistService, WalletService walletService, CommentService commentService, PaymentService paymentService) {
        super(repository);
        this.suggestionService = suggestionService;
        this.orderService = orderService;
        this.specialistService = specialistService;
        this.walletService = walletService;
        this.commentService = commentService;
        this.paymentService = paymentService;
    }

    @Transactional
    @Override
    public void customerSingUp(UserSingUpDto dto) {
        Wallet wallet = walletService.saveWallet();
        checkValidName(dto);
        checkUsernameAndEmailForRegistration(dto);
        checkPassword(dto);
        repository.save(
                Customer
                        .builder()
                        .firstName(dto.getFirstName())
                        .lastName(dto.getLastName())
                        .emailAddress(dto.getEmailAddress())
                        .username(dto.getUsername())
                        .password(dto.getPassword())
                        .isActive(true)
                        .hasPermission(false)
                        .wallet(wallet)
                        .build()
        );
    }

    @Override
    public List<Suggestion> findOrdersByCustomerAndOrderBySpecialistScore(Customer customer) {
        return suggestionService.findSuggestionsByCustomerAndOrderBySpecialistScore(customer);
    }

    @Override
    public List<Suggestion> findOrdersByCustomerAndOrderBySuggestionPrice(Customer customer) {
        return suggestionService.findSuggestionsByCustomerAndOrderBySuggestionPrice(customer);
    }

    @Override
    public void chooseSuggestionByCustomer(Order order, Suggestion suggestion) {
        if (suggestion != null && order != null && order.getSuggestions().contains(suggestion)) {
            orderService.changeOrderStatus(order, OrderStatus.WAITING_FOR_THE_SPECIALIST_TO_COME_TO_YOUR_PLACE);
        } else throw new NotFoundException("INVALID INFORMATION !");
    }

    @Override
    public void changeOrderStatusToStarted(Order order, Suggestion suggestion) {
        if (changeOrderStatusToStartedValidation(order, suggestion)) {
            orderService.changeOrderStatus(order, OrderStatus.STARTED);
        } else throw new NotFoundException("INVALID INFORMATION !");
    }


    @Override
    public void changeOrderStatusToDone(Order order) {
        if (order != null) {
            orderService.changeOrderStatus(order, OrderStatus.DONE);
            chengOrderStatusToDoneDate = LocalDate.now();
        } else throw new NotFoundException("ORDER NOT FOUND");
    }

    @Override
    public void payWithWalletCredit(Order order, Suggestion suggestion) {
        paymentService.payWithWalletCredit(order, suggestion);
        checkIfSpecialistHasDelay(suggestion);
    }

    @Override
    public void payWithCard(Order order, PayWithCardDto payWithCardDto, Suggestion suggestion) {
        paymentService.payWithCard(order, payWithCardDto, suggestion);
        checkIfSpecialistHasDelay(suggestion);
    }

    @Override
    public Comment addComment(AddCommentDto addCommentDto, Order order) {
        return commentService.addCommentToOrder(order, addCommentDto);
    }

    private static boolean changeOrderStatusToStartedValidation(Order order, Suggestion suggestion) {
        if (order != null && suggestion != null) {
            if (suggestion.getOrder().equals(order)) {
                return suggestion.getSuggestedStartDate().isAfter(LocalDate.now());
            }
        }
        return false;
    }

    private void checkIfSpecialistHasDelay(Suggestion suggestion) {
        Duration duration = Duration.between(suggestion.getWorkDuration(), chengOrderStatusToDoneDate);
        long hours = duration.toHours();
        for (int i = 0; i < hours; i++) {
            specialistService.reducingScore(suggestion.getSpecialist());
        }
        if (suggestion.getSpecialist().getStar() < 0) {
            specialistService.demotionOfTheSpecialist(suggestion.getSpecialist());
        }
    }
}