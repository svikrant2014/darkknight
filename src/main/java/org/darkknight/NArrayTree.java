package org.darkknight;

import java.util.*;

// https://leetcode.com/problems/serialize-and-deserialize-n-ary-tree
public class NArrayTree {
    class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    };

    // Encodes a tree to a single string.
    public String serialize(Node root) {
        if(root == null) return "";

        StringBuilder sb = new StringBuilder();
        ser(root, sb);
        return sb.toString();
    }

    private void ser(Node root, StringBuilder sb){
        if(root == null) return;
        else{
            sb.append(root.val + ",");
            sb.append(root.children.size() + ",");

            for(Node child : root.children){
                ser(child, sb);
            }
        }
    }

    // Decodes your encoded data to tree.
    public Node deserialize(String data) {
        if(data.isEmpty()) return null;

        Queue<String> queue = new LinkedList<>(Arrays.asList(data.split(",")));
        Node newRoot = deser(queue);
        return newRoot;
    }

    private Node deser(Queue<String> queue){
        Node node = new Node();
        int val = Integer.parseInt(queue.poll());
        node.val = val;
        int size = Integer.parseInt(queue.poll());
        node.children = new ArrayList<Node>(size);

        for(int i=0; i<size; i++){
            node.children.add(deser(queue));
        }
        return node;
    }

    // https://leetcode.com/problems/n-ary-tree-preorder-traversal
    public List<Integer> nArrayPreorder(Node root) {
        List<Integer> res = new ArrayList<>();
        if(root == null) return res;

        Stack<Node> stack = new Stack<>();
        stack.push(root);

        while(!stack.isEmpty()){
            Node curr = stack.pop();

            res.add(curr.val);

            Collections.reverse(curr.children);
            for (Node item : curr.children) {
                stack.add(item);
            }
        }

        return res;
    }

    // https://leetcode.com/problems/n-ary-tree-level-order-traversal/
    public List<List<Integer>> nArraylevelOrder(Node root) {
        List<List<Integer>> res = new ArrayList<>();
        if(root == null) return res;

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int size = queue.size();

            List<Integer> local = new ArrayList<>();
            for(int i=0; i<size; i++){
                Node curr = queue.remove();
                local.add(curr.val);

                for(Node child : curr.children){
                    queue.offer(child);
                }
            }
            res.add(local);
        }
        return res;
    }

    // https://leetcode.com/problems/n-ary-tree-postorder-traversal/
    public List<Integer> postorder(Node root) {
        List<Integer> result = new ArrayList<>();

        // If the root is null, return the empty list
        if (root == null) return result;

        Stack<Node> stack = new Stack<>();
        stack.add(root);

        // Traverse the tree using the stack
        while (!stack.isEmpty()) {
            Node currentNode = stack.pop();

            result.add(currentNode.val);
            // Push all the children of the current node to the stack
            for (Node child : currentNode.children) stack.add(child);
        }

        // Reverse the result list to get the correct postorder traversal
        Collections.reverse(result);
        return result;
    }
}
