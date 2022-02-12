package com.study.board.domain.entity.user.repository;

import com.study.board.domain.entity.user.User;
import com.study.board.domain.entity.user.projection.UserProjection;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    @Autowired
    EntityManager em;

    @Test
    void findByUserIdAndPassword() {

        User save = repository.save(new User("a", "aa", "aa", "a@a"));

        em.flush();
        em.clear();

        List<UserProjection> projectionAll = repository.findAllBy();
        Assertions.assertThat(projectionAll.size()).isEqualTo(1);
        Assertions.assertThat(projectionAll.get(0).getUserId()).isEqualTo(save.getUserId());
    }

    @Test
    void findByUserIdExists() {
        User save = repository.save(new User("a", "aa", "aa", "a@a"));

        Boolean byUserIdExists = repository.existsByUserIdEquals("b");

        Assertions.assertThat(byUserIdExists).isEqualTo(false);
    }
}