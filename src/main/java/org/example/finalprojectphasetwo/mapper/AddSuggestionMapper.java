package org.example.finalprojectphasetwo.mapper;

import org.example.finalprojectphasetwo.dto.request.CreateSuggestionDto;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AddSuggestionMapper {

    AddSuggestionMapper INSTANCE = Mappers.getMapper(AddSuggestionMapper.class);
    Suggestion convertToDto(CreateSuggestionDto createSuggestionDto);
    CreateSuggestionDto dtoToCustomer(Suggestion suggestion);
}