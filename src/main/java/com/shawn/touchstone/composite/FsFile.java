package com.shawn.touchstone.composite;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FsFile extends FileSystemNode{

    public FsFile(String path) {
        super(path);
    }

    @Override
    public int countNumOfFiles() {
        return 1;
    }

    @Override
    public long countSizeOfFiles() {
        try {
            return Files.size(Paths.get(super.getPath()));
        } catch (IOException e) {
            return 0;
        }
    }
}
