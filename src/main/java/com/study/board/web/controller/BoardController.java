package com.study.board.web.controller;

import com.study.board.domain.entity.post.Post;
import com.study.board.domain.entity.post.repository.PostRepository;
import com.study.board.domain.entity.user.User;
import com.study.board.web.dto.boarddto.BoardListDto;
import com.study.board.web.dto.boarddto.PaginationDto;
import com.study.board.web.service.PostService;
import com.study.board.web.service.UserService;
import com.study.board.web.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final PostService postService;
    private final PaginationUtil paginationUtil;

    private final int SHOW_ONE_PAGE_BLOCK = 5;
    private final int SHOW_ONE_PAGE_POST = 10;

    @GetMapping()
    public String boardList(@RequestParam(value = "page",defaultValue = "1") int pageNum, Model model) {
        Page<BoardListDto> postList = postService.getPostList(pageNum);
        PaginationDto paginationDto = paginationUtil.of(postList.getTotalElements(), SHOW_ONE_PAGE_BLOCK, SHOW_ONE_PAGE_POST, pageNum);
        log.info("paginationDto = {}", paginationDto);
        model.addAttribute("postList", postList);
        model.addAttribute("pagination",paginationDto);
        return "board/postList";
    }

//    @PostConstruct
//    void init() {
//        User user = new User("votm777","ttpp1212","aaaa","");
//        Long userIdx = userService.join(user);
//
//        for (int i = 0; i < 100; i++) {
//            Post post = new Post("테스트 " + i, "data");
//            post.userPublish(user);
//            repository.save(post);
//        }
//    }
}