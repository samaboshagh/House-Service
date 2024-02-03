package org.example.finalprojectphasetwo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.exception.NotFoundException;
import org.example.finalprojectphasetwo.repository.SubServiceRepository;
import org.example.finalprojectphasetwo.service.SubServiceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
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

    @Override
    public List<SubService> loadSubServiceWithNoSpcialist() {
        Collection<SubService> subServices = repository.findAll();
        return subServices.stream()
                .filter(subService -> subService.getSpecialists() == null)
                .toList();
    }

    @Transactional
    @Override
    public void editDescriptionAndPrice(Integer subServiceId, String description, Double price) {
        SubService subService = repository.findById(subServiceId).orElse(null);
        if (subService == null) throw new NotFoundException("SUB SERVICE NOT FOUND");
        subService.setDescription(description);
        subService.setBasePrice(price);
        repository.save(subService);
    }
}