package com.study.board.web.util;


import com.study.board.domain.entity.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserContext {

    private final ThreadLocal<String> userIdData = new ThreadLocal<>();
    private final ThreadLocal<String> userNicknameData = new ThreadLocal<>();


    public String getUserIdData() {
        String data = userIdData.get();
        userIdData.remove();
        return data;
    }

    public String getUserNickname() {
        String data = userNicknameData.get();
        userNicknameData.remove();
        return data;
    }

    public void setUser(User user) {
        this.userIdData.set(user.getUserId());
        this.userNicknameData.set(user.getNickname());
    }
}
