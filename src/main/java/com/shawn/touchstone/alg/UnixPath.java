package com.shawn.touchstone.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class UnixPath {

    /**
     * compact the raw path to shortened without modifying the logical path
     * @param path
     * @return shortened path
     */
    public static String shortenPath(String path) {
        ArrayList<String> stack = new ArrayList<>();
        boolean startWithPath = path.charAt(0) == '/';
        //skip '.' as it indicates the path not changed
        List<String> tokens = Arrays.stream(path.split("/")).filter(token -> isImportant(token)).collect(Collectors.toList());
        if (startWithPath) {
            stack.add("");
        }
        for (String token : tokens) {
            if (token.equals("..")) {
                if (stack.isEmpty() || stack.get(stack.size() - 1).equals("..")) {
                    stack.add(token);
                } else if (!stack.get(stack.size() - 1).equals("")) {
                    stack.remove(stack.size() - 1);
                }
            } else {
                stack.add(token);
            }
        }
        if (stack.size() == 1 && stack.get(0).equals("")) return "/";
        return String.join("/", stack);
    }

    private static boolean isImportant(String token) {
        return token.length() > 0 && !token.equals(".");
    }


}
