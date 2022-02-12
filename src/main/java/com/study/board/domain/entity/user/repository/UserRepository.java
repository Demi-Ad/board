package com.study.board.domain.entity.user.repository;

import com.study.board.domain.entity.user.User;
import com.study.board.domain.entity.user.projection.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserIdAndPassword(String userId,String password);

    List<UserProjection> findAllBy();

    boolean existsByUserIdEquals(String userId);

    long countByUserIdLike(String userId);

    Optional<User> findByUserId(String userId);



}