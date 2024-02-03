package org.example.finalprojectphasetwo.service;


import org.example.finalprojectphasetwo.dto.AddCommentDto;
import org.example.finalprojectphasetwo.entity.Comment;
import org.example.finalprojectphasetwo.entity.Order;

public interface CommentService {

    Comment addCommentToOrder(Order order, AddCommentDto dto);

}