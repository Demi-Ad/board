package com.study.board.domain.entity.post.repository;

import com.study.board.domain.entity.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Override
    @EntityGraph(attributePaths = {"user"})
    Page<Post> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"user","commentList"})
    Optional<Post> findById(Long id);

    @EntityGraph(attributePaths = {"user"})
    Optional<Post> findWithUserById(Long id);

    Optional<Post> findSingleById(Long id);

    @EntityGraph(attributePaths = {"user"})
    Page<Post> findByTitleContains(String title,Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    Page<Post> findByContentsContains(String content, Pageable pageable);

    @Query("select p from Post p where p.user.nickname = :nickname order by p.createdDate DESC")
    Page<Post> findUserNickname(@Param("nickname") String nickname, Pageable pageable);

    @Query("select p.contents from Post p")
    List<String> findAllContents();

    Page<Post> getByUser_UserIdEquals(String userId, Pageable pageable);




}