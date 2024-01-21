package org.example.finalprojectphasetwo.service;


import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.service.dto.SubServiceDto;

import java.util.List;
public interface SubServiceService {

    void addSubServiceByAdmin(SubServiceDto subServiceDto);

    List<SubService> loadSubServiceWithNoSepcialist();

}