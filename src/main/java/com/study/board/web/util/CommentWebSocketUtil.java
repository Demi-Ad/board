package com.study.board.web.util;

import com.study.board.domain.entity.post.Post;
import com.study.board.domain.entity.post.repository.PostRepository;
import com.study.board.web.common.UserSessionData;
import com.study.board.web.dto.commentdto.CommentRequestDto;
import com.study.board.web.dto.websocket.CommentWSForm;
import com.study.board.web.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CommentWebSocketUtil {

    private final SimpMessagingTemplate messagingTemplate;
    private final PostRepository postRepository;
    private final String WS_URL = "/comment-ws/";

    @Async
    public void webSocketCall(CommentRequestDto requestDto, String targetUserId) {

        Post post = postRepository.findWithUserById(requestDto.getPostId()).orElseThrow(PostNotFoundException::new);
        String userId = post.getUser().getUserId();
        log.info("test : userId = {}, targetId = {}",userId,targetUserId);
        if (userId.equals(targetUserId)) {
            return;
        }
        CommentWSForm commentWSForm = new CommentWSForm(requestDto);
        log.info("form = {}",commentWSForm);
        messagingTemplate.convertAndSend(WS_URL + userId,commentWSForm);
    }
}
