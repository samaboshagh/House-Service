package org.example.finalprojectphasetwo.repository;

import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpecialistRepository extends UserRepository<Specialist> {

    @Query(value = "FROM Specialist s WHERE s.specialistStatus = :NEW")
    List<Specialist> loadSpecialistWithNewStatus();

}