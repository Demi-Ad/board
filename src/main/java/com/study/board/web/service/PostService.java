package com.study.board.web.service;

import com.study.board.domain.entity.post.Post;
import com.study.board.domain.entity.post.repository.PostRepository;
import com.study.board.domain.entity.user.User;
import com.study.board.domain.entity.user.repository.UserRepository;
import com.study.board.web.common.UserSessionData;
import com.study.board.web.dto.commentdto.CommentResponseDto;
import com.study.board.web.dto.postdto.PostDto;
import com.study.board.web.dto.postdto.PostResponseDto;
import com.study.board.web.exception.PostNotFoundException;
import com.study.board.web.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    public Long save(PostDto postDto) throws UserNotFoundException {
        User user = userRepository.findByUserId(postDto.getUserId())
                .orElseThrow(UserNotFoundException::new);

        Post post = postDto.toEntity();
        post.userPublish(user);
        postRepository.save(post);
        return post.getId();
    }


    public PostDto findPostDto(Long postId) throws PostNotFoundException {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        return new PostDto(post);
    }

    public PostResponseDto findPost(Long postId, boolean isCountUp, final UserSessionData userSessionData) throws PostNotFoundException {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

        boolean isUpdatable = userSessionData != null && Objects.equals(userSessionData.getId(),post.getUser().getId());

        if (isCountUp) {
            post.postHit();
        }

        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .userId(post.getUser().getUserId())
                .author(post.getUser().getNickname())
                .content(post.getContents())
                .lastModifiedDate(post.getUpdatedDate())
                .isUpdatable(isUpdatable)
                .commentList(post.getCommentList()
                        .stream()
                        .map(comment -> {
                            if (userSessionData == null) {
                                return new CommentResponseDto(comment,false);
                            } else {
                                if (Objects.equals(userSessionData.getUserId(), comment.getUser().getUserId())) {
                                    return new CommentResponseDto(comment,true);
                                } else {
                                    return new CommentResponseDto(comment,false);
                                }
                            }
                        })
                        .collect(Collectors.toList()))
                .build();
    }

    public void updatePost(PostDto postDto, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        post.changePost(postDto.getTitle(), postDto.getContents());
    }


    public void removePost(Long postId) throws PostNotFoundException {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        postRepository.delete(post);
    }
}
