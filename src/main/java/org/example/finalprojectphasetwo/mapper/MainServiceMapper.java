package org.example.finalprojectphasetwo.mapper;

import org.example.finalprojectphasetwo.dto.request.MainServiceDto;
import org.example.finalprojectphasetwo.entity.services.MainService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MainServiceMapper {

    MainServiceMapper INSTANCE = Mappers.getMapper(MainServiceMapper.class);

    MainService convertToDto(MainServiceDto mainServiceDto);

}