package org.example.finalprojectphasetwo.service;

import org.example.finalprojectphasetwo.dto.request.ChangePasswordRequest;
import org.example.finalprojectphasetwo.entity.Comment;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.entity.enumeration.SpecialistStatus;
import org.example.finalprojectphasetwo.entity.users.Specialist;


import java.io.IOException;
import java.util.List;

@SuppressWarnings("unused")
public interface SpecialistService {

    Specialist save(Specialist specialist);

    List<Specialist> findAll();

    Specialist findByUsername(String username);

    void specialistSingUp(Specialist specialist, String path) throws IOException;

    void changePassword(ChangePasswordRequest changePasswordRequest);

    String getSpecialistProfileImageFromDatabase(Specialist specialist) throws IOException;

    List<Order> findAllOrders(String username);

    void addSuggestionToOrderBySpecialist(Suggestion suggestion, Integer orderId);

    void demotionOfTheSpecialist(Specialist specialist);

    void reducingScore(Specialist specialist);

    void specialistGetPayment(Suggestion suggestion);

    List<Comment> findAllBySpecialist(String specialistUsername);

    List<Specialist> findSpecialistBySpecialistStatus(SpecialistStatus status);

    List<Order> findAllOrdersBySpecialist(String username, OrderStatus status);

    Double seeCredit(String username);

    void confirmEmail(String confirmationToken);
}