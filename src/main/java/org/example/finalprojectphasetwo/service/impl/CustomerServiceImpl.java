package org.example.finalprojectphasetwo.service.impl;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.example.finalprojectphasetwo.dto.request.OrderDto;
import org.example.finalprojectphasetwo.dto.request.PayWithCardDto;
import org.example.finalprojectphasetwo.dto.request.SearchForUsers;
import org.example.finalprojectphasetwo.entity.Comment;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.Wallet;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.entity.services.MainService;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.entity.users.Customer;
import org.example.finalprojectphasetwo.entity.users.User;
import org.example.finalprojectphasetwo.exception.InvalidInputException;
import org.example.finalprojectphasetwo.exception.NotFoundException;
import org.example.finalprojectphasetwo.repository.CustomerRepository;
import org.example.finalprojectphasetwo.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class CustomerServiceImpl
        extends UserServiceImpl<Customer, CustomerRepository>
        implements CustomerService {

    private final SuggestionService suggestionService;
    private final SubServiceService subServiceService;
    private final MainServiceService mainServiceService;
    private final OrderService orderService;
    private final SpecialistService specialistService;
    private final WalletService walletService;
    private final CommentService commentService;
    private final PaymentService paymentService;
    private final Validator validator;

    private ZonedDateTime chengOrderStatusToDoneDate;

    public CustomerServiceImpl(CustomerRepository userRepository, SuggestionService suggestionService, SubServiceService subServiceService, MainServiceService mainServiceService, OrderService orderService, SpecialistService specialistService, WalletService walletService, CommentService commentService, PaymentService paymentService, Validator validator) {
        super(userRepository);
        this.suggestionService = suggestionService;
        this.subServiceService = subServiceService;
        this.mainServiceService = mainServiceService;
        this.orderService = orderService;
        this.specialistService = specialistService;
        this.walletService = walletService;
        this.commentService = commentService;
        this.paymentService = paymentService;
        this.validator = validator;
    }

    @Override
    public Customer findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundException("USER NOT FOUND")
        );
    }

    @Override
    public List<MainService> showAllMainServices() {
        return mainServiceService.findAll();
    }

    @Override
    public List<SubService> showAllSubServices() {
        return subServiceService.findAll();
    }

    @Transactional
    @Override
    public void customerSingUp(Customer customer) {
        Wallet wallet = walletService.saveWallet();
        checkUsernameAndEmailForRegistration(customer);
        customer.setActive(true);
        customer.setWallet(wallet);
        userRepository.save(customer);
    }

    @Override
    public void changePassword(String username, String password) {
        if (username == null && password == null)
            throw new NotFoundException("USERNAME OR PASSWORD CANNOT BE NULL");
        Customer customer = findByUsername(username);
        changePassword(customer, password);
    }

    @Override
    public void addOrder(OrderDto orderDto) {
        orderService.addOrder(orderDto);
    }

    @Override
    public List<Suggestion> findSuggestionByCustomerAndOrderBySpecialistScore(String customerUsername) {
        if (customerUsername == null)
            throw new NotFoundException("CUSTOMER USERNAME CAN NOT BE NULL");
        Customer customer = findByUsername(customerUsername);
        return suggestionService.findSuggestionsByCustomerAndOrderBySpecialistScore(customer);
    }

    @Override
    public List<Suggestion> findSuggestionsByCustomerAndOrderBySuggestionPrice(String customerUsername) {
        if (customerUsername == null)
            throw new NotFoundException("CUSTOMER USERNAME CAN NOT BE NULL");
        Customer customer = findByUsername(customerUsername);
        return suggestionService.findSuggestionsByCustomerAndOrderBySuggestionPrice(customer);
    }

    @Override
    public void chooseSuggestionByCustomer(Integer suggestionId) {
        orderAndSuggestionValidation(suggestionId);
        Suggestion suggestion = suggestionService.findById(suggestionId);
        Order order = suggestion.getOrder();
        orderService.changeOrderStatus(order, OrderStatus.WAITING_FOR_THE_SPECIALIST_TO_COME_TO_YOUR_PLACE);
    }

    @Override
    public void changeOrderStatusToStarted(Integer suggestionId) {
        orderAndSuggestionValidation(suggestionId);
        Suggestion suggestion = suggestionService.findById(suggestionId);
        Order order = suggestion.getOrder();
        if (changeOrderStatusToStartedValidation(order, suggestion)) {
            orderService.changeOrderStatus(order, OrderStatus.STARTED);
        } else throw new NotFoundException("INVALID INFORMATION !");
    }


    @Override
    public void changeOrderStatusToDone(Integer suggestionId) {
        orderAndSuggestionValidation(suggestionId);
        Suggestion suggestion = suggestionService.findById(suggestionId);
        Order order = suggestion.getOrder();
        if (changeOrderStatusToStartedValidation(order, suggestion)) {
            orderService.changeOrderStatus(order, OrderStatus.DONE);
        } else throw new NotFoundException("INVALID INFORMATION !");
        chengOrderStatusToDoneDate = ZonedDateTime.now();
    }

    @Override
    public void payWithWalletCredit(Integer suggestionId) {
        orderAndSuggestionValidation(suggestionId);
        Suggestion suggestion = suggestionService.findById(suggestionId);
        Order order = suggestion.getOrder();
        paymentService.payWithWalletCredit(order, suggestion);
        checkIfSpecialistHasDelay(suggestion);
    }

    @Override
    public void payWithCard(PayWithCardDto dto) {
        Set<ConstraintViolation<PayWithCardDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new InvalidInputException("INVALID INFO");
        }
        Order order = orderService.findById(dto.getOrderId());
        Suggestion suggestion = suggestionService.findById(dto.getSuggestionId());
        paymentService.payWithCard(order, dto, suggestion);
        checkIfSpecialistHasDelay(suggestion);
    }

    @Override
    public void addComment(Comment comment, Integer orderId) {
        if (comment == null && orderId == null)
            throw new NotFoundException("ORDER ID CAN NOT BE NULL");
        Order order = orderService.findById(orderId);
        commentService.addCommentToOrder(comment, order);
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
        int hours = chengOrderStatusToDoneDate.getHour() - suggestion.getWorkDuration();
        for (int i = 0; i < hours; i++) {
            specialistService.reducingScore(suggestion.getSpecialist());
        }
        if (suggestion.getSpecialist().getStar() < 0) {
            specialistService.demotionOfTheSpecialist(suggestion.getSpecialist());
        }
    }

    private void orderAndSuggestionValidation(Integer suggestionId) {
        if (suggestionId == null)
            throw new NotFoundException("ORDER ID OR SUGGESTION ID CAN NOT BE NULL");
    }
}