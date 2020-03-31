package com.shawn.touchstone.chainOfReponsibility.chain;

import com.shawn.touchstone.chainOfReponsibility.handlers.Handler;

public class ListHandlerChain implements HandlerChain{

    private Handler head = null;

    public void addHandler(Handler handler) {
        handler.setSuccessor(null);

        if (head == null) {
            head = handler;
            return;
        }

        Handler cur = head;
        while (cur.getSuccessor() != null) {
            cur = cur.getSuccessor();
        }
        cur.setSuccessor(handler);
    }

    public void handle() {
        if (head != null) {
            head.doHandle();
        }
    }
}
