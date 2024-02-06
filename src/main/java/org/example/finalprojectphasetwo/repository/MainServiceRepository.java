package org.example.finalprojectphasetwo.repository;

import org.example.finalprojectphasetwo.entity.services.MainService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MainServiceRepository extends JpaRepository<MainService, Integer> {

    Optional<MainService> findByTitle(String title);

    Boolean existsByTitle(String title);

}