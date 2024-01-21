package org.example.finalprojectphasetwo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.entity.services.MainService;
import org.example.finalprojectphasetwo.repository.MainServiceRepository;
import org.example.finalprojectphasetwo.service.MainServiceService;
import org.example.finalprojectphasetwo.service.dto.MainServiceDto;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MainServiceServiceImpl implements MainServiceService {

   private final MainServiceRepository repository;

    @Override
    public void saveServiceByAdmin(MainServiceDto dto) {

        MainService mainService = new MainService();
        mainService.setTitle(dto.getTitle());
        repository.save(mainService);

    }
}