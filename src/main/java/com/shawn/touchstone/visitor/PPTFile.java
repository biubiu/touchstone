package com.shawn.touchstone.visitor;

public class PPTFile extends ResourceFile {

    public PPTFile(String path) {
        super(path);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
