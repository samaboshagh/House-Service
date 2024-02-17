package org.example.finalprojectphasetwo.mapper;

import org.example.finalprojectphasetwo.dto.request.OrderDto;
import org.example.finalprojectphasetwo.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AddOrderMapper {

    AddOrderMapper INSTANCE = Mappers.getMapper(AddOrderMapper.class);

    Order convertToDto(OrderDto orderDto);

}