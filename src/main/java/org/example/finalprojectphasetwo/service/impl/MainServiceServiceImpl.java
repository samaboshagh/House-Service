package org.example.finalprojectphasetwo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.entity.services.MainService;
import org.example.finalprojectphasetwo.exception.NotFoundException;
import org.example.finalprojectphasetwo.repository.MainServiceRepository;
import org.example.finalprojectphasetwo.service.MainServiceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MainServiceServiceImpl implements MainServiceService {

    private final MainServiceRepository mainServiceRepository;

    @Transactional
    @Override
    public MainService save(MainService mainService) {
        return mainServiceRepository.save(mainService);
    }

    @Override
    public MainService findByTitle(String title) {
        return mainServiceRepository.findByTitle(title).orElseThrow(() ->
                new NotFoundException("MAIN SERVICE TITLE NOT FOUND !")
        );
    }

    @Override
    public Boolean existsByTitle(String title) {
        return mainServiceRepository.existsByTitle(title);
    }
}