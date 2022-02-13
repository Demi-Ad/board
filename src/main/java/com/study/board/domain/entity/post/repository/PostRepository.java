package com.study.board.domain.entity.post.repository;

import com.study.board.domain.entity.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Override
    @EntityGraph(attributePaths = {"user"})
    Page<Post> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"commentList"})
    Optional<Post> findById(Long id);

    @EntityGraph(attributePaths = {"user"})
    Optional<Post> findWithUserById(Long id);

    Optional<Post> findSingleById(Long id);

}