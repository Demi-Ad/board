package com.study.board.web.util;

import java.util.concurrent.atomic.AtomicInteger;

@Deprecated
public class SessionCounter {
    private final AtomicInteger atomicInteger = new AtomicInteger();
    private volatile static SessionCounter sessionCounter;

    public static SessionCounter getInstance() {
        if (sessionCounter == null) {
            synchronized (SessionCounter.class) {
                if (sessionCounter == null) {
                    sessionCounter = new SessionCounter();
                }
            }
        }
        return sessionCounter;
    }
    
    private SessionCounter() {
    }

    public Integer getCount() {
        return atomicInteger.get();
    }

    public Integer increment() {
        return atomicInteger.incrementAndGet();
    }

    public Integer decrement(){
        if (atomicInteger.get() == 0)
            return 0;
        return atomicInteger.decrementAndGet();
    }
}
