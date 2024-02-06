package org.example.finalprojectphasetwo.Controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.example.finalprojectphasetwo.dto.request.SpecialistSingUpDto;
import org.example.finalprojectphasetwo.dto.request.UserSingUpDto;
import org.example.finalprojectphasetwo.dto.request.CreateSuggestionDto;
import org.example.finalprojectphasetwo.dto.response.CreateSpecialistResponse;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.exception.InvalidInputException;
import org.example.finalprojectphasetwo.mapper.SpecialistMapper;
import org.example.finalprojectphasetwo.service.SpecialistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("/specialist")
@AllArgsConstructor
public class SpecialistController {

    private final SpecialistService specialistService;

    private final Validator validator;

    @PostMapping("/specialist_sing_up")
    public ResponseEntity<CreateSpecialistResponse> specialistSingUp(@RequestBody SpecialistSingUpDto dto, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Set<ConstraintViolation<UserSingUpDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new InvalidInputException("INVALID INPUT VALUE : " + violations);
        }
        Specialist specialist = SpecialistMapper.INSTANCE.convertToDto(dto);
        specialistService.specialistSingUp(specialist, dto.getPathName());
        CreateSpecialistResponse response = SpecialistMapper.INSTANCE.dtoToCustomer(specialist);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/change_password")
    public void changePassword(@RequestParam String username, @RequestParam String password) {
        specialistService.changePassword(username, password);
    }

    //    public ResponseEntity<> getSpecialistProfileImageFromDatabase(Specialist specialist) throws IOException {
//
//    }
//
    @PostMapping("/add_suggestion")
    public ResponseEntity<String> addSuggestionToOrderBySpecialist(@RequestBody CreateSuggestionDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        specialistService.addSuggestionToOrderBySpecialist(dto);
        return new ResponseEntity<>("SUGGESTION ADDED SUCCESSFULLY", HttpStatus.CREATED);
    }
}