package com.shawn.touchstone.alg;

import java.util.LinkedList;
import java.util.Queue;

public class ConnectAllSibling {

    public static void connect(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode prev = null;
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            if (curr.left != null) {
                queue.offer(curr.left);
            }
            if (curr.right != null) {
                queue.offer(curr.right);
            }
            if (prev != null) {
                prev.next = curr;
            }
            prev = curr;
        }
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode next;

        TreeNode(int x) {
            val = x;
            left = right = next = null;
        }
    }
}
