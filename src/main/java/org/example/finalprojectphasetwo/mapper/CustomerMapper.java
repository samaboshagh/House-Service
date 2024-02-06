package org.example.finalprojectphasetwo.mapper;

import org.example.finalprojectphasetwo.dto.request.UserSingUpDto;
import org.example.finalprojectphasetwo.dto.response.CreateCustomerResponse;
import org.example.finalprojectphasetwo.entity.users.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer convertToDto(UserSingUpDto singUpDto);

    CreateCustomerResponse dtoToCustomer(Customer customer);

}