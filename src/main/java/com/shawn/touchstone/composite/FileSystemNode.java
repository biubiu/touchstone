package com.shawn.touchstone.composite;

public abstract class FileSystemNode {

    private String path;


    public FileSystemNode(String path) {
        this.path = path;
    }

    public abstract int countNumOfFiles();

    public abstract long countSizeOfFiles();

    public String getPath() {
        return path;
    }
}
