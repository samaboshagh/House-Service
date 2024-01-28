package org.example.finalprojectphasetwo.service;


import org.example.finalprojectphasetwo.entity.services.SubService;

import java.util.List;
public interface SubServiceService {

    SubService save(SubService subService);

    List<SubService> findAll();

    List<SubService> loadSubServiceWithNoSpcialist();

//  parameter might change
    void editDescriptionAndPrice(Integer subService, String description , Double price);

}