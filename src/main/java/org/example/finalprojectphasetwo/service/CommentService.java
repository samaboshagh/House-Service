package org.example.finalprojectphasetwo.service;


import org.example.finalprojectphasetwo.entity.Comment;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.users.Specialist;

import java.util.List;

public interface CommentService {

    void addCommentToOrder(Comment comment, Order order);

    List<Comment> findAllBySpecialist(Specialist specialist);

}