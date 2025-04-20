package org.darkknight;

import java.util.*;

public class LazyPostorderIterator {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);

        LazyPostorderIterator  iter = new LazyPostorderIterator (root);
        while (iter.hasNext()) {
            System.out.print(iter.next() + " "); // Expected: 4 5 2 6 7 3 1
        }
    }
    private Stack<TreeNode> stack;
    private TreeNode lastVisited;

    public LazyPostorderIterator(TreeNode root) {
        stack = new Stack<>();
        lastVisited = null;
        pushLeftmost(root);
    }

    private void pushLeftmost(TreeNode node) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }

    public int next() {
        while (hasNext()) {
            TreeNode current = stack.peek();

            // If there's an unvisited right child, process it first
            if (current.right != null && lastVisited != current.right) {
                pushLeftmost(current.right);
            } else {
                // If both left and right are processed, pop and return
                stack.pop();
                lastVisited = current;
                return current.val;
            }
        }
        throw new RuntimeException("No more elements!");
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode next;

        TreeNode parent;

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
}
