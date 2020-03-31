package com.shawn.touchstone.chainOfReponsibility.chain;

import com.shawn.touchstone.chainOfReponsibility.handlers.Handler;

public interface HandlerChain {

    void addHandler(Handler handler);

    void handle();
}
