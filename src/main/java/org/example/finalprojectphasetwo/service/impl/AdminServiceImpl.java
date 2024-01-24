package org.example.finalprojectphasetwo.service.impl;

import jakarta.annotation.PostConstruct;
import org.apache.coyote.BadRequestException;
import org.example.finalprojectphasetwo.entity.enumeration.SpecialistStatus;
import org.example.finalprojectphasetwo.entity.services.MainService;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.entity.users.Admin;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.repository.*;
import org.example.finalprojectphasetwo.service.AdminService;
import org.example.finalprojectphasetwo.service.MainServiceService;
import org.example.finalprojectphasetwo.service.SpecialistService;
import org.example.finalprojectphasetwo.service.SubServiceService;
import org.example.finalprojectphasetwo.dto.MainServiceDto;
import org.example.finalprojectphasetwo.dto.SubServiceDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

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
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("admin123");
        admin.setHasPermission(true);
        admin.setActive(true);
        repository.save(admin);
    }

    @Override
    public void saveServiceByAdmin(MainServiceDto dto) {

        MainService mainService = MainService
                .builder()
                .title(dto.getTitle())
                .build();
        mainServiceService.save(mainService);

    }

    @Override
    public void addSubServiceByAdmin(SubServiceDto dto) throws BadRequestException {

        Collection<SubService> subServices = subServiceService.findAll();
        for (SubService sService : subServices) {
            if (sService.getSubServiceTitle().equals(dto.getSubServiceTitle())) {
                throw new BadRequestException("THIS SUB SERVICE ALREADY exists");
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

    public void addSpecialistToSubServiceByAdmin(Specialist specialist, SubService subService) throws BadRequestException {
        Set<Specialist> specialists = subService.getSpecialists();
        if (specialist.getSpecialistStatus().equals(SpecialistStatus.ACCEPTED)) {
            specialists.add(specialist);
            subService.setSpecialists(specialists);
            subServiceService.save(subService);
        } else {
            throw new BadRequestException("SPECIALIST IS NOT ACCEPTED !");
        }
    }

    public void deleteSpecialistFromSubServiceByAdmin(Set<Specialist> specialists, Specialist specialist, SubService subService) {
        specialists.remove(specialist);
        subService.setSpecialists(specialists);
        subServiceService.save(subService);
    }

    @Override
    public void setAcceptStatusForSpecialistByAdmin(Specialist specialist) {
        specialist.setSpecialistStatus(SpecialistStatus.ACCEPTED);
        specialistService.save(specialist);
    }
}