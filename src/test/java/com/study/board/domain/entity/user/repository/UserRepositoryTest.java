package com.study.board.domain.entity.user.repository;

import com.study.board.domain.entity.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    @Autowired
    EntityManager em;

    @Test
    void findByUserIdExists() {
        User save = repository.save(new User("a", "aa", "aa", "a@a"));

        Boolean byUserIdExists = repository.existsByUserIdEquals("b");

        Assertions.assertThat(byUserIdExists).isEqualTo(false);
    }
}