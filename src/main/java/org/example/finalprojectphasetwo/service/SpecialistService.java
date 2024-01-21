package org.example.finalprojectphasetwo.service;


import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.service.dto.SpecialistSingUpDto;


import java.io.IOException;
import java.util.List;
public interface SpecialistService {

    List<Specialist> loadSpecialistWithNewStatus();

    void specialistSingUp(SpecialistSingUpDto dto) throws IOException;

    void setAcceptedStatus(Specialist specialist);


}