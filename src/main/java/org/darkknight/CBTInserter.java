package org.darkknight;

import java.util.LinkedList;
import java.util.Queue;

// https://leetcode.com/problems/complete-binary-tree-inserter
public class CBTInserter {
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

    Queue<TreeNode> queue;
    TreeNode root;
    public CBTInserter(TreeNode root) {
        this.root = root;
        queue= new LinkedList<>();
        queue.offer(root);

        while(queue.peek().right != null){
            TreeNode top = queue.poll();
            queue.offer(top.left);
            queue.offer(top.right);
        }
    }

    public int insert(int val) {
        TreeNode node = queue.peek();
        if(node.left == null){
            node.left = new TreeNode(val);
        }else{
            node.right = new TreeNode(val);
            queue.offer(node.left);
            queue.offer(node.right);
            queue.poll();
        }
        return node.val;
    }

    public TreeNode get_root() {
        return root;
    }
}
