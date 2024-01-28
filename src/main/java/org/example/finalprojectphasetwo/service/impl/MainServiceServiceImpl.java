package org.example.finalprojectphasetwo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.entity.services.MainService;
import org.example.finalprojectphasetwo.repository.MainServiceRepository;
import org.example.finalprojectphasetwo.service.MainServiceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MainServiceServiceImpl implements MainServiceService {

   private final MainServiceRepository repository;

   @Transactional
   @Override
   public MainService save(MainService mainService) {
      return repository.save(mainService);
   }
}