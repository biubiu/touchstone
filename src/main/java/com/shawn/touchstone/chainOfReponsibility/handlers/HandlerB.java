package com.shawn.touchstone.chainOfReponsibility.handlers;

public class HandlerB extends Handler {
    @Override
    public boolean doHandle() {
        System.out.println("from handler B");
        return true;
    }
}
