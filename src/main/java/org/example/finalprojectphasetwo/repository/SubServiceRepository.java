package org.example.finalprojectphasetwo.repository;

import org.example.finalprojectphasetwo.entity.services.SubService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubServiceRepository extends JpaRepository<SubService, Integer> {

    Boolean existsBySubServiceTitle(String serviceTitle);

    Optional<SubService> findBySubServiceTitle(String serviceTitle);

}