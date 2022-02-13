package com.study.board.web.controller;

import com.study.board.web.common.UserSessionData;
import com.study.board.web.service.UserService;
import com.study.board.web.exception.UserNotFoundException;
import com.study.board.web.dto.userdto.UserLoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginData",new UserLoginDto());
        return "user/UserLoginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginData") UserLoginDto dto,
                        BindingResult bindingResult,
                        HttpServletRequest request, HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            return "/user/UserLoginForm";
        }
        try {
            UserSessionData userSessionData = userService.login(dto);
            HttpSession session = request.getSession(true);
            session.setAttribute("userSessionData",userSessionData);

            String requestUri = request.getParameter("requestUri");
            if (requestUri != null) {
                return "redirect:" + requestUri;
            }

            return "redirect:/";

        } catch (UserNotFoundException userNotFoundException) {
            bindingResult.reject("userNotFound");
            return "/user/UserLoginForm";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }
}
