package com.study.board.web.service;

import com.study.board.domain.entity.post.Post;
import com.study.board.domain.entity.post.repository.PostRepository;
import com.study.board.domain.entity.user.User;
import com.study.board.domain.entity.user.repository.UserRepository;
import com.study.board.web.dto.userdto.UserPublishedPostDto;
import com.study.board.web.exception.UserNotFoundException;
import com.study.board.web.common.UserSessionData;
import com.study.board.web.dto.userdto.UserLoginDto;
import com.study.board.web.dto.userdto.UserSignupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {
    private final UserRepository userRepository;

    private final PostRepository postRepository;

    public UserSessionData login(UserLoginDto loginDto) throws UserNotFoundException {

        User user = userRepository.findByUserIdAndPassword(loginDto.getUserId(), loginDto.getUserPassword())
                .orElseThrow(() -> new UserNotFoundException("해당하는 유저가 존재하지않습니다"));
        log.info("userId = {}",user.getUserId());
        return UserSessionData.builder().userId(user.getUserId()).id(user.getId()).userNickName(user.getNickname()).build();
    }

    public Long join(UserSignupDto signupDto) {
        return userRepository.save(signupDto.toEntity()).getId();
    }


    public Boolean idDuplicateCheck(String userId) {
        return !userRepository.existsByUserIdEquals(userId);
    }


    public Page<UserPublishedPostDto> getUserPostList(Integer page,String userId) {
        PageRequest request = PageRequest.of(page-1, 10, Sort.Direction.DESC, "createdDate");
        Page<Post> userPublishedPostList = postRepository.getByUser_UserIdEquals(userId,request);
        log.info("service Dto = {}",userPublishedPostList);
        return userPublishedPostList.map(UserPublishedPostDto::new);
    }

}
