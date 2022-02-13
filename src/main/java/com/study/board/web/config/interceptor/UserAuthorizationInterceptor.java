package com.study.board.web.config.interceptor;

import com.study.board.domain.entity.post.Post;
import com.study.board.domain.entity.post.repository.PostRepository;
import com.study.board.web.common.UserSessionData;
import com.study.board.web.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAuthorizationInterceptor implements HandlerInterceptor {

    private final PostRepository postRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }
        UserSessionData userSessionData = (UserSessionData) session.getAttribute("userSessionData");

        if (userSessionData == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }

        String requestURI = request.getRequestURI();
        log.info("requestUri = {}", requestURI);

        try {
            log.info("postId = {}", requestURI.split("/")[2]);
            long postId = Long.parseLong(requestURI.split("/")[2]);

            Post post = postRepository.findWithUserById(postId).orElseThrow(PostNotFoundException::new);
            if (post.getUser().getNickname().equals(userSessionData.getUserNickName())) {
                return true;
            }
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }
    }
}
