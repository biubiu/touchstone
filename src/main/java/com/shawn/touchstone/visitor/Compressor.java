package com.shawn.touchstone.visitor;

public class Compressor implements Visitor{

    @Override
    public void visit(PDFFile pdfFile) {
        System.out.println("compress pdfFile ");
    }

    @Override
    public void visit(PPTFile pptFile) {
        System.out.println("compress pdfFile ");
    }
}
