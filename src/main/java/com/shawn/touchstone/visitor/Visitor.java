package com.shawn.touchstone.visitor;

public interface Visitor {

    void visit(PDFFile file);

    void visit(PPTFile pptFile);
}
