package com.study.board.web.controller;

import com.study.board.web.dto.postdto.PostImageDeleteDto;
import com.study.board.web.dto.postdto.PostImageResponseDto;
import com.study.board.web.exception.UserNotFoundException;
import com.study.board.web.common.UserSessionData;
import com.study.board.web.dto.commentdto.CommentRequestDto;
import com.study.board.web.dto.postdto.PostResponseDto;
import com.study.board.web.service.PostImageService;
import com.study.board.web.service.PostService;
import com.study.board.web.dto.postdto.PostCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final PostImageService postImageService;

    @GetMapping("/new")
    public String postCreateForm(Model model, @SessionAttribute(required = false) UserSessionData userSessionData,
                                 HttpServletRequest request) {

        PostCreateDto postCreateDto = new PostCreateDto();

        if (userSessionData != null) {
            postCreateDto.setUserNickname(userSessionData.getUserNickName());
            postCreateDto.setUserId(userSessionData.getUserId());
            model.addAttribute("postData",postCreateDto);
            return "post/postForm";
        }

        return "redirect:/login?requestUri=" + request.getRequestURI();
    }

    @PostMapping("/new")
    public String postCreate(@Valid @ModelAttribute("postData") PostCreateDto postCreateDto,
                             BindingResult bindingResult,
                             @SessionAttribute UserSessionData userSessionData,
                             RedirectAttributes redirectAttributes) {
        log.info("userdata = {}", userSessionData);
        log.info("createDTO = {}",postCreateDto);
        if (bindingResult.hasErrors()) {
            return "post/postForm";
        }
        try {
            Long postId = postService.save(postCreateDto);
            redirectAttributes.addAttribute("id",postId);
            return "redirect:/post/{id}";
        } catch (UserNotFoundException e) {
            return "redirect:/login";
        }
    }

    @GetMapping("/{postId}")
    public String postSingle(@PathVariable Long postId, Model model, HttpServletRequest request,
                             @SessionAttribute(required = false) UserSessionData userSessionData) {
        String referer = request.getHeader("Referer");

        boolean isCountUp = referer == null || (!referer.contains("edit") && !referer.contains("new") && !referer.contains("post"));

        PostResponseDto post = postService.findPost(postId,isCountUp);

        CommentRequestDto commentRequestDto = getCommentRequestDto(postId, userSessionData);

        model.addAttribute("post",post);
        model.addAttribute("commentForm",commentRequestDto);

        return "post/postSingle";
    }

    private CommentRequestDto getCommentRequestDto(Long postId, UserSessionData userSessionData) {
        CommentRequestDto commentRequestDto = new CommentRequestDto();

        commentRequestDto.setPostId(postId);
        if (userSessionData != null)
            commentRequestDto.setUserId(userSessionData.getUserId());

        return commentRequestDto;
    }


    @GetMapping("/{postId}/edit")
    public String pageEditForm(@PathVariable Long postId, Model model,
                               @SessionAttribute(required = false) UserSessionData userSessionData) {

        PostCreateDto postCreateDto = new PostCreateDto(postService.findPost(postId, false),
                userSessionData.getUserId());

        model.addAttribute("postData",postCreateDto);

        return "post/postForm";
    }

    @PostMapping("/{postId}/edit")
    public String pageEdit(@PathVariable Long postId,
                           @ModelAttribute PostCreateDto postCreateDto,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "post/postForm";
        }

        postService.updatePost(postCreateDto, postId);
        return "redirect:/post/{postId}";
    }

    @GetMapping("/{postId}/delete")
    public String deletePost(@PathVariable Long postId) {
        postService.removePost(postId);
        return "redirect:/board";
    }

    @PostMapping("/uploadImage")
    @ResponseBody
    public PostImageResponseDto uploadImage(@RequestParam("file") MultipartFile multipartFile) {
        log.info("file = {}",multipartFile);
        String url = postImageService.uploadImage(multipartFile);
        PostImageResponseDto responseDto = new PostImageResponseDto();

        if (url != null) {
            responseDto.setUrl(url);
            responseDto.setResponseCode("success");
        } else {
            responseDto.setUrl(null);
            responseDto.setResponseCode("fail");
        }
        log.info("dto = {}",responseDto);
        return responseDto;
    }

    @PostMapping("/deleteImage")
    @ResponseBody
    public boolean deleteUploadImage(@RequestBody PostImageDeleteDto postImageDeleteDto) {
        if (postImageDeleteDto.getDeleteImages().isEmpty()) {
            return true;
        }
        postImageService.deleteImage(postImageDeleteDto.getDeleteImages());
        return true;
    }

}
