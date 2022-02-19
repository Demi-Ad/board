package com.study.board.web.controller;

import com.study.board.web.dto.boarddto.BoardListDto;
import com.study.board.web.dto.boarddto.PaginationDto;
import com.study.board.web.dto.searchdto.SearchCriteria;
import com.study.board.web.service.BoardService;
import com.study.board.web.util.Constants;
import com.study.board.web.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final PaginationUtil paginationUtil;


    @GetMapping
    public String boardList(@RequestParam(value = "page",defaultValue = "1") int pageNum, Model model) {
        Page<BoardListDto> postList = boardService.getPostList(pageNum);

        PaginationDto paginationDto = paginationUtil.of(postList.getTotalElements(),
                Constants.SHOW_ONE_PAGE_BLOCK,
                Constants.SHOW_ONE_PAGE_POST, pageNum);

        model.addAttribute("searchCriteria",new SearchCriteria());
        model.addAttribute("postList", postList);
        model.addAttribute("pagination",paginationDto);
        return "board/postList";
    }

    @GetMapping("/search")
    public String searchList(@Valid @ModelAttribute("searchCriteria") SearchCriteria searchCriteria,
                             BindingResult bindingResult,
                             @RequestParam(value = "page",defaultValue = "1") int pageNum,
                             Model model) {

        if (bindingResult.hasErrors()) {
            return "redirect:/board?searchErr=true";
        }

        Page<BoardListDto> postList = boardService.getPostListCriteria(searchCriteria, pageNum);
        PaginationDto paginationDto = paginationUtil.of(postList.getTotalElements(), Constants.SHOW_ONE_PAGE_BLOCK,
                Constants.SHOW_ONE_PAGE_POST, pageNum);

        model.addAttribute("postList", postList);
        model.addAttribute("pagination",paginationDto);
        return "board/searchPostList";
    }
}