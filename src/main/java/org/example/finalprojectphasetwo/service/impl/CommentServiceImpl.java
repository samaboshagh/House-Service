package org.example.finalprojectphasetwo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.entity.Comment;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.repository.CommentRepository;
import org.example.finalprojectphasetwo.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public void addCommentToOrder(Comment comment, Order order) {
        comment.setOrder(order);
        commentRepository.save(comment);

    }

    @Override
    public List<Comment> findAllBySpecialist(Specialist specialist) {
        return commentRepository.findAllBySpecialist(specialist);
    }
}