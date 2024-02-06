package org.example.finalprojectphasetwo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.dto.request.EditPriceAndDescriptionRequest;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.exception.NotFoundException;
import org.example.finalprojectphasetwo.repository.SubServiceRepository;
import org.example.finalprojectphasetwo.service.SubServiceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class SubServiceServiceImpl implements SubServiceService {

    private final SubServiceRepository repository;

    @Transactional
    @Override
    public SubService save(SubService subService) {
        return repository.save(subService);
    }

    @Override
    public List<SubService> findAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public void editDescriptionAndPrice(EditPriceAndDescriptionRequest request) {
        SubService subService = findBySubServiceTitle(request.getSubServiceTitle());
        subService.setDescription(request.getDescription());
        subService.setBasePrice(request.getBasePrice());
        repository.save(subService);
    }

    @Override
    public Boolean existsBySubServiceTitle(String serviceTitle) {
        return repository.existsBySubServiceTitle(serviceTitle);
    }

    @Override
    public SubService findBySubServiceTitle(String serviceTitle) {
        return repository.findBySubServiceTitle(serviceTitle).orElseThrow(
                () -> new NotFoundException("SUB SERVICE NOT FOUND !")
        );
    }
}