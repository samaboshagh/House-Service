package org.example.finalprojectphasetwo.mapper;

import org.example.finalprojectphasetwo.dto.request.AddCommentDto;
import org.example.finalprojectphasetwo.dto.response.AddCommentResponse;
import org.example.finalprojectphasetwo.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddCommentMapper {

    AddCommentMapper INSTANCE = Mappers.getMapper(AddCommentMapper.class);

    Comment convertToDto(AddCommentDto addCommentDto);

    AddCommentResponse dtoToCustomer(Comment comment);

    List<AddCommentResponse> dtoToCustomers(List<Comment> comment);

}