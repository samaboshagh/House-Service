package org.example.finalprojectphasetwo.repository;

import org.example.finalprojectphasetwo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
