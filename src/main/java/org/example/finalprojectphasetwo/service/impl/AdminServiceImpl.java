package org.example.finalprojectphasetwo.service.impl;

import jakarta.annotation.PostConstruct;
import org.example.finalprojectphasetwo.entity.enumeration.SpecialistStatus;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.entity.users.Admin;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.repository.AdminRepository;
import org.example.finalprojectphasetwo.repository.SubServiceRepository;
import org.example.finalprojectphasetwo.repository.UserRepository;
import org.example.finalprojectphasetwo.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AdminServiceImpl
        extends UserServiceImpl<Admin>
        implements AdminService {

    private final AdminRepository repository;

    private final SubServiceRepository subServiceRepository;

    public AdminServiceImpl(UserRepository<Admin> repository, AdminRepository repository1, SubServiceRepository subServiceRepository) {
        super(repository);
        this.repository = repository1;
        this.subServiceRepository = subServiceRepository;
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

    public void addByAdmin(Specialist specialist, SubService subService) {
        Set<Specialist> specialists = subService.getSpecialists();
        if (specialist.getSpecialistStatus().equals(SpecialistStatus.ACCEPTED)) {
            specialists.add(specialist);
            subService.setSpecialists(specialists);
            subServiceRepository.save(subService);
        } else {
            throw new IllegalStateException();
        }
    }

    public void deleteByAdmin(Set<Specialist> specialists, Specialist specialist, SubService subService) {
        specialists.remove(specialist);
        subService.setSpecialists(specialists);
        subServiceRepository.save(subService);
    }
}