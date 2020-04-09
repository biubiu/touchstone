package com.shawn.touchstone.visitor;

public class Extractor implements Visitor {

    public void visit(PPTFile ppt) {
        System.out.println("extract from ppt");
    }

    public void visit(PDFFile pdf) {
        System.out.println("extract from ppt");
    }

}
