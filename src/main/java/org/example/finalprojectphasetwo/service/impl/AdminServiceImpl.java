package org.example.finalprojectphasetwo.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.example.finalprojectphasetwo.dto.request.AddAndDeleteSpecialistFromSubServiceRequest;
import org.example.finalprojectphasetwo.dto.request.EditPriceAndDescriptionRequest;
import org.example.finalprojectphasetwo.dto.request.SearchForUsers;
import org.example.finalprojectphasetwo.entity.enumeration.SpecialistStatus;
import org.example.finalprojectphasetwo.entity.services.MainService;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.entity.users.Admin;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.entity.users.User;
import org.example.finalprojectphasetwo.exception.DuplicateException;
import org.example.finalprojectphasetwo.exception.InvalidInputException;
import org.example.finalprojectphasetwo.exception.SpecialistQualificationException;
import org.example.finalprojectphasetwo.repository.AdminRepository;
import org.example.finalprojectphasetwo.service.*;
import org.example.finalprojectphasetwo.dto.request.SubServiceDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Set;

@Transactional(readOnly = true)
@Service
public class AdminServiceImpl
        extends UserServiceImpl<Admin, AdminRepository>
        implements AdminService {

    private final SpecialistService specialistService;
    private final SubServiceService subServiceService;
    private final MainServiceService mainServiceService;
    private final SearchUsersService searchUsersService;
    private final Validator validator;

    public AdminServiceImpl(AdminRepository userRepository, SpecialistService specialistService, SubServiceService subServiceService, MainServiceService mainServiceService, SearchUsersService searchUsersService, Validator validator) {
        super(userRepository);
        this.specialistService = specialistService;
        this.subServiceService = subServiceService;
        this.mainServiceService = mainServiceService;
        this.searchUsersService = searchUsersService;
        this.validator = validator;
    }


    @Override
    @PostConstruct
    public void init() {
        if (!userRepository.existsByUsername("admin")) {
            userRepository.save(
                    Admin
                            .builder()
                            .username("admin")
                            .password("admin123")
                            .hasPermission(true)
                            .isActive(true)
                            .build()
            );
        }
    }

    @Override
    @Transactional
    public void saveServiceByAdmin(MainService mainService) {
        Set<ConstraintViolation<MainService>> violations = validator.validate(mainService);
        if (!violations.isEmpty()) {
            throw new InvalidInputException("MAIN SERVICE TITLE CON NOT BE NULL");
        }
        if (mainServiceService.existsByTitle(mainService.getTitle()))
            throw new DuplicateException("SERVICE ALREADY EXISTS");
        if (mainService.getTitle() != null && !mainService.getTitle().isEmpty()) {
            mainServiceService.save(mainService);
        } else throw new InvalidInputException("MAIN SERVICE TITLE CON NOT BE NULL !");
    }

    @Override
    public List<MainService> showAllMainServices() {
        return mainServiceService.findAll();
    }

    @Override
    @Transactional
    public void addSubServiceByAdmin(SubServiceDto dto) {
        Set<ConstraintViolation<SubServiceDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new InvalidInputException("SUB SERVICE TITLE CON NOT BE NULL");
        }
        if (subServiceService.existsBySubServiceTitle(dto.getSubServiceTitle()))
            throw new DuplicateException("SUB SERVICE ALREADY EXISTS");
        MainService mainService = mainServiceService.findByTitle(dto.getMainServiceName());
        SubService subService = SubService
                .builder()
                .subServiceTitle(dto.getSubServiceTitle())
                .basePrice(dto.getBasePrice())
                .description(dto.getDescription())
                .mainService(mainService)
                .build();
        subServiceService.save(subService);
    }

    @Override
    public List<SubService> showAllSubServices() {
        return subServiceService.findAll();
    }

    @Override
    public List<Specialist> findAllSpecialist() {
        return specialistService.findAll();
    }

    @Override
    @Transactional
    public void addSpecialistToSubServiceByAdmin(AddAndDeleteSpecialistFromSubServiceRequest request) {
        checkValidation(request);
        SubService subService = subServiceService.findBySubServiceTitle(request.getSubServiceTitle());
        Specialist specialist = specialistService.findByUsername(request.getSpecialistUsername());
        Set<Specialist> specialists = subService.getSpecialists();
        if (!addSpecialistToSubServiceByAdminValidation(specialist))
            throw new SpecialistQualificationException("SPECIALIST IS NOT QUALIFIED !");
        specialists.add(specialist);
        subService.setSpecialists(specialists);
        subServiceService.save(subService);
    }


    @Override
    @Transactional
    public void deleteSpecialistFromSubServiceByAdmin(AddAndDeleteSpecialistFromSubServiceRequest request) {
        checkValidation(request);
        SubService subService = subServiceService.findBySubServiceTitle(request.getSubServiceTitle());
        Specialist specialist = specialistService.findByUsername(request.getSpecialistUsername());
        Set<Specialist> specialists = subService.getSpecialists();
        if (specialists.isEmpty()) throw new InvalidInputException("SPECIALIST DOES NOT HAVE ANY SUB SERVICE");
        specialists.remove(specialist);
        subService.setSpecialists(specialists);
        subServiceService.save(subService);
    }

    @Override
    @Transactional
    public void setAcceptStatusForSpecialistByAdmin(String username) {
        if (username == null) throw new InvalidInputException("USER NAME IS NULL");
        Specialist specialist = specialistService.findByUsername(username);
        specialist.setSpecialistStatus(SpecialistStatus.ACCEPTED);
        specialistService.save(specialist);
    }

    @Override
    public void editDescriptionAndPrice(EditPriceAndDescriptionRequest request) {
        Set<ConstraintViolation<EditPriceAndDescriptionRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new InvalidInputException("SUB SERVICE TITLE OR DESCRIPTION CON NOT BE NULL");
        }
        subServiceService.editDescriptionAndPrice(request);
    }

    @Override
    public List<Specialist> findSpecialistBySpecialistStatus(SpecialistStatus status) {
        return specialistService.findSpecialistBySpecialistStatus(status);
    }

    @Override
    public List<User> searchUsersByAdmin(SearchForUsers search) {
        return searchUsersService.searchForUsers(search);
    }

    private void checkValidation(AddAndDeleteSpecialistFromSubServiceRequest request) {
        Set<ConstraintViolation<AddAndDeleteSpecialistFromSubServiceRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new InvalidInputException("SUB SERVICE TITLE OR SPECIALIST USERNAME CON NOT BE NULL");
        }
    }

    private boolean addSpecialistToSubServiceByAdminValidation(Specialist specialist) {
        return specialist.getSpecialistStatus().equals(SpecialistStatus.ACCEPTED) && specialist.isActive();
    }
}