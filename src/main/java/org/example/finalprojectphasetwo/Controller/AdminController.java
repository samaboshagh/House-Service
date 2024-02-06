package org.example.finalprojectphasetwo.Controller;

import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.dto.request.AddAndDeleteSpecialistFromSubServiceRequest;
import org.example.finalprojectphasetwo.dto.request.EditPriceAndDescriptionRequest;
import org.example.finalprojectphasetwo.dto.request.MainServiceDto;
import org.example.finalprojectphasetwo.dto.request.SubServiceDto;
import org.example.finalprojectphasetwo.entity.services.MainService;
import org.example.finalprojectphasetwo.mapper.MainServiceMapper;
import org.example.finalprojectphasetwo.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Validated
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/add_main_service")
    public ResponseEntity<MainServiceDto> addService(@RequestBody MainServiceDto mainServiceDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        MainService mainService = MainServiceMapper.INSTANCE.convertToDto(mainServiceDto);
        adminService.saveServiceByAdmin(mainService);
        return new ResponseEntity<>(mainServiceDto, HttpStatus.CREATED);
    }

    @PostMapping("/add_sub_service")
    public ResponseEntity<SubServiceDto> addSubService(@RequestBody SubServiceDto subServiceDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        adminService.addSubServiceByAdmin(subServiceDto);
        return new ResponseEntity<>(subServiceDto, HttpStatus.CREATED);
    }

    @PutMapping("/add_specialist_to_sub_service")
    public ResponseEntity<AddAndDeleteSpecialistFromSubServiceRequest> addSpecialistToSubService(@RequestBody AddAndDeleteSpecialistFromSubServiceRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        adminService.addSpecialistToSubServiceByAdmin(request);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @DeleteMapping("/delete_specialist_to_sub_service")
    public ResponseEntity<AddAndDeleteSpecialistFromSubServiceRequest> deleteSpecialistFromSubService(@RequestBody AddAndDeleteSpecialistFromSubServiceRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        adminService.deleteSpecialistFromSubServiceByAdmin(request);
        return new ResponseEntity<>(request, HttpStatus.NO_CONTENT);
    }

    @PutMapping("update_specialist_status")
    public ResponseEntity<HttpStatus> setAcceptStatusForSpecialist(@RequestParam String username) {
        adminService.setAcceptStatusForSpecialistByAdmin(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("edit_description_and_price")
    public ResponseEntity<EditPriceAndDescriptionRequest> editDescriptionAndPrice(@RequestBody EditPriceAndDescriptionRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        adminService.editDescriptionAndPrice(request);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }
}