package com.shawn.touchstone.fs;

import java.util.ArrayList;
import java.util.List;

public class FsDir extends FileSystemNode {
    private List<FileSystemNode> nodes = new ArrayList<>();

    public FsDir(String path, List<FileSystemNode> nodes) {
        super(path);
    }

    @Override
    public int countNumOfFiles() {
        int numOfFiles = 0;
        for (FileSystemNode node : nodes) {
            numOfFiles += node.countNumOfFiles();
        }
        return numOfFiles;
    }

    @Override
    public long countSizeOfFiles() {
        long sizeOfFiles = 0l;
        for (FileSystemNode node : nodes) {
            sizeOfFiles += countSizeOfFiles();
        }
        return sizeOfFiles;
    }

    public void addSubNode(FileSystemNode node) {
        nodes.add(node);
    }

    public void removeSubNode(FileSystemNode fileorDir) {
        int size = nodes.size();
        int i = 0;
        for (; i< size; i++) {
            if (nodes.get(i).getPath().equalsIgnoreCase(fileorDir.getPath())) {
                break;
            }
        }
        if (i < size) {
            nodes.remove(i);
        }
    }
}
