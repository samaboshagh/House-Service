package org.example.finalprojectphasetwo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalprojectphasetwo.dto.AddCommentDto;
import org.example.finalprojectphasetwo.entity.Comment;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.repository.CommentRepository;
import org.example.finalprojectphasetwo.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    @Override
    public Comment addCommentToOrder(Order order, AddCommentDto dto) {
        return repository.save(
                Comment
                        .builder()
                        .comment(dto.getComment())
                        .score(dto.getScore())
                        .order(order)
                        .build());
    }
}