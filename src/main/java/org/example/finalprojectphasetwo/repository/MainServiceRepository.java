package org.example.finalprojectphasetwo.repository;

import org.example.finalprojectphasetwo.entity.services.MainService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MainServiceRepository extends JpaRepository<MainService, Integer> {
}
