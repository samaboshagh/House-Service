package org.example.finalprojectphasetwo.service;


import org.apache.coyote.BadRequestException;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.enumeration.SpecialistStatus;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.dto.createSuggestionDto;
import org.example.finalprojectphasetwo.dto.SpecialistSingUpDto;


import java.io.IOException;
import java.util.List;

@SuppressWarnings("unused")
public interface SpecialistService {

    Specialist save(Specialist specialist);

    List<Specialist> findSpecialistBySpecialistStatus(SpecialistStatus status);

    void specialistSingUp(SpecialistSingUpDto dto) throws IOException;

    byte[] setProfileImageToSpecialist(String pathName) throws IOException;

    String getSpecialistProfileImageFromDatabase(Specialist specialist) throws IOException;

    void addSuggestionToOrderBySpecialist(Order order, createSuggestionDto dto) throws BadRequestException;

    void demotionOfTheSpecialist(Specialist specialist);

    void reducingScore(Specialist specialist);

    void specialistGetPayment(Suggestion suggestion);

}