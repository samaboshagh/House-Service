package org.example.finalprojectphasetwo.mapper;

import org.example.finalprojectphasetwo.dto.request.SubServiceDto;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SubServiceMapper {

    SubServiceMapper INSTANCE = Mappers.getMapper(SubServiceMapper.class);

    SubService convertToDto(SubServiceDto subServiceDto);

}