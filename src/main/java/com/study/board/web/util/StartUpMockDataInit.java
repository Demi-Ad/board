package com.study.board.web.util;

import com.study.board.domain.entity.post.Post;
import com.study.board.domain.entity.post.repository.PostRepository;
import com.study.board.domain.entity.user.User;
import com.study.board.domain.entity.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

@Component
@RequiredArgsConstructor
public class StartUpMockDataInit implements ApplicationListener<ContextRefreshedEvent> {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @PostConstruct
    private void init() {
        File file = new File(Constants.IMAGE_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        User user = new User("tempUser","tempUser","USERA",null);

        userRepository.save(user);

        for (int i = 0; i < 50; i++) {
            Post post = new Post("테스트 A" + i, "테스트");
            post.userPublish(user);
            postRepository.save(post);
        }
    }
}
