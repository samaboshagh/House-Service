package org.example.finalprojectphasetwo.Controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.finalprojectphasetwo.dto.request.ChangePasswordRequest;
import org.example.finalprojectphasetwo.dto.request.SpecialistSingUpDto;
import org.example.finalprojectphasetwo.dto.request.CreateSuggestionDto;
import org.example.finalprojectphasetwo.dto.response.AddCommentResponse;
import org.example.finalprojectphasetwo.dto.response.CreateSpecialistResponse;
import org.example.finalprojectphasetwo.entity.Comment;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.mapper.AddCommentMapper;
import org.example.finalprojectphasetwo.mapper.SpecialistMapper;
import org.example.finalprojectphasetwo.service.SpecialistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/specialist_sing_up")
    public ResponseEntity<CreateSpecialistResponse> specialistSingUp(@RequestBody @Valid SpecialistSingUpDto dto) throws IOException {
        Specialist specialist = SpecialistMapper.INSTANCE.convertToDto(dto);
        specialistService.specialistSingUp(specialist, dto.getPathName());
        CreateSpecialistResponse response = SpecialistMapper.INSTANCE.dtoToCustomer(specialist);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/change_password")
    public ResponseEntity<String> changePassword(@RequestBody @Valid ChangePasswordRequest password) {
        specialistService.changePassword(password);
        return new ResponseEntity<>("PASSWORD CHANGED SUCCESSFULLY", HttpStatus.OK);
    }

//    public ResponseEntity<> getSpecialistProfileImageFromDatabase(Specialist specialist) throws IOException {
//
//    }

    @GetMapping("/show_all_orders")
    public List<Order> findAllOrders() {
        return specialistService.findAllOrders();
    }

    @PostMapping("/add_suggestion")
    public ResponseEntity<String> addSuggestionToOrder(@RequestBody @Valid CreateSuggestionDto dto) {
        specialistService.addSuggestionToOrderBySpecialist(dto);
        return new ResponseEntity<>("SUGGESTION ADDED SUCCESSFULLY", HttpStatus.CREATED);
    }

    @GetMapping("/show_comments")
    public ResponseEntity<List<AddCommentResponse>> findAllBySpecialist(@RequestParam String specialistUsername) {
        List<Comment> comments = specialistService.findAllBySpecialist(specialistUsername);
        List<AddCommentResponse> responses = AddCommentMapper.INSTANCE.dtoToCustomers(comments);
        return new ResponseEntity<>(responses, HttpStatus.CREATED);
    }

}