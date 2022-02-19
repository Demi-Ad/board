package com.study.board.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNotAuthorizationException extends RuntimeException {
    public UserNotAuthorizationException() {
        super();
    }

    public UserNotAuthorizationException(String message) {
        super(message);
    }

    public UserNotAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotAuthorizationException(Throwable cause) {
        super(cause);
    }

    protected UserNotAuthorizationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
