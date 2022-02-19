package com.study.board.web.config;

import com.study.board.web.util.SessionCounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

//@WebListener
@Deprecated
public class HttpSessionListenerConfig implements HttpSessionListener {

    private static final Logger log = LoggerFactory.getLogger(HttpSessionListenerConfig.class);
    public static final SessionCounter COUNTER = SessionCounter.getInstance();


    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        Integer increment = COUNTER.increment();
        log.info("-------Session Created--------");
        log.info("Total Active Session : {} ", increment);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {

        Integer decrement = COUNTER.decrement();
        log.info("Total Active Session : {} ", decrement);
        log.info("-------Session Destroyed--------");
    }
}
