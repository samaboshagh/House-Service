package org.example.finalprojectphasetwo.service;



import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.entity.users.Admin;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.dto.MainServiceDto;
import org.example.finalprojectphasetwo.dto.SubServiceDto;

import java.util.Set;
@SuppressWarnings("unused")
public interface AdminService extends UserService<Admin>{

    void init();

    void saveServiceByAdmin(MainServiceDto mainServiceDto);

    void addSubServiceByAdmin(SubServiceDto subServiceDto);

    void addSpecialistToSubServiceByAdmin(Specialist specialist, SubService subService);

    void deleteSpecialistFromSubServiceByAdmin(Set<Specialist> specialists, Specialist specialist, SubService subService);

    void setAcceptStatusForSpecialistByAdmin(Specialist specialist);

}