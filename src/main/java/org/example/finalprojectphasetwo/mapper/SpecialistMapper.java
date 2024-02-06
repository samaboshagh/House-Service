package org.example.finalprojectphasetwo.mapper;

import org.example.finalprojectphasetwo.dto.request.SpecialistSingUpDto;
import org.example.finalprojectphasetwo.dto.response.CreateSpecialistResponse;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SpecialistMapper {

    SpecialistMapper INSTANCE = Mappers.getMapper(SpecialistMapper.class);

    Specialist convertToDto(SpecialistSingUpDto singUpDto);

    CreateSpecialistResponse dtoToCustomer(Specialist specialist);

}