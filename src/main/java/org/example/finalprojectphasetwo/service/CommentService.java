package org.example.finalprojectphasetwo.service;


import org.example.finalprojectphasetwo.entity.Comment;
import org.example.finalprojectphasetwo.entity.Order;

public interface CommentService {

    void addCommentToOrder(Comment comment, Order order);

}