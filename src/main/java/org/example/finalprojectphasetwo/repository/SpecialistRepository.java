package org.example.finalprojectphasetwo.repository;

import org.example.finalprojectphasetwo.entity.enumeration.SpecialistStatus;
import org.example.finalprojectphasetwo.entity.users.Specialist;

import java.util.List;

public interface SpecialistRepository extends UserRepository<Specialist> {

    List<Specialist> findSpecialistBySpecialistStatus(SpecialistStatus status);

}