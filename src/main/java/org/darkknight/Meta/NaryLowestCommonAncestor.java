package org.darkknight.Meta;

import java.util.*;

public class NaryLowestCommonAncestor {
    private static class Node {
        int val;
        List<Node> children;

        public Node(int val) {
            this.val = val;
            this.children = new ArrayList<>();
        }
    }

    // lowest-common-ancestor-of-a-n-ary-tree
    public static Node findLCA(Node root, Node p, Node q) {
        if (root == null || root == p || root == q) {
            return root;  // Base case: If root is null or one of the nodes, return root
        }

        Node lca = null;
        int count = 0; // Count of non-null children returning a node

        for (Node child : root.children) {
            Node res = findLCA(child, p, q);
            if (res != null) {
                count++;
                lca = res; // Store last non-null found
            }
        }

        // If two or more children return non-null, current node is LCA
        return (count >= 2) ? root : lca;
    }

    // Example Usage
    public static void main(String[] args) {
        Node root = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);

        root.children.add(node2);
        root.children.add(node3);
        root.children.add(node4);
        node2.children.add(node5);
        node2.children.add(node6);
        node3.children.add(node7);

        Node lca = findLCA(root, node5, node6);
        System.out.println("LCA of 5 and 6: " + (lca != null ? lca.val : "null")); // Output: 2

        lca = findLCA(root, node5, node7);
        System.out.println("LCA of 5 and 7: " + (lca != null ? lca.val : "null")); // Output: 1
    }
}
