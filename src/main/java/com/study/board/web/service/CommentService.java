package com.study.board.web.service;

import com.study.board.domain.entity.comment.Comment;
import com.study.board.domain.entity.comment.repository.CommentRepository;
import com.study.board.domain.entity.post.Post;
import com.study.board.domain.entity.post.repository.PostRepository;
import com.study.board.domain.entity.user.User;
import com.study.board.domain.entity.user.repository.UserRepository;
import com.study.board.web.common.UserSessionData;
import com.study.board.web.exception.CommentNotAuthorizationException;
import com.study.board.web.exception.UserNotFoundException;
import com.study.board.web.dto.commentdto.CommentRequestDto;
import com.study.board.web.dto.commentdto.CommentResponseDto;
import com.study.board.web.exception.PostNotFoundException;
import com.study.board.web.util.UserIdContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UserIdContext context;

    public CommentResponseDto addComment(CommentRequestDto requestDto) throws UserNotFoundException, PostNotFoundException {
        Comment comment = createComment(requestDto);
        commentRepository.save(comment);
        return comment.toResponseDto();
    }

    private Comment createComment(CommentRequestDto requestDto) {
        Long postId = requestDto.getPostId();
        String userId = requestDto.getUserId();
        context.setContext(userId);
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
        Post post = postRepository.findSingleById(postId).orElseThrow(PostNotFoundException::new);
        return new Comment(requestDto.getComment(), user, post);
    }

    public Long commentDelete(Long commentId, UserSessionData userSessionData) {
        Comment comment = commentRepository.findWithPostById(commentId).orElseThrow();
        if (Objects.equals(comment.getUser().getUserId(), userSessionData.getUserId())) {
            Long postId = comment.getPost().getId();
            commentRepository.delete(comment);
            return postId;
        }
        throw new CommentNotAuthorizationException();
    }
}
