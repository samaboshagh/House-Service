package org.example.finalprojectphasetwo.service;

import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.dto.request.CreateSuggestionDto;


import java.io.IOException;


public interface SpecialistService {

    Specialist save(Specialist specialist);

    Specialist findByUsername(String username);

    void specialistSingUp(Specialist specialist, String path) throws IOException;

    void changePassword(String username, String password);

    String getSpecialistProfileImageFromDatabase(Specialist specialist) throws IOException;

    void addSuggestionToOrderBySpecialist(CreateSuggestionDto dto);

    void demotionOfTheSpecialist(Specialist specialist);

    void reducingScore(Specialist specialist);

    void specialistGetPayment(Suggestion suggestion);

}