package org.darkknight;

import java.util.*;

public class BinarySearchTree {
    class Nodee{
        int val;
        Nodee left;
        Nodee right;

        public Nodee(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }
    }
    private class TreeNode {
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

    public static void main(String[] args) {
        
    }
    // https://leetcode.com/problems/validate-binary-search-tree/description/
    public boolean isValidBST(TreeNode root) {
        if(root == null) return true;

        return checkBST(root);
    }

    TreeNode prev = null;
    private boolean checkBST(TreeNode root){
        if(root == null) return true;

        if(!checkBST(root.left)) return false;
        if(prev != null && prev.val >= root.val) return false;
        prev = root;
        if(!checkBST(root.right)) return false;

        return true;
    }

    // https://leetcode.com/problems/recover-binary-search-tree
    public void recoverTree(TreeNode root) {
        if(root == null) return;

        TreeNode prev = null;
        TreeNode firstPoint = null;
        TreeNode lastPoint = null;

        Stack<TreeNode> stack = new Stack<>();


        while(!stack.isEmpty() || root!= null){
            while(root!= null){
                stack.push(root);
                root = root.left;
            }

            root = stack.pop();
            if(prev!= null && prev.val > root.val){
                if(firstPoint == null){
                    firstPoint = prev;
                }
                lastPoint = root;
            }
            prev = root;
            root = root.right;
        }

        int tmp = firstPoint.val;
        firstPoint.val = lastPoint.val;
        lastPoint.val = tmp;
    }

    // Morris traversal for above
    // https://leetcode.com/problems/recover-binary-search-tree/
    public static void recoverTreeInConstantSpace(Nodee root) {
        Nodee pre = null;
        Nodee first = null, second = null;
        // Morris Traversal
        Nodee temp = null;
        while(root!=null){
            if(root.left!=null){
                // connect threading for root
                temp = root.left;
                while(temp.right!=null && temp.right != root)
                    temp = temp.right;
                // the threading already exists
                if(temp.right!=null){
                    if(pre!=null && pre.val > root.val){
                        if(first==null){first = pre;second = root;}
                        else{second = root;}
                    }
                    pre = root;

                    temp.right = null;
                    root = root.right;
                }else{
                    // construct the threading
                    temp.right = root;
                    root = root.left;
                }
            }else{
                if(pre!=null && pre.val > root.val){
                    if(first==null){first = pre;second = root;}
                    else{second = root;}
                }
                pre = root;
                root = root.right;
            }
        }
        // swap two node values;
        if(first!= null && second != null){
            int t = first.val;
            first.val = second.val;
            second.val = t;
        }
    }

    // https://leetcode.com/problems/convert-binary-search-tree-to-sorted-doubly-linked-list/
    // exactly same as above just map the pointers with correct one
    public static Nodee treeToDoublyLinkedList(Nodee root){
        if(root == null) return null;
        Stack<Nodee> s = new Stack<>();

        Nodee curr = root;
        Nodee start = root;
        while(start.left != null){
            start = start.left;
        }

        Nodee prev = null;
        while(curr != null || !s.isEmpty()){
            while(curr != null){
                s.push(curr);
                curr = curr.left;
            }

            curr = s.pop();
            System.out.println(curr.val);
            if(prev != null){
                prev.right = curr;
                curr.left = prev;
            }
            prev = curr;
            curr = curr.right;
        }
        // in the end to join tail to the head for doubly
        start.left = prev;
        prev.right = start;
        return start;
    }

    private TreeNode previous = null;
    private TreeNode head = null;

    // using DFS - convert-binary-search-tree-to-sorted-doubly-linked-list
    public TreeNode treeToDoublyList(TreeNode root) {
        if (root == null) return null;

        dfs(root);

        // Optional: Make it circular if needed
        // head.left = prev;
        // prev.right = head;

        return head;
    }

    private void dfs(TreeNode node) {
        if (node == null) return;

        dfs(node.left); // Traverse left subtree

        if (previous == null) {
            head = node; // Found the smallest node
        } else {
            previous.right = node; // Link previous node (right pointer)
            node.left = previous;  // Link current node (left pointer)
        }

        previous = node; // Update prev to current node

        dfs(node.right); // Traverse right subtree
    }

    // https://leetcode.com/problems/cousins-in-binary-tree/
    // follows same level order traversal
    // if they are found at the same level but not right & left of same node... they are cousins
    public static boolean isCousins(Nodee root, int x, int y){
        Queue<Nodee> queue = new LinkedList<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int size = queue.size();
            boolean aExists = false;
            boolean bExists = false;

            for (int i = 0; i < size; i++) {
                Nodee tmp = queue.poll();
                if(tmp.val == x) aExists = true;
                if(tmp.val == y) bExists = true;

                if(tmp.left!= null && tmp.right != null){
                    if (tmp.left.val == x && tmp.right.val == y) {
                        return false;
                    }
                    if (tmp.left.val == y && tmp.right.val == x) {
                        return false;
                    }
                }

                if(tmp.left != null) queue.offer(tmp.left);
                if(tmp.right != null) queue.offer(tmp.right);
            }

            if(aExists && bExists) return true;
        }
        return false;
    }

    // https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree
    public TreeNode sortedArrayToBST(int[] nums) {
        return CreateBST(nums, 0, nums.length - 1);
    }

    private TreeNode CreateBST(int nums[], int l, int r) {
        if (l > r) { // Base Condition or Recursion Stoping Condition
            return null;
        }
        // so basically in this question we have to convert sorted array to height
        // balanced tree
        // so if we directly create tree in given sorted order it will become linked
        // list
        // so we have to take middle element as head value such it will become height
        // balanced tree
        int mid = l + (r - l) / 2; // this is the formula to find mid value
        TreeNode root = new TreeNode(nums[mid]); // mid value or median
        root.left = CreateBST(nums, l, mid - 1); // assign the value for left of subtree that is l to mid -1 for given
        // array
        root.right = CreateBST(nums, mid + 1, r); // assign the value for right go subtree that is mid+1 to r for given
        // array
        return root;
    }

    //  just need to find the smallest one that is larger than the given value
    //  since there are no duplicate values in a BST
    // https://leetcode.com/problems/inorder-successor-in-bst/
    // O(logN), space -> O(1)
    public TreeNode inorderSuccessorBST(TreeNode root, TreeNode p) {
        if(root == null || p == null) return null;

        TreeNode successor = null;
        while(root!= null){
            if(p.val >= root.val){
                root = root.right;
            }else{
                successor = root;
                root = root.left;
            }
        }
        return successor;
    }

    // https://leetcode.com/problems/serialize-and-deserialize-bst/
    public void serializeDeserialize(Nodee root){
        StringBuilder sb = new StringBuilder();
        serializeRec(root, sb);
        String serializedString = sb.toString();

        Queue<String> queue = new LinkedList<>(Arrays.asList(serializedString.split(",")));

        // this is exactly same as BT but using a little trick
        // to make it super compact and we don't have to add 'X' while serializing
        // refer note below
        Nodee tree = deSerializeRec(queue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private void serializeRec(Nodee root, StringBuilder sb){
        if (root == null) return;

        sb.append(root.val).append(",");
        serializeRec(root.left, sb);
        serializeRec(root.right, sb);
    }

    //  According to the Binary search rule, only the lower values than the root can be added to the left child of the tree.
//  This is done by creating a boundary such that if the value > higher bound, do not add it to left child i.e. Val > upper=> return null condition
    private Nodee deSerializeRec(Queue<String> queue, int lower, int upper) {
        if(queue.isEmpty()) return null;

        int val = Integer.parseInt(queue.peek());

        if(val < lower || val > upper) return null;
        queue.poll();

        Nodee newNodee = new Nodee(val);
        newNodee.left = deSerializeRec(queue, lower, val);
        newNodee.right = deSerializeRec(queue, val, upper);

        return newNodee;
    }

    // https://leetcode.com/problems/delete-node-in-a-bst/
    public TreeNode deleteNode(TreeNode root, int key) {
        if(root == null) return null;

        if(root.val < key){
            root.right = deleteNode(root.right, key);
        }
        else if(root.val > key){
            root.left = deleteNode(root.left, key);
        }else{
            if(root.left != null && root.right != null){
                int value = findMax(root.left, Integer.MIN_VALUE);
                root.val = value;
                root.left = deleteNode(root.left, value);
            }
            else if (root.left!= null){
                return root.left;
            }
            else if (root.right != null){
                return root.right;
            }
            else{
                return null;
            }
        }
        return root;
    }

    private int findMax(TreeNode root, int max){
        while(root != null){
            max = Math.max(max, root.val);
            root = root.right;
        }
        return max;
    }

    HashSet<Integer> set = new HashSet<>();

    // https://leetcode.com/problems/two-sum-iv-input-is-a-bst
    public boolean twoSumBST(TreeNode root, int k) {
        if(root == null) return false;

        int tmp = k-root.val;
        if(set.contains(tmp)) return true;

        set.add(root.val);
        return twoSumBST(root.right, k)
                || twoSumBST(root.left, k);

    }

    int ans;
    // https://leetcode.com/problems/range-sum-of-bst
    public int rangeSumBST(TreeNode root, int low, int high) {
        ans = 0;
        dfs(root, low, high);
        return ans;
    }

    // noted that a node can be in path but not counted as it's out of range
    // for the first example, it is 7+10+15=32, 5 is in the path but not counted
    public void dfs(TreeNode root, int low, int high) {
        if(root == null) return;

        if(root.val >= low && root.val <= high){
            ans += root.val;
        }

        if(root.val > low) dfs(root.left, low, high);
        if (root.val < high) dfs(root.right, low, high);
    }

    public int rangeSumBSTIterative(TreeNode root, int low, int high) {
        int ans = 0;
        Stack<TreeNode> stack = new Stack();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node != null) {
                if (low <= node.val && node.val <= high)
                    ans += node.val;
                if (low < node.val)
                    stack.push(node.left);
                if (node.val < high)
                    stack.push(node.right);
            }
        }
        return ans;
    }
}
