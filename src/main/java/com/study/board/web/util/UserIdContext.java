package com.study.board.web.util;


import org.springframework.stereotype.Component;

@Component
public class UserIdContext {

    private final ThreadLocal<String> context = new ThreadLocal<>();

    public String getContext() {
        String data = context.get();
        context.remove();
        return data;
    }

    public void setContext(String context) {
        this.context.set(context);
    }
}
