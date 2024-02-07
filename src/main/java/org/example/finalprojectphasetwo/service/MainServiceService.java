package org.example.finalprojectphasetwo.service;

import org.example.finalprojectphasetwo.entity.services.MainService;

import java.util.List;


public interface MainServiceService {
    MainService save(MainService mainService);

    MainService findByTitle(String title);

    Boolean existsByTitle(String title);

    List<MainService> findAll();
}
