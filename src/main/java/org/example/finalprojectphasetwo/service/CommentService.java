package org.example.finalprojectphasetwo.service;


import org.example.finalprojectphasetwo.entity.Comment;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.users.Specialist;

import java.util.List;

public interface CommentService {

    void addCommentToOrder(Comment comment, Suggestion suggestion);

    List<Comment> findAllBySpecialist(Specialist specialist);

    Boolean existsByOrder(Order order);

}