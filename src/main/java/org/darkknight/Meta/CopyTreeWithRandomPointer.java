package org.darkknight.Meta;

import java.util.HashMap;

public class CopyTreeWithRandomPointer {
    public class Node {
      int val;
      Node left;
      Node right;
         Node random;
              Node() {}
              Node(int val) { this.val = val; }
              Node(int val, Node left, Node right, Node random) {
                  this.val = val;
                  this.left = left;
                  this.right = right;
                  this.random = random;
              }
  }

    public class NodeCopy {
        int val;
        NodeCopy left;
        NodeCopy right;
        NodeCopy random;
        NodeCopy() {}
        NodeCopy(int val) { this.val = val; }
        NodeCopy(int val, NodeCopy left, NodeCopy right, NodeCopy random) {
            this.val = val;
            this.left = left;
            this.right = right;
            this.random = random;
        }
    }

    // Hashmap to map old tree's nodes with new tree's nodes.
    private HashMap<Node, NodeCopy> seen = new HashMap<>();

    // TC - o(n+3n)
    // SC - o(n)
    private  NodeCopy dfs(Node root) {
        if (root == null) {
            return null;
        }
        if (seen.containsKey(root)) {
            return seen.get(root);
        }

        NodeCopy newRoot = new NodeCopy(root.val);
        // Mark old root as seen and store its respective new node.
        seen.put(root, newRoot);

        // Traverse on all 3 edges of root and attach respective new node
        // to the newRoot.
        newRoot.left = dfs(root.left);
        newRoot.right = dfs(root.right);
        newRoot.random = dfs(root.random); // random node will be found coz previous 2 dfs will create left and right nodes
        return newRoot;
    }

    public NodeCopy copyRandomBinaryTree(Node root) {
        // Traverse on each node of the given tree.
        NodeCopy newRoot = dfs(root);
        return newRoot;
    }


}
