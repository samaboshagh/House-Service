package org.example.finalprojectphasetwo.Controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.example.finalprojectphasetwo.dto.request.AddCommentDto;
import org.example.finalprojectphasetwo.dto.request.OrderDto;
import org.example.finalprojectphasetwo.dto.request.PayWithCardDto;
import org.example.finalprojectphasetwo.dto.request.UserSingUpDto;
import org.example.finalprojectphasetwo.dto.response.AddCommentResponse;
import org.example.finalprojectphasetwo.dto.response.CreateCustomerResponse;
import org.example.finalprojectphasetwo.entity.Comment;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.services.MainService;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.entity.users.Customer;
import org.example.finalprojectphasetwo.exception.InvalidInputException;
import org.example.finalprojectphasetwo.mapper.AddCommentMapper;
import org.example.finalprojectphasetwo.mapper.CustomerMapper;
import org.example.finalprojectphasetwo.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    private final Validator validator;

    @PostMapping("/customer_sing_up")
    public ResponseEntity<CreateCustomerResponse> customerSingUp(@RequestBody UserSingUpDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Set<ConstraintViolation<UserSingUpDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new InvalidInputException("INVALID INPUT VALUE : " + violations);
        }
        Customer customer = CustomerMapper.INSTANCE.convertToDto(dto);
        customerService.customerSingUp(customer);
        CreateCustomerResponse createCustomerResponse = CustomerMapper.INSTANCE.dtoToCustomer(customer);
        return new ResponseEntity<>(createCustomerResponse, HttpStatus.CREATED);
    }

    @PutMapping("/change_password")
    public ResponseEntity<String> changePassword(@RequestParam String username, @RequestParam String password) {
        customerService.changePassword(username, password);
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
    public ResponseEntity<String> addOrder(@RequestBody OrderDto orderDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        customerService.addOrder(orderDto);
        return new ResponseEntity<>("ORDER SUCCESSFULLY PLACED", HttpStatus.OK);
    }

    @GetMapping("/filter_suggestion_by_specialist_score")
    public List<Suggestion> findSuggestionsByCustomerAndOrderBySpecialistScore(@RequestParam String customerUsername) {
        return customerService.findSuggestionByCustomerAndOrderBySpecialistScore(customerUsername);
    }

    @GetMapping("/filter_suggestion_by_suggested_price")
    public List<Suggestion> findSuggestionsByCustomerAndOrderBySuggestedPrice(@RequestParam String customerUsername) {
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

    @PutMapping("/pay_with_credit")
    public ResponseEntity<String> payWithWalletCredit(@RequestParam(required = false) Integer suggestionId) {
        customerService.payWithWalletCredit(suggestionId);
        return new ResponseEntity<>("SUCCESSFULLY PAID", HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/pay_with_card")
    public ResponseEntity<String> payWithCard(@RequestBody PayWithCardDto payWithCardDto) {
//        if (bindingResult.hasErrors()) {
//            return ResponseEntity.badRequest().build();
//        }
//        customerService.payWithCard(payWithCardDto);
        return new ResponseEntity<>("SUCCESSFULLY PAID", HttpStatus.OK);
    }

    @PostMapping("/add_comment")
    public ResponseEntity<AddCommentResponse> addComment(@RequestBody AddCommentDto addCommentDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Set<ConstraintViolation<AddCommentDto>> violations = validator.validate(addCommentDto);
        if (!violations.isEmpty()) {
            throw new InvalidInputException("INVALID INPUT VALUE : " + violations);
        }
        Comment comment = AddCommentMapper.INSTANCE.convertToDto(addCommentDto);
        Integer orderId = addCommentDto.getOrderId();
        customerService.addComment(comment, orderId);
        AddCommentResponse addCommentResponse = AddCommentMapper.INSTANCE.dtoToCustomer(comment);
        return new ResponseEntity<>(addCommentResponse, HttpStatus.OK);
    }
}