package org.example.finalprojectphasetwo.service;


import org.example.finalprojectphasetwo.dto.request.AddAndDeleteSpecialistFromSubServiceRequest;
import org.example.finalprojectphasetwo.dto.request.EditPriceAndDescriptionRequest;
import org.example.finalprojectphasetwo.entity.services.MainService;
import org.example.finalprojectphasetwo.entity.users.Admin;
import org.example.finalprojectphasetwo.dto.request.SubServiceDto;

@SuppressWarnings("unused")
public interface AdminService extends UserService<Admin> {

    void init();

    void saveServiceByAdmin(MainService mainService);

    void addSubServiceByAdmin(SubServiceDto subServiceDto);

    void addSpecialistToSubServiceByAdmin(AddAndDeleteSpecialistFromSubServiceRequest request);

    void deleteSpecialistFromSubServiceByAdmin(AddAndDeleteSpecialistFromSubServiceRequest request);

    void setAcceptStatusForSpecialistByAdmin(String username);

    void editDescriptionAndPrice(EditPriceAndDescriptionRequest request);

}