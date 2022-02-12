package com.study.board.web.controller;

import com.study.board.web.service.UserService;
import com.study.board.web.dto.userdto.UserIdDuplicationDto;
import com.study.board.web.dto.userdto.UserSignupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

}
