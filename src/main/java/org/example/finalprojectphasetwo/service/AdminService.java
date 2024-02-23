package org.example.finalprojectphasetwo.service;


import org.example.finalprojectphasetwo.dto.request.*;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.enumeration.SpecialistStatus;
import org.example.finalprojectphasetwo.entity.services.MainService;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.entity.users.Admin;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.entity.users.User;

import java.util.List;

@SuppressWarnings("unused")
public interface AdminService extends UserService<Admin> {

    void init();

    void saveServiceByAdmin(MainService mainService);

    List<MainService> showAllMainServices();

    void addSubServiceByAdmin(SubService subService, String mainServiceTitle);

    List<SubService> showAllSubServices();

    List<Specialist> findAllSpecialist();

    void addSpecialistToSubServiceByAdmin(AddAndDeleteSpecialistFromSubServiceRequest request);

    void deleteSpecialistFromSubServiceByAdmin(AddAndDeleteSpecialistFromSubServiceRequest request);

    void setAcceptStatusForSpecialistByAdmin(String username);

    void editDescriptionAndPrice(EditPriceAndDescriptionRequest request);

    List<Specialist> findSpecialistBySpecialistStatus(SpecialistStatus status);

    List<User> searchUsersByAdmin(SearchForUsers search);

    List<SubService> getHistoryOfSubServicesForUser(String username);

    List<Order> getHistoryOfOrdersForUser(OrderHistoryDto dto);

    List<Suggestion> getHistoryOfSuggestionForUser(String username);

    ReportDto reportingFromUsers(String username);

}