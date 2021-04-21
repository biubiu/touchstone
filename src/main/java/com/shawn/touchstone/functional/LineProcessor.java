package com.shawn.touchstone.functional;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LineProcessor {
    public static void main(String[] args) throws IOException {
        String oneLine = processFile((BufferedReader br) -> br.readLine());
        String twoLine = processFile((BufferedReader br) -> br.readLine() + br.readLine());
    }

    private static String processFile(BufferedReaderProcessor processor) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(""))) {
            return processor.process(br);
        }
    }
    interface BufferedReaderProcessor {
        String process(BufferedReader bufferedReader) throws IOException;
    }
}
