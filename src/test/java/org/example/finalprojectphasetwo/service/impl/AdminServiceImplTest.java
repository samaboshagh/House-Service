package org.example.finalprojectphasetwo.service.impl;

import org.apache.coyote.BadRequestException;
import org.example.finalprojectphasetwo.dto.subServiceDto;
import org.example.finalprojectphasetwo.entity.enumeration.SpecialistStatus;
import org.example.finalprojectphasetwo.entity.services.MainService;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.repository.SubServiceRepository;
import org.example.finalprojectphasetwo.repository.SpecialistRepository;
import org.example.finalprojectphasetwo.service.AdminService;
import org.example.finalprojectphasetwo.service.SpecialistService;

import org.example.finalprojectphasetwo.service.SubServiceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class AdminServiceImplTest {

    @Autowired
    private SubServiceRepository subServiceRepository;
    @Autowired
    private SpecialistRepository specialistRepository;
    @Autowired
    private SubServiceService subServiceService;
    @Autowired
    private SpecialistService specialistService;
    @Autowired
    private AdminService adminService;

    @AfterEach
    void tearDown() {
        adminService.deleteAll();
    }
    @Test
    @Order(1)
    void addSpecialistToSubServiceByAdminTestSpecialistHasAcceptedStatus() throws BadRequestException {
        // given
        Specialist specialist = new Specialist(
                "SpecialistFirstname",
                "SpecialistLastname",
                "Specialist@gmail.com",
                "SpecialistUsernameTest",
                "s1234567",
                SpecialistStatus.ACCEPTED
        );
        Set<Specialist> specialists = new HashSet<>();
        specialists.add(specialist);
        specialist.setActive(true);
        specialistService.save(specialist);

        SubService subService = SubService
                .builder()
                .subServiceTitle("TITLE")
                .specialists(specialists)
                .build();
        subServiceService.save(subService);
        // when
        adminService.addSpecialistToSubServiceByAdmin(specialist, subService);
        // then
        Optional<SubService> savedSubService = subServiceRepository.findById(subService.getId());
        assertThat(savedSubService.isPresent()).isTrue();

    }

    @Test
    @Order(2)
    void addSpecialistToSubServiceByAdminTestBadRequestException() {
        // given
        Specialist specialist = new Specialist(
                "SpecialistFirstname",
                "SpecialistLastname",
                "SpecialistTest@gmail.com",
                "SpecialistUsername",
                "s1234567",
                SpecialistStatus.NEW
        );
        specialist.setActive(true);
        specialistRepository.save(specialist);
        Set<Specialist> specialists = new HashSet<>();
        specialists.add(specialist);

        SubService subService = SubService
                .builder()
                .subServiceTitle("TITLE")
                .specialists(specialists)
                .build();
        subServiceRepository.save(subService);
        // when and then
        assertThatThrownBy(() -> adminService.addSpecialistToSubServiceByAdmin(specialist, subService))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("SPECIALIST IS NOT QUALIFIED !");
    }

    @Test
    @Order(3)
    void testAddSubServiceByAdmin() {
        // given
        subServiceDto dto = new subServiceDto();
        dto.setSubServiceTitle("Existing SubService");
        dto.setBasePrice(100.0);
        dto.setDescription("Test Description");
        dto.setMainService(new MainService());
        // when
        subServiceService.save(SubService.builder()
                .subServiceTitle("Existing SubService")
                .build());
        // then
        assertThatThrownBy(() -> adminService.addSubServiceByAdmin(dto))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("THIS SUB SERVICE ALREADY exists");
        Collection<SubService> savedSubServices = subServiceRepository.findAll();
        assertThat(savedSubServices).hasSize(1);
    }
}