package com.shawn.touchstone.chainOfReponsibility;

import com.shawn.touchstone.chainOfReponsibility.chain.ArrayHandlerChain;
import com.shawn.touchstone.chainOfReponsibility.chain.HandlerChain;
import com.shawn.touchstone.chainOfReponsibility.chain.ListHandlerChain;
import com.shawn.touchstone.chainOfReponsibility.handlers.HandlerA;
import com.shawn.touchstone.chainOfReponsibility.handlers.HandlerB;

public class HandlerApp {
    public static void main(String[] args) {
        HandlerChain handlerChain = new ListHandlerChain();
        //HandlerChain handlerChain = new ArrayHandlerChain();
        handlerChain.addHandler(new HandlerA());
        handlerChain.addHandler(new HandlerB());
        handlerChain.handle();
    }
}
