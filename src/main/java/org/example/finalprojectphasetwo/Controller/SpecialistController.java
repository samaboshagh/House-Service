package org.example.finalprojectphasetwo.Controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.finalprojectphasetwo.dto.request.ChangePasswordRequest;
import org.example.finalprojectphasetwo.dto.request.CreateSuggestionDto;
import org.example.finalprojectphasetwo.dto.request.SpecialistsSingUpDto;
import org.example.finalprojectphasetwo.dto.response.AddCommentResponse;
import org.example.finalprojectphasetwo.dto.response.CreateSpecialistResponse;
import org.example.finalprojectphasetwo.entity.Comment;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.mapper.AddCommentMapper;
import org.example.finalprojectphasetwo.mapper.AddSuggestionMapper;
import org.example.finalprojectphasetwo.mapper.SpecialistMapper;
import org.example.finalprojectphasetwo.service.SpecialistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/specialist")
@AllArgsConstructor
@Validated
public class SpecialistController {

    private final SpecialistService specialistService;

    @PostMapping("/sing-up")
    public ResponseEntity<CreateSpecialistResponse> specialistSingUp(@RequestBody @Valid SpecialistsSingUpDto dto) throws IOException {
        Specialist specialist = SpecialistMapper.INSTANCE.convertToDto(dto);
        specialistService.specialistSingUp(specialist, dto.getPathName());
        CreateSpecialistResponse response = SpecialistMapper.INSTANCE.dtoToCustomer(specialist);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody @Valid ChangePasswordRequest password) {
        specialistService.changePassword(password);
        return new ResponseEntity<>("PASSWORD CHANGED SUCCESSFULLY", HttpStatus.OK);
    }

    @GetMapping("/show-all-orders")
    public List<Order> findAllOrders() {
        String specialistUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return specialistService.findAllOrders(specialistUsername);
    }

    @PostMapping("/add-suggestion")
    public ResponseEntity<String> addSuggestionToOrder(@RequestBody @Valid CreateSuggestionDto dto) {
        Suggestion suggestion = AddSuggestionMapper.INSTANCE.convertToDto(dto);
        Integer orderId = dto.getOrderId();
        specialistService.addSuggestionToOrderBySpecialist(suggestion, orderId);
        return new ResponseEntity<>("SUGGESTION ADDED SUCCESSFULLY", HttpStatus.CREATED);
    }

    @GetMapping("/show-comments")
    public ResponseEntity<List<AddCommentResponse>> findAllBySpecialist() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Comment> comments = specialistService.findAllBySpecialist(username);
        List<AddCommentResponse> responses = AddCommentMapper.INSTANCE.dtoToCustomers(comments);
        return new ResponseEntity<>(responses, HttpStatus.CREATED);
    }

    @GetMapping("/find-all-orders-by-specialist")
    public List<Order> findAllOrdersBySpecialist(@RequestParam(required = false) OrderStatus status) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return specialistService.findAllOrdersBySpecialist(username, status);
    }

    @GetMapping("/see-credit")
    public Double seeCredit() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return specialistService.seeCredit(username);
    }

}