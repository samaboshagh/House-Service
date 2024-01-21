package org.example.finalprojectphasetwo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.repository.SubServiceRepository;
import org.example.finalprojectphasetwo.service.SubServiceService;
import org.example.finalprojectphasetwo.service.dto.SubServiceDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubServiceServiceImpl implements SubServiceService {

   private final SubServiceRepository repository;

    @Override
    public void addSubServiceByAdmin(SubServiceDto dto) {

        SubService subService = new SubService();
        Collection<SubService> subServices = repository.findAll();
        for (SubService sService : subServices) {
            if (sService.getSubServiceTitle().equals(dto.getSubServiceTitle())) {
                throw new IllegalStateException();
            }
        }
        subService.setSubServiceTitle(dto.getSubServiceTitle());
        subService.setBasePrice(dto.getBasePrice());
        subService.setDescription(dto.getDescription());
        subService.setMainService(dto.getMainService());
        repository.save(subService);

    }

    @Override
    public List<SubService> loadSubServiceWithNoSepcialist() {
        Collection<SubService> subServices = repository.findAll();
        return subServices.stream()
                .filter(subService -> subService.getSpecialists() == null)
                .toList();
    }
}