package com.shawn.touchstone.chainOfReponsibility.handlers;

public class HandlerA extends Handler {

    @Override
    public boolean doHandle() {
        System.out.println("from handler A");
        return false;
    }
}
