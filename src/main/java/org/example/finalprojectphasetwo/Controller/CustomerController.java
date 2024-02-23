package org.example.finalprojectphasetwo.Controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.finalprojectphasetwo.dto.request.*;
import org.example.finalprojectphasetwo.dto.response.AddCommentResponse;
import org.example.finalprojectphasetwo.dto.response.CreateCustomerResponse;
import org.example.finalprojectphasetwo.entity.Comment;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.entity.services.MainService;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.entity.users.Customer;
import org.example.finalprojectphasetwo.mapper.AddCommentMapper;
import org.example.finalprojectphasetwo.mapper.AddOrderMapper;
import org.example.finalprojectphasetwo.mapper.CustomerMapper;
import org.example.finalprojectphasetwo.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/customer")
@AllArgsConstructor
@Validated
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/sing_up")
    public ResponseEntity<CreateCustomerResponse> customerSingUp(@RequestBody @Valid UserSingUpDto dto) {
        Customer customer = CustomerMapper.INSTANCE.convertToDto(dto);
        customerService.customerSingUp(customer);
        CreateCustomerResponse createCustomerResponse = CustomerMapper.INSTANCE.dtoToCustomer(customer);
        return new ResponseEntity<>(createCustomerResponse, HttpStatus.CREATED);
    }

    @PutMapping("/change_password")
    public ResponseEntity<String> changePassword(@RequestBody @Valid ChangePasswordRequest passwordRequest) {
        customerService.changePassword(passwordRequest);
        return new ResponseEntity<>("PASSWORD CHANGED SUCCESSFULLY", HttpStatus.OK);
    }

    @GetMapping("/show_all_mainServices")
    public List<MainService> showAllMainServices() {
        return customerService.showAllMainServices();
    }

    @GetMapping("/show_all_subServices")
    public List<SubService> showAllSubServices() {
        return customerService.showAllSubServices();
    }

    @PostMapping("/add_order")
    public ResponseEntity<String> addOrder(@RequestBody @Valid OrderDto orderDto) {
        Order order = AddOrderMapper.INSTANCE.convertToDto(orderDto);
        Double suggestedPrice = orderDto.getSuggestedPrice();
        String subServiceTitle = orderDto.getSubServiceTitle();
        LocalDate timeOfOrder = orderDto.getTimeOfOrder();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        customerService.addOrder(suggestedPrice, username, subServiceTitle, timeOfOrder, order);
        return new ResponseEntity<>("ORDER SUCCESSFULLY PLACED", HttpStatus.OK);
    }

    @GetMapping("/filter_suggestion_by_specialist_score")
    public List<Suggestion> findSuggestionsByCustomerAndOrderBySpecialistScore() {
        String customerUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return customerService.findSuggestionByCustomerAndOrderBySpecialistScore(customerUsername);
    }

    @GetMapping("/filter_suggestion_by_suggested_price")
    public List<Suggestion> findSuggestionsByCustomerAndOrderBySuggestedPrice() {
        String customerUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return customerService.findSuggestionsByCustomerAndOrderBySuggestionPrice(customerUsername);
    }

    @PutMapping("/select_suggestion")
    public ResponseEntity<String> chooseSuggestion(@RequestParam(required = false) Integer suggestionId) {
        customerService.chooseSuggestionByCustomer(suggestionId);
        return new ResponseEntity<>("SUGGESTION SUCCESSFULLY SELECTED", HttpStatus.OK);
    }

    @PutMapping("/start_order")
    public ResponseEntity<String> changeOrderStatusToStarted(@RequestParam(required = false) Integer suggestionId) {
        customerService.changeOrderStatusToStarted(suggestionId);
        return new ResponseEntity<>("ORDER STARTED", HttpStatus.OK);
    }

    @PutMapping("/change_order_status_to_done")
    public ResponseEntity<String> changeOrderStatusToDone(@RequestParam(required = false) Integer suggestionId) {
        customerService.changeOrderStatusToDone(suggestionId);
        return new ResponseEntity<>("FINISHED !", HttpStatus.OK);
    }

    @PutMapping("/increase_credit")
    public ResponseEntity<String> increaseCredit(@RequestParam Double amount) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        customerService.increaseCredit(amount, username);
        return new ResponseEntity<>("CREDIT SUCCESSFULLY INCREASED !", HttpStatus.OK);
    }

    @PutMapping("/pay_with_credit")
    public ResponseEntity<String> payWithWalletCredit(@RequestParam(required = false) Integer suggestionId) {
        customerService.payWithWalletCredit(suggestionId);
        return new ResponseEntity<>("SUCCESSFULLY PAID", HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/pay_with_card")
    public ResponseEntity<String> payWithCard() {
        return new ResponseEntity<>("SUCCESSFULLY PAID", HttpStatus.OK);
    }

    @PostMapping("/add_comment")
    public ResponseEntity<AddCommentResponse> addComment(@RequestBody @Valid AddCommentDto addCommentDto) {
        Comment comment = AddCommentMapper.INSTANCE.convertToDto(addCommentDto);
        Integer suggestionId = addCommentDto.getSuggestionId();
        customerService.addComment(comment, suggestionId);
        AddCommentResponse addCommentResponse = AddCommentMapper.INSTANCE.dtoToCustomer(comment);
        return new ResponseEntity<>(addCommentResponse, HttpStatus.OK);
    }

    @GetMapping("/find_all_orders_by_customer")
    public List<Order> findAllOrdersByCustomer(@RequestParam(required = false) OrderStatus status) {
        String customerUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return customerService.findAllOrdersByCustomer(customerUsername, status);
    }

    @GetMapping("/see_credit")
    public Double seeCredit() {
        String customerUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return customerService.seeCredit(customerUsername);
    }
}