package org.example.finalprojectphasetwo.service.impl;

import jakarta.annotation.PostConstruct;
import org.example.finalprojectphasetwo.entity.enumeration.SpecialistStatus;
import org.example.finalprojectphasetwo.entity.services.MainService;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.entity.users.Admin;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.exception.DuplicateException;
import org.example.finalprojectphasetwo.exception.SpecialistQualificationException;
import org.example.finalprojectphasetwo.repository.*;
import org.example.finalprojectphasetwo.service.AdminService;
import org.example.finalprojectphasetwo.service.MainServiceService;
import org.example.finalprojectphasetwo.service.SpecialistService;
import org.example.finalprojectphasetwo.service.SubServiceService;
import org.example.finalprojectphasetwo.dto.MainServiceDto;
import org.example.finalprojectphasetwo.dto.subServiceDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

@Transactional(readOnly = true)
@Service
public class AdminServiceImpl
        extends UserServiceImpl<Admin>
        implements AdminService {

    private final AdminRepository repository;
    private final SpecialistService specialistService;
    private final SubServiceService subServiceService;
    private final MainServiceService mainServiceService;


    public AdminServiceImpl(UserRepository<Admin> repository, AdminRepository repository1, SpecialistService specialistService, SubServiceService subServiceService, MainServiceService mainServiceService) {
        super(repository);
        this.repository = repository1;
        this.specialistService = specialistService;
        this.subServiceService = subServiceService;
        this.mainServiceService = mainServiceService;
    }


    @Override
    @PostConstruct
    public void init() {
        if (!repository.existsByUsername("admin")) {
            repository.save(
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
    public void saveServiceByAdmin(MainServiceDto dto) {

        MainService mainService = MainService
                .builder()
                .title(dto.getTitle())
                .build();
        mainServiceService.save(mainService);

    }

    @Override
    @Transactional
    public void addSubServiceByAdmin(subServiceDto dto) {

        Collection<SubService> subServices = subServiceService.findAll();
        for (SubService sService : subServices) {
            if (sService.getSubServiceTitle().equals(dto.getSubServiceTitle())) {
                throw new DuplicateException("THIS SUB SERVICE ALREADY EXISTS");
            }
        }
        SubService subService = SubService
                .builder()
                .subServiceTitle(dto.getSubServiceTitle())
                .basePrice(dto.getBasePrice())
                .description(dto.getDescription())
                .mainService(dto.getMainService())
                .build();
        subServiceService.save(subService);
    }

    @Override
    @Transactional
    public void addSpecialistToSubServiceByAdmin(Specialist specialist, SubService subService) {
        Set<Specialist> specialists = subService.getSpecialists();
        if (addSpecialistToSubServiceByAdminValidation(specialist, specialists)) {
            specialists.add(specialist);
            subService.setSpecialists(specialists);
            subServiceService.save(subService);
        } else {
            throw new SpecialistQualificationException("SPECIALIST IS NOT QUALIFIED !");
        }
    }

    private boolean addSpecialistToSubServiceByAdminValidation(Specialist specialist, Set<Specialist> specialists) {
        return specialist.getSpecialistStatus().equals(SpecialistStatus.ACCEPTED) && !specialists.isEmpty() && specialist.isActive();
    }

    @Override
    @Transactional
    public void deleteSpecialistFromSubServiceByAdmin(Set<Specialist> specialists, Specialist specialist, SubService subService) {
        specialists.remove(specialist);
        subService.setSpecialists(specialists);
        subServiceService.save(subService);
    }

    @Override
    @Transactional
    public void setAcceptStatusForSpecialistByAdmin(Specialist specialist) {
        specialist.setSpecialistStatus(SpecialistStatus.ACCEPTED);
        specialistService.save(specialist);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}