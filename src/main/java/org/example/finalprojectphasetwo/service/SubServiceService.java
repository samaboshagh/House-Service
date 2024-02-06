package org.example.finalprojectphasetwo.service;


import org.example.finalprojectphasetwo.dto.request.EditPriceAndDescriptionRequest;
import org.example.finalprojectphasetwo.entity.services.SubService;

import java.util.List;

public interface SubServiceService {

    SubService save(SubService subService);

    List<SubService> findAll();

    void editDescriptionAndPrice(EditPriceAndDescriptionRequest request);

    Boolean existsBySubServiceTitle(String serviceTitle);

    SubService findBySubServiceTitle(String serviceTitle);

}