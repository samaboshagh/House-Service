package org.example.finalprojectphasetwo.service;

import org.example.finalprojectphasetwo.entity.Comment;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.enumeration.SpecialistStatus;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.dto.request.CreateSuggestionDto;


import java.io.IOException;
import java.util.List;


public interface SpecialistService {

    Specialist save(Specialist specialist);

    List<Specialist> findAll();

    Specialist findByUsername(String username);

    void specialistSingUp(Specialist specialist, String path) throws IOException;

    void changePassword(String username, String password);

    String getSpecialistProfileImageFromDatabase(Specialist specialist) throws IOException;

    List<Order> findAllOrders();


    void addSuggestionToOrderBySpecialist(CreateSuggestionDto dto);

    void demotionOfTheSpecialist(Specialist specialist);

    void reducingScore(Specialist specialist);

    void specialistGetPayment(Suggestion suggestion);

    List<Comment> findAllBySpecialist(String specialistUsername);

    List<Specialist> findSpecialistBySpecialistStatus(SpecialistStatus status);

}