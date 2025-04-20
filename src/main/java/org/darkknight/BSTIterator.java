package org.darkknight;
import java.util.*;

public class BSTIterator {
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode next;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    Stack<TreeNode> stack;
    // represent  in-order traversal
    public BSTIterator(TreeNode root) {
        stack = new Stack<>();
        while(root!= null){
            stack.push(root);
            root = root.left;
        }
    }

    public int next() {
        TreeNode top = stack.pop();
        TreeNode right = top.right;
        while(right != null){
            stack.push(right);
            right = right.left;
        }
        return top.val;
    }

    public boolean hasNext() {
        return (!stack.isEmpty());
    }
}
