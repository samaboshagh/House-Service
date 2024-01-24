package org.example.finalprojectphasetwo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.repository.SubServiceRepository;
import org.example.finalprojectphasetwo.service.SubServiceService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubServiceServiceImpl implements SubServiceService {

   private final SubServiceRepository repository;

    @Override
    public SubService save(SubService subService) {
        return repository.save(subService);
    }

    @Override
    public List<SubService> findAll() {
        return repository.findAll();
    }

    @Override
    public List<SubService> loadSubServiceWithNoSepcialist() {
        Collection<SubService> subServices = repository.findAll();
        return subServices.stream()
                .filter(subService -> subService.getSpecialists() == null)
                .toList();
    }

    @Override
    public void editDescriptionAndPrice(Integer subServiceId, String description, Double price) {
        SubService subService = repository.findById(subServiceId).orElse(null);
        if (subService == null) throw new NullPointerException();
        subService.setDescription(description);
        subService.setBasePrice(price);
        repository.save(subService);
    }


}