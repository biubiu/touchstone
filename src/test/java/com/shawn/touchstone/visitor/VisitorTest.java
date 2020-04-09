package com.shawn.touchstone.visitor;

import org.junit.Test;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class VisitorTest {

    @Test
    public void testVisitor() {
        Visitor compressor = spy(Compressor.class);
        Visitor extractor = spy(Extractor.class);
        PDFFile pdfFile = new PDFFile("pdf");
        pdfFile.accept(compressor);
        verify(compressor, times(1)).visit(pdfFile);
        pdfFile.accept(extractor);
        verify(extractor, times(1)).visit(pdfFile);
    }
}
