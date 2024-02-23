package org.example.finalprojectphasetwo.repository;

import org.example.finalprojectphasetwo.entity.services.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SubServiceRepository extends JpaRepository<SubService, Integer>, JpaSpecificationExecutor<SubService> {

    Boolean existsBySubServiceTitle(String serviceTitle);

    Optional<SubService> findBySubServiceTitle(String serviceTitle);

}