package org.example.finalprojectphasetwo.service.impl;

import org.example.finalprojectphasetwo.dto.request.ChangePasswordRequest;
import org.example.finalprojectphasetwo.dto.request.PayWithCardDto;
import org.example.finalprojectphasetwo.entity.Comment;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.Wallet;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.entity.enumeration.Role;
import org.example.finalprojectphasetwo.entity.services.MainService;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.entity.users.Customer;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.exception.InvalidInputException;
import org.example.finalprojectphasetwo.exception.NotFoundException;
import org.example.finalprojectphasetwo.exception.WrongTimeException;
import org.example.finalprojectphasetwo.repository.CustomerRepository;
import org.example.finalprojectphasetwo.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

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

    public CustomerServiceImpl(CustomerRepository userRepository, SuggestionService suggestionService, SubServiceService subServiceService, MainServiceService mainServiceService, OrderService orderService, SpecialistService specialistService, WalletService walletService, CommentService commentService, PaymentService paymentService) {
        super(userRepository);
        this.suggestionService = suggestionService;
        this.subServiceService = subServiceService;
        this.mainServiceService = mainServiceService;
        this.orderService = orderService;
        this.specialistService = specialistService;
        this.walletService = walletService;
        this.commentService = commentService;
        this.paymentService = paymentService;
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
        customer.setRole(Role.CUSTOMER);
        userRepository.save(customer);
    }

    @Override
    public void changePassword(ChangePasswordRequest password) {
        Customer customer = findByUsername(password.getUsername());
        changePassword(customer, password);
    }

    @Override
    public void addOrder(Double suggestedPrice, String customerUsername, String subServiceTitle, LocalDate timeOfOrder, Order order) {
        timeValidation(timeOfOrder);
        Customer customer = findByUsername(customerUsername);
        SubService subService = subServiceService.findBySubServiceTitle(subServiceTitle);
        if (!checkPrice(subService, suggestedPrice))
            throw new InvalidInputException("BASE PRICE IS MORE THAN SUGGESTED PRICE ! ");
        orderService.addOrder(subService, customer, order);
    }

    @Override
    public List<Suggestion> findSuggestionByCustomerAndOrderBySpecialistScore(String customerUsername) {
        findSuggestionValidation(customerUsername);
        Customer customer = findByUsername(customerUsername);
        return suggestionService.findSuggestionsByCustomerAndOrderBySpecialistScore(customer);
    }

    @Override
    public List<Suggestion> findSuggestionsByCustomerAndOrderBySuggestionPrice(String customerUsername) {
        findSuggestionValidation(customerUsername);
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
        if (changeOrderStatusValidation(order, suggestion) &&
            order.getStatus().equals(OrderStatus.WAITING_FOR_THE_SPECIALIST_TO_COME_TO_YOUR_PLACE))
            throw new InvalidInputException("INVALID INFORMATION !");
        orderService.changeOrderStatus(order, OrderStatus.STARTED);
    }


    @Override
    public void changeOrderStatusToDone(Integer suggestionId) {
        orderAndSuggestionValidation(suggestionId);
        Suggestion suggestion = suggestionService.findById(suggestionId);
        Order order = suggestion.getOrder();
        if (changeOrderStatusValidation(order, suggestion) &&
            order.getStatus().equals(OrderStatus.STARTED))
            throw new InvalidInputException("INVALID INFORMATION !");
        orderService.changeOrderStatus(order, OrderStatus.DONE);
        order.setOrderEndTime(ZonedDateTime.now());
        orderService.save(order);
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
        Order order = orderService.findById(dto.getOrderId());
        Suggestion suggestion = suggestionService.findById(dto.getSuggestionId());
        paymentService.payWithCard(dto, suggestion);
        checkIfSpecialistHasDelay(suggestion);
    }

    @Override
    @Transactional
    public void addComment(Comment comment, Integer suggestionId) {
        if (comment == null && suggestionId == null)
            throw new NotFoundException("ORDER ID CAN NOT BE NULL");
        Suggestion suggestion = suggestionService.findById(suggestionId);
        Specialist specialist = suggestion.getSpecialist();
        assert comment != null;
        specialist.setStar(comment.getScore());
        specialistService.save(specialist);
        commentService.addCommentToOrder(comment, suggestion);
    }

    private static boolean changeOrderStatusValidation(Order order, Suggestion suggestion) {
        if (order != null && suggestion != null) {
            if (suggestion.getOrder().equals(order)) {
                return !suggestion.getSuggestedStartDate().isAfter(ZonedDateTime.now());
            }
        }
        return true;
    }

    private void checkIfSpecialistHasDelay(Suggestion suggestion) {
        int hours = suggestion.getOrder().getOrderEndTime().getHour() - suggestion.getSuggestedStartDate().getHour();
        if (hours > suggestion.getWorkDuration()) {
            for (int i = 0; i < hours; i++) {
                specialistService.reducingScore(suggestion.getSpecialist());
            }
            if (suggestion.getSpecialist().getStar() < 0) {
                specialistService.demotionOfTheSpecialist(suggestion.getSpecialist());
            }
        }
    }

    private void orderAndSuggestionValidation(Integer suggestionId) {
        if (suggestionId == null)
            throw new NotFoundException("SUGGESTION ID CAN NOT BE NULL !");
    }

    private static void findSuggestionValidation(String customerUsername) {
        if (customerUsername == null)
            throw new NotFoundException("CUSTOMER USERNAME CAN NOT BE NULL");
    }

    private static void timeValidation(LocalDate timeOfOrder) {
        if (timeOfOrder == null)
            throw new InvalidInputException("TIME OF ORDER CANNOT BE NULL");
        if (timeOfOrder.isBefore(LocalDate.now()))
            throw new WrongTimeException("INVALID DATE !");
    }

    private boolean checkPrice(SubService subService, Double suggestedPrice) {
        if (suggestedPrice == null) throw new NotFoundException("SUGGESTED PRICE IS NULL !");
        return subService.getBasePrice() < suggestedPrice;
    }

}