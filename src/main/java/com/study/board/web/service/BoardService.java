package com.study.board.web.service;

import com.study.board.domain.entity.post.repository.PostRepository;
import com.study.board.web.dto.boarddto.BoardListDto;
import com.study.board.web.dto.searchdto.SearchCriteria;
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
@Transactional(readOnly = true)
public class BoardService {
    private final PostRepository postRepository;

    public Page<BoardListDto> getPostList(int pageNum) {
        PageRequest pageRequest = PageRequest.of(pageNum-1, 10, Sort.Direction.DESC, "createdDate", "id");
        return postRepository.findAll(pageRequest).map(BoardListDto::new);
    }

    public Page<BoardListDto> getPostListCriteria(SearchCriteria searchCriteria,int pageNum) {
        PageRequest pageRequest = PageRequest.of(pageNum-1, 10, Sort.Direction.DESC, "createdDate");

        switch (searchCriteria.getSearchType()) {
            case TITLE:
                return postRepository.findByTitleContains(searchCriteria.getSearchText(),pageRequest)
                        .map(BoardListDto::new);
            case AUTHOR:
                return postRepository.findUserNickname(searchCriteria.getSearchText(), pageRequest)
                        .map(BoardListDto::new);
            case CONTENT:
                return postRepository.findByContentsContains(searchCriteria.getSearchText(), pageRequest)
                        .map(BoardListDto::new);
        }
        throw new IllegalArgumentException("반드시 조건이 있어야합니다");
    }
}
