package com.study.board.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CommentNotAuthorizationException extends RuntimeException{
    public CommentNotAuthorizationException() {
        super();
    }

    public CommentNotAuthorizationException(String message) {
        super(message);
    }

    public CommentNotAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommentNotAuthorizationException(Throwable cause) {
        super(cause);
    }

    protected CommentNotAuthorizationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
