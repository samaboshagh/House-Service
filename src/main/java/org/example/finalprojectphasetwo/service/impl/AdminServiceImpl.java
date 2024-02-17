package org.example.finalprojectphasetwo.service.impl;

import jakarta.annotation.PostConstruct;
import org.example.finalprojectphasetwo.dto.request.AddAndDeleteSpecialistFromSubServiceRequest;
import org.example.finalprojectphasetwo.dto.request.EditPriceAndDescriptionRequest;
import org.example.finalprojectphasetwo.dto.request.SearchForUsers;
import org.example.finalprojectphasetwo.entity.enumeration.Role;
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

    public AdminServiceImpl(AdminRepository userRepository,
                            SpecialistService specialistService,
                            SubServiceService subServiceService,
                            MainServiceService mainServiceService,
                            SearchUsersService searchUsersService) {
        super(userRepository);
        this.specialistService = specialistService;
        this.subServiceService = subServiceService;
        this.mainServiceService = mainServiceService;
        this.searchUsersService = searchUsersService;
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
                            .role(Role.ADMIN)
                            .build()
            );
        }
    }

    @Override
    @Transactional
    public void saveServiceByAdmin(MainService mainService) {
        if (mainServiceService.existsByTitle(mainService.getTitle()))
            throw new DuplicateException("SERVICE ALREADY EXISTS");
        mainServiceService.save(mainService);
    }

    @Override
    public List<MainService> showAllMainServices() {
        return mainServiceService.findAll();
    }

    @Override
    @Transactional
    public void addSubServiceByAdmin(SubService subService, String mainServiceTitle) {
        if (subServiceService.existsBySubServiceTitle(subService.getSubServiceTitle()))
            throw new DuplicateException("SUB SERVICE ALREADY EXISTS");
        MainService mainService = mainServiceService.findByTitle(mainServiceTitle);
        subService.setMainService(mainService);
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
    @Transactional
    public void editDescriptionAndPrice(EditPriceAndDescriptionRequest request) {
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

    private boolean addSpecialistToSubServiceByAdminValidation(Specialist specialist) {
        return specialist.getSpecialistStatus().equals(SpecialistStatus.ACCEPTED) && specialist.isActive();
    }
}