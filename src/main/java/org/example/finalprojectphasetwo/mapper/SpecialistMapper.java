package org.example.finalprojectphasetwo.mapper;

import org.example.finalprojectphasetwo.dto.request.SpecialistsSingUpDto;
import org.example.finalprojectphasetwo.dto.response.CreateSpecialistResponse;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SpecialistMapper {

    SpecialistMapper INSTANCE = Mappers.getMapper(SpecialistMapper.class);

    Specialist convertToDto(SpecialistsSingUpDto singUpDto);

    CreateSpecialistResponse dtoToCustomer(Specialist specialist);

}