package com.shawn.touchstone.chainOfReponsibility.chain;

import com.shawn.touchstone.chainOfReponsibility.handlers.Handler;

import java.util.ArrayList;
import java.util.List;

public class ArrayHandlerChain implements HandlerChain {
    private List<Handler> arr = new ArrayList<>();

    @Override
    public void addHandler(Handler handler) {
        arr.add(handler);
    }

    @Override
    public void handle() {
        for (Handler handler:arr) {
            boolean isHandled = handler.doHandle();
            if (isHandled) {
                break;
            }
        }
    }
}
