package org.darkknight;

import java.util.Stack;

public class TreeAdditional {
    private class TreeNode {
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

    // #meta
    // https://leetcode.com/problems/construct-binary-tree-from-string
    // 4(2(3)(1))(6(5))
    public TreeNode stringToTree(String s) {
        Stack<TreeNode> stack = new Stack<>();
        for(int i = 0, j=i; i < s.length(); i++, j=i){
            char ch = s.charAt(i);

            if(ch == ')'){
                stack.pop();
            }
            else if (Character.isDigit(ch) || ch == '-'){
                while(i+1 < s.length() && Character.isDigit(s.charAt(i+1))) {
                    i++;
                }
                TreeNode node = new TreeNode(Integer.valueOf(s.substring(j, i+1)));
                if(!stack.isEmpty()){
                    TreeNode parent = stack.peek();
                    if(parent.left != null) parent.right = node;
                    else parent.left = node;
                }
                stack.push(node);
            }
        }

        return stack.isEmpty() ? null : stack.peek();
    }
}
