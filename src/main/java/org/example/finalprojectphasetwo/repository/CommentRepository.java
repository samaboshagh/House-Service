package org.example.finalprojectphasetwo.repository;

import org.example.finalprojectphasetwo.entity.Comment;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllBySpecialist(Specialist specialist);

}
