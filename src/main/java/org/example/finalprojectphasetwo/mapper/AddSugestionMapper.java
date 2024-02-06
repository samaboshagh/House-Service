package org.example.finalprojectphasetwo.mapper;

import org.example.finalprojectphasetwo.dto.request.CreateSuggestionDto;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AddSugestionMapper {

    AddSugestionMapper INSTANCE = Mappers.getMapper(AddSugestionMapper.class);

    Suggestion convertToDto(CreateSuggestionDto suggestionDto);

    CreateSuggestionDto dtoToCustomer(Suggestion suggestion);

}