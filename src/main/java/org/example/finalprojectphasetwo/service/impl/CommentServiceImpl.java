package org.example.finalprojectphasetwo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.entity.Comment;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.exception.DuplicateException;
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
    public void addCommentToOrder(Comment comment, Suggestion suggestion) {
        if (existsByOrder(suggestion.getOrder()))
            throw new DuplicateException("YOU CAN NOT HAVE SECOND COMMENT FOR SAME ORDER !");
        comment.setOrder(suggestion.getOrder());
        comment.setSpecialist(suggestion.getSpecialist());
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> findAllBySpecialist(Specialist specialist) {
        return commentRepository.findAllBySpecialist(specialist);
    }

    @Override
    public Boolean existsByOrder(Order order) {
        return commentRepository.existsByOrder(order);
    }

}