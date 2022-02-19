package com.study.board.web.controller;

import com.study.board.web.common.UserSessionData;
import com.study.board.web.dto.boarddto.PaginationDto;
import com.study.board.web.dto.userdto.*;
import com.study.board.web.exception.UserNotAuthorizationException;
import com.study.board.web.service.UserService;
import com.study.board.web.util.Constants;
import com.study.board.web.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PaginationUtil paginationUtil;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("signupData",new UserSignupDto());
        return "user/UserSignupForm";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("signupData") UserSignupDto signupDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "user/UserSignupForm";
        log.info("signupDto = {}",signupDto);
        userService.join(signupDto);

        return "redirect:/";
    }

    @PostMapping("/useridCheck")
    @ResponseBody
    public Map<String,Boolean> userIdCheck(@RequestBody UserIdDuplicationDto userId) {
        log.info("userid = {}",userId);
        Map<String,Boolean> resultData = new HashMap<>();
        Boolean check = userService.idDuplicateCheck(userId.getUserId());
        resultData.put("data",check);
        log.info("resultData = {}",resultData);
        return resultData;
    }

    @GetMapping("/info/{userId}") //user/{userId}?page=
    public String getUserInfo(@PathVariable String userId,
                              @RequestParam(name = "page",defaultValue = "1") Integer page,
                              @SessionAttribute(name = "userSessionData", required = false) UserSessionData userSessionData,
                              Model model) {
        Page<UserPublishedPostDto> userPostList = userService.getUserPostList(page, userId);

        PaginationDto paginationDto = paginationUtil.of(userPostList.getTotalElements(),
                Constants.SHOW_ONE_PAGE_BLOCK, Constants.SHOW_ONE_PAGE_POST, page);

        UserInfoDto userInfoDto = new UserInfoDto(userId, userPostList.getContent());

        boolean isRoleUser = false;

        if (userSessionData != null) {
            isRoleUser = userSessionData.getUserId().equals(userId);
        }

        model.addAttribute("userInfo",userInfoDto);
        model.addAttribute("pagination",paginationDto);
        model.addAttribute("hasRole",isRoleUser);

        return "user/userInfoForm";
    }

    @GetMapping("/edit/{userId}")
    public String userEditForm(@PathVariable String userId, Model model,
                               @SessionAttribute(name = "userSessionData",required = false) UserSessionData userSessionData) {
        if (userSessionData == null || !userSessionData.getUserId().equals(userId)) {
            throw new UserNotAuthorizationException("접근 권한이 없는 유저");
        }
        UserEditDto userEditInfo = userService.getUserEditInfo(userId);

        model.addAttribute("userId",userId);
        model.addAttribute("userEditDto",userEditInfo);
        return "user/userEditForm";
    }

    @PostMapping("/edit/{userId}")
    public String userEdit(@PathVariable String userId, @Valid @ModelAttribute("userEditDto") UserEditDto userEditDto,
                           BindingResult bindingResult,
                           @SessionAttribute("userSessionData") UserSessionData userSessionData) {

        if (bindingResult.hasErrors()) {
            return "user/userEditForm";
        }
        userService.userInfoChange(userEditDto,userId);
        userSessionData.setUserNickName(userEditDto.getNickName());
        return "redirect:/";
    }

}
