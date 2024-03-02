package org.example.finalprojectphasetwo.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.dto.request.*;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.enumeration.SpecialistStatus;
import org.example.finalprojectphasetwo.entity.services.MainService;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.entity.users.User;
import org.example.finalprojectphasetwo.mapper.MainServiceMapper;
import org.example.finalprojectphasetwo.mapper.SubServiceMapper;
import org.example.finalprojectphasetwo.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Validated
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/add-main-service")
    public ResponseEntity<MainServiceDto> addService(@RequestBody @Valid MainServiceDto mainServiceDto) {
        MainService mainService = MainServiceMapper.INSTANCE.convertToDto(mainServiceDto);
        adminService.saveServiceByAdmin(mainService);
        return new ResponseEntity<>(mainServiceDto, HttpStatus.CREATED);
    }

    @GetMapping("/show-all-MainServices")
    public List<MainService> showAll() {
        return adminService.showAllMainServices();
    }

    @PostMapping("/add-sub-service")
    public ResponseEntity<SubServiceDto> addSubService(@RequestBody @Valid SubServiceDto subServiceDto) {
        String mainServiceName = subServiceDto.getMainServiceName();
        SubService subService = SubServiceMapper.INSTANCE.convertToDto(subServiceDto);
        adminService.addSubServiceByAdmin(subService, mainServiceName);
        return new ResponseEntity<>(subServiceDto, HttpStatus.CREATED);
    }

    @GetMapping("/show-all-subServices")
    public List<SubService> showAllSubServices() {
        return adminService.showAllSubServices();
    }


    @GetMapping("/show-all-specialists")
    public List<Specialist> findAllSpecialist() {
        return adminService.findAllSpecialist();
    }


    @PutMapping("/add-specialist-to-sub-service")
    public ResponseEntity<AddAndDeleteSpecialistFromSubServiceRequest> addSpecialistToSubService(@RequestBody @Valid AddAndDeleteSpecialistFromSubServiceRequest request) {
        adminService.addSpecialistToSubServiceByAdmin(request);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @DeleteMapping("/delete-specialist-to-sub-service")
    public ResponseEntity<String> deleteSpecialistFromSubService(@RequestBody @Valid AddAndDeleteSpecialistFromSubServiceRequest request) {
        adminService.deleteSpecialistFromSubServiceByAdmin(request);
        return new ResponseEntity<>("SPECIALIST SUCCESSFULLY DELETED ", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/show-specialist-by-status")
    public List<Specialist> findSpecialistBySpecialistStatus(@RequestParam SpecialistStatus status) {
        return adminService.findSpecialistBySpecialistStatus(status);
    }

    @PutMapping("update-specialist-status")
    public ResponseEntity<String > setAcceptStatusForSpecialist(@RequestParam String username) {
        adminService.setAcceptStatusForSpecialistByAdmin(username);
        return new ResponseEntity<>("SPECIALIST STATUS SUCCESSFULLY CHANGED ",HttpStatus.OK);
    }

    @PutMapping("edit-description-and-price")
    public ResponseEntity<EditPriceAndDescriptionRequest> editDescriptionAndPrice(@RequestBody @Valid EditPriceAndDescriptionRequest request) {
        adminService.editDescriptionAndPrice(request);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @GetMapping("/search")
    public List<User> searchUsersByAdmin(@RequestBody SearchForUsers search) {
        return adminService.searchUsersByAdmin(search);
    }

    @GetMapping("/get-history-of-sub-services-for-user")
    public List<SubService> getHistoryOfSubServicesForUser(@RequestParam String username) {
        return adminService.getHistoryOfSubServicesForUser(username);
    }

    @GetMapping("/get-history-of-suggestion-for-user")
    public List<Suggestion> getHistoryOfSuggestionForUser(@RequestParam String username){
        return adminService.getHistoryOfSuggestionForUser(username);
    }

    @GetMapping("/get-history-of-orders-for-user")
    public List<Order> getHistoryOfOrdersForUser(@RequestBody OrderHistoryDto dto) {
        return adminService.getHistoryOfOrdersForUser(dto);
    }

    @GetMapping("/reporting-from-users")
    public ReportDto reportingFromUsers(@RequestParam String username) {
        return adminService.reportingFromUsers(username);
    }
}