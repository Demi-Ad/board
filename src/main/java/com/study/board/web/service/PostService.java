package com.study.board.web.service;

import com.study.board.domain.entity.post.Post;
import com.study.board.domain.entity.post.repository.PostRepository;
import com.study.board.domain.entity.user.User;
import com.study.board.domain.entity.user.repository.UserRepository;
import com.study.board.web.exception.UserNotFoundException;
import com.study.board.web.common.UserSessionData;
import com.study.board.web.dto.commentdto.CommentResponseDto;
import com.study.board.web.exception.PostNotFoundException;
import com.study.board.web.dto.postdto.PostResponseDto;
import com.study.board.web.dto.postdto.PostCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    public Long save(PostCreateDto postCreateDto) throws UserNotFoundException {
        User user = userRepository.findByUserId(postCreateDto.getUserId())
                .orElseThrow(UserNotFoundException::new);

        Post post = postCreateDto.toEntity();
        post.userPublish(user);
        postRepository.save(post);
        return post.getId();
    }




    public PostResponseDto findPost(Long postId, boolean isCountUp) throws PostNotFoundException {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        UserSessionData userSessionData = null;

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();

        boolean isUpdatable = false;

        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            HttpSession session = request.getSession(false);
            if (session != null) {
                userSessionData = (UserSessionData) session.getAttribute("userSessionData");
                if (userSessionData != null) {
                    if (Objects.equals(post.getUser().getId(), userSessionData.getId())) {
                        isUpdatable = true;
                    }
                }
            }
        }
        if (isCountUp) {
            post.postHit();
        }

        UserSessionData tempUserSessionData = userSessionData;

        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .author(post.getUser().getNickname())
                .content(post.getContents())
                .lastModifiedDate(post.getUpdatedDate())
                .isUpdatable(isUpdatable)
                .commentList(post.getCommentList()
                        .stream()
                        .map(comment -> {
                            if (tempUserSessionData == null) {
                                return new CommentResponseDto(comment,false);
                            } else {
                                if (Objects.equals(tempUserSessionData.getUserId(), comment.getUser().getUserId())) {
                                    return new CommentResponseDto(comment,true);
                                } else {
                                    return new CommentResponseDto(comment,false);
                                }
                            }
                        })
                        .collect(Collectors.toList()))
                .build();
    }

    public void updatePost(PostCreateDto postCreateDto, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        post.changePost(postCreateDto.getTitle(), postCreateDto.getContents());
    }


    public void removePost(Long postId) throws PostNotFoundException {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        postRepository.delete(post);
    }
}
