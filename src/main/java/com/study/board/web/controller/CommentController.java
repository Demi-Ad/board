package com.study.board.web.controller;

import com.study.board.web.common.UserSessionData;
import com.study.board.web.dto.commentdto.CommentRequestDto;
import com.study.board.web.service.CommentService;
import com.study.board.web.util.CommentWebSocketUtil;
import com.study.board.web.util.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final CommentWebSocketUtil webSocketUtil;
    private final UserContext context;

    @PostMapping("/comment")
    public String createComment(@Valid @ModelAttribute CommentRequestDto commentRequestDto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                @SessionAttribute(required = false) UserSessionData userSessionData) {

        redirectAttributes.addAttribute("postId",commentRequestDto.getPostId());

        if (userSessionData == null) {
            return "redirect:/login?requestUri=/post/{postId}";
        }

        if (bindingResult.hasErrors()) {
            return "redirect:/post/{postId}?commentErr=true";
        }

        commentService.addComment(commentRequestDto);
        webSocketUtil.webSocketReceive(commentRequestDto, context.getUserIdData(),context.getUserNickname());
        return "redirect:/post/{postId}";
    }

    @GetMapping("/comment/{commentId}/delete")
    public String deleteComment(@PathVariable Long commentId,
                                @SessionAttribute(required = false) UserSessionData userSessionData,
                                HttpServletResponse response,
                                RedirectAttributes redirectAttributes) throws IOException {

        if (userSessionData == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return "/error/4xx";
        } else {
            Long postId = commentService.commentDelete(commentId, Objects.requireNonNull(userSessionData));
            redirectAttributes.addAttribute("postId",postId);
            return "redirect:/post/{postId}";
        }
    }
}
