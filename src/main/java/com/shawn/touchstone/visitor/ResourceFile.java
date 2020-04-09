package com.shawn.touchstone.visitor;


public abstract class ResourceFile {

    protected String path;

    public ResourceFile(String path) {
        this.path = path;
    }

    public abstract void accept(Visitor visitor);
}

