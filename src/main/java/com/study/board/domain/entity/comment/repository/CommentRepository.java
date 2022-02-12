package com.study.board.domain.entity.comment.repository;

import com.study.board.domain.entity.comment.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = "post")
    Optional<Comment> findWithPostById(Long id);
}