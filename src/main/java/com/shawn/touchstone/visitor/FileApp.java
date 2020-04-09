package com.shawn.touchstone.visitor;

import java.util.ArrayList;
import java.util.List;

public class FileApp {

    public static void main(String[] args) {
        Extractor extractor = new Extractor();
        Compressor compressor = new Compressor();
        for (ResourceFile file: listAll()) {
            file.accept(extractor);
            file.accept(compressor);
        }

    }
    private static List<ResourceFile> listAll() {
        List<ResourceFile> files = new ArrayList<>();
        files.add(new PDFFile(""));
        files.add(new PPTFile (""));
        return files;
    }
}
