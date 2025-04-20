package org.darkknight;

import java.util.*;

public class BinaryTreeOnes {

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

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode left1 = new TreeNode(2);
        left1.left = new TreeNode(4);
        left1.right = new TreeNode(5);

        TreeNode right1 = new TreeNode(3);
        right1.left = new TreeNode(6);
        root.left = left1;
        root.right = right1;

        BinaryTreeOnes bt = new BinaryTreeOnes();
        bt.inOrder2(root);
        bt.verticalOrder(root);
    }

    // root-left-right - preOrder
    // left-root-right - inOrder (gives non-decreasing order for BST)
    // left-right-root - ()
    private void inOrder2(TreeNode root){
        inOrderrec(root);
    }

    private void inOrderrec(TreeNode root){
         if(root == null) return;

         inOrderrec(root.left);
         System.out.println(root.val);
         inOrderrec(root.right);
    }

    private void inorderitr(TreeNode root){
         if (root == null) return;

         Stack<TreeNode> stack = new Stack<>();

         while(!stack.isEmpty() || root != null){
             while(root != null){
                 stack.push(root);
                 root = root.left;
             }

             root = stack.pop();
             System.out.println(root.val);
             root = root.right;
         }
    }

    // for min depth, BFS > DFS coz it can early exit
    // https://leetcode.com/problems/minimum-depth-of-binary-tree
    private int minDepthBFS(TreeNode root){
         if(root == null) return 0;

         Queue<TreeNode> queue = new LinkedList<>();
         int minDepth = 1;
         queue.offer(root);

         while(!queue.isEmpty()){
             int size = queue.size();

             for(int i =0; i<size; i++){
                 TreeNode curr = queue.poll();
                 if(curr == null) continue;

                 if(curr.left == null && curr.right == null) return minDepth;

                 queue.offer(curr.left);
                 queue.offer(curr.right);
             }
             ++minDepth;
         }

         // code should not reach here but return any value if queue is empty
         return -1;
    }

    // for minDepth finding, above solution with BFS is far greater coz it can early exit
    private int minDepthDFS(TreeNode root){
        if(root == null) return 0;

        if(root.left == null){
            return 1+ minDepthDFS(root.right);
        }
        else if (root.right == null){
            return 1+ minDepthDFS(root.left);
        }

        return 1+Math.min(minDepthDFS(root.left), minDepthDFS(root.right));
    }

    // for maxDepth, DFS still makes sense coz still has to go to the whole tree
    // no conditions coz keep going to other side and take max of whatever gave last
    // https://leetcode.com/problems/maximum-depth-of-binary-tree/
    public int maxDepth(TreeNode root) {
        if(root == null) return 0;

        int lDepth = maxDepth(root.left);
        int rDepth = maxDepth(root.right);

        return (Math.max(lDepth, rDepth)) + 1;
    }

    private void inOrder(TreeNode root){
         if(root == null) return;

         inOrder(root.left);
         System.out.println(root.val);
         inOrder(root.right);
    }

    // https://leetcode.com/problems/binary-tree-inorder-traversal
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if(root == null) return res;

        Stack<TreeNode> stack = new Stack<>();

        while(!stack.isEmpty() || root!= null){
            while(root!= null){
                stack.push(root);
                root = root.left;
            }

            root = stack.pop();
            res.add(root.val);
            root = root.right;
        }
        return res;
    }

    // https://leetcode.com/problems/trim-a-binary-search-tree
    public TreeNode trimBST(TreeNode root, int low, int high) {
        if(root == null) return root;

        if(root.val < low) {
            return trimBST(root.right, low, high);
        }
        else if (root.val > high) {
            return trimBST(root.left, low, high);
        }else {
            root.left = trimBST(root.left, low, high);
            root.right = trimBST(root.right, low, high);
            return root;
        }
    }

    // https://leetcode.com/problems/merge-two-binary-trees/
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if(root1 == null) return root2;
        if(root2 == null) return root1;

        root1.val = root1.val + root2.val;

        root1.left = mergeTrees(root1.left, root2.left);
        root1.right = mergeTrees(root1.right, root2.right);

        return root1;
    }

    // Encodes a tree to a single string.
    // https://leetcode.com/problems/serialize-and-deserialize-binary-tree/
    public String serialize(TreeNode root) {
        if(root == null) return "";

        StringBuilder sb = new StringBuilder();
        doSerialize(root, sb);
        return sb.toString();
    }

    private void doSerialize(TreeNode root, StringBuilder sb){
         if(root == null){
             sb.append("#").append(",");
         }
         else{
             sb.append(root.val + ",");
             doSerialize(root.left, sb);
             doSerialize(root.right, sb);
         }
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if(data == null || data.length() == 0) return null;

        Queue<String> queue = new LinkedList<>(Arrays.asList(data.split(",")));
        TreeNode newRoot = doDeSerialize(queue);
        return newRoot;
    }

    private TreeNode doDeSerialize(Queue<String> queue){
         String str = queue.poll();
         if(str.equals("#")) return null;

         TreeNode node = new TreeNode(Integer.parseInt(str));
         node.left = doDeSerialize(queue);
         node.right = doDeSerialize(queue);
         return node;
    }

    // https://leetcode.com/problems/serialize-and-deserialize-bst/description/
    public String serializeBST(TreeNode root){
         if(root == null) return "";
         StringBuilder sb = new StringBuilder();

         doSerializeBST(root, sb);
         return sb.toString();
    }

    private void doSerializeBST(TreeNode root, StringBuilder sb){
         if(root == null) return;

         sb.append(root.val).append(",");
         doSerializeBST(root.left, sb);
        doSerializeBST(root.right, sb);
    }

    public TreeNode deSerializeBST(String str){
         if(str.isEmpty()) return null;
         Queue<String> queue = new LinkedList<>(Arrays.asList(str.split(",")));
         return deserBST(queue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private TreeNode deserBST(Queue<String> queue, int lower, int upper) {
        if (queue.isEmpty()) return null;

        String str = queue.peek();
        int val = Integer.parseInt(str);
        if (val > upper) return null;
        queue.poll();
        TreeNode root = new TreeNode();
        root.left = deserBST(queue, lower, val);
        root.right = deserBST(queue, val, upper);
        return root;
    }

    List<Integer> boundary;
    // // https://leetcode.com/problems/boundary-of-binary-tree/
    public List<Integer> boundaryOfBinaryTree(TreeNode root) {
        boundary = new ArrayList<>();

        boundary.add(root.val);
        leftBoundary(root.left);
        leafBoundary(root.left);
        leafBoundary(root.right);
        rightBoundary(root.right);
        return boundary;
    }

    private void leftBoundary(TreeNode root){
        if(root == null) return;

        if(root.left != null){
            boundary.add(root.val);
            leftBoundary(root.left);
        }
        else if (root.right!= null){
            boundary.add(root.val);
            leftBoundary(root.right);
        }
    }

    // in right boundary, first recurse, then add
    // coz reverse order is needed, also right first
    private void rightBoundary(TreeNode root){
        if(root == null) return;

        if(root.right != null){
            rightBoundary(root.right);
            boundary.add(root.val);
        }
        else if (root.left!= null){
            rightBoundary(root.left);
            boundary.add(root.val);
        }
    }

    private void leafBoundary(TreeNode root){
        if(root == null) return;

        leafBoundary(root.left);
        if(root.left == null  && root.right == null) {
            boundary.add(root.val);
        }
        leafBoundary(root.right);
    }

    // https://leetcode.com/problems/second-minimum-node-in-a-binary-tree/
    public int secondMinimumNode(TreeNode root) {
        if(root == null) return -1;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        Integer secondMin = null;

        while(!queue.isEmpty()){
            TreeNode curr = queue.poll();
            if(curr.val != root.val){
                if(secondMin == null) secondMin = curr.val;
                secondMin = Math.min(secondMin, curr.val);
            }

            if(curr.right != null) queue.offer(curr.right);
            if(curr.left != null) queue.offer(curr.left);
        }
        return secondMin == null ? -1 : secondMin;
    }

    public int findPredecessorInOrder(TreeNode root, TreeNode p){
        if(root == null || p == null) return Integer.MIN_VALUE;

        int predecessor = Integer.MIN_VALUE;

        while(root!= null){
            if(p.val <= root.val){
                root = root.left;
            }
            else
            {
                predecessor = root.val;
                root = root.right;
            }
        }
        return predecessor;
    }

    private class Pair{
        TreeNode node;
        int num;
        Pair(TreeNode node, int num){
            this.node = node;
            this.num = num;
        }
    }

    // https://leetcode.com/problems/maximum-width-of-binary-tree/
    public int widthOfBinaryTree(TreeNode root){
        if(root == null) return 0;
        int ans = 0;

        Queue<Pair> queue = new LinkedList<>();
        queue.offer(new Pair(root, 0));

        while(!queue.isEmpty()){
            int size = queue.size();
            int minonthislevel = queue.peek().num;
            int first =0, last = 0;

            for(int i =0; i<size; i++){
                int curr = queue.peek().num-minonthislevel;
                TreeNode node = queue.poll().node;

                if(i == 0) first = curr;
                if(i == size-1) last = curr;

                if(node.left != null) queue.offer(new Pair(node.left, curr*2+1));
                if(node.right != null) queue.offer(new Pair(node.right, curr*2+2));
            }
            ans = Math.max(ans, last-first+1);
        }
        return ans;
    }

    public boolean findTarget(TreeNode root, int k) {
        HashSet<Integer> set = new HashSet<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        while(!q.isEmpty()){
            TreeNode node = q.poll();
            if(set.contains(k-node.val)){
                return true;
            }
            set.add(node.val);
            if(node.left!= null) q.offer(node.left);
            if(node.right!= null) q.offer(node.right);
        }
        return false;
    }

    // https://leetcode.com/problems/path-sum/
    public boolean hasPathSum(TreeNode root, int targetSum) {
         if(root == null) return false;

         targetSum -= root.val;
         if(root.left == null && root.right == null && targetSum==0){
             return true;
         }

         return hasPathSum(root.left, targetSum) || hasPathSum(root.right, targetSum);
    }

    List<List<Integer>> res;
     // https://leetcode.com/problems/path-sum-ii/
    // backtracking/dfs
    // time-comlexity(O(N^2) , space -> O(N)(N^2 if you count result else only stack space))
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        res = new ArrayList<>();
        checkPathSum(root, targetSum, new ArrayList<>());
        return res;
    }

    private void checkPathSum(TreeNode root, int remTarget, List<Integer> curr){
        if(root == null) return;

        remTarget-= root.val;
        curr.add(root.val);

        if(root.left == null && root.right == null && remTarget == 0){
            res.add(new ArrayList<>(curr));
            // why not to return ??
            // return;
        }else{
            checkPathSum(root.left, remTarget, curr);
            checkPathSum(root.right, remTarget, curr);
        }
        curr.remove(curr.size()-1);
    }

    // uses prefix sum method as in continuous sub-array problem
    // don't forget to use Long for tmpSum
    // https://leetcode.com/problems/path-sum-iii/
    HashMap<Long, Integer> indexMap;
    int count = 0;
    public int pathSumIII(TreeNode root, int targetSum) {
        if(root == null) return 0;
        indexMap = new HashMap<>();
        indexMap.put(0L, 1);
        getPathSum(root, targetSum, 0);
        return count;
    }

    private void getPathSum(TreeNode root, int targetSum, long tmpSum){
        if(root == null) return;

        tmpSum += root.val;
        if(indexMap.containsKey(tmpSum - targetSum)){
            count += indexMap.get(tmpSum - targetSum);
        }

        indexMap.put(tmpSum, indexMap.getOrDefault(tmpSum, 0)+1);

        getPathSum(root.left, targetSum, tmpSum);
        getPathSum(root.right, targetSum, tmpSum);

        if(indexMap.get(tmpSum) == 1){
            indexMap.remove(tmpSum);
        }else{
            indexMap.put(tmpSum, indexMap.get(tmpSum)-1);
        }
    }

    private int maxSum;
    // https://leetcode.com/problems/binary-tree-maximum-path-sum/
    // This is kinda like Kadane's algo, except with trees!
    public int maxPathSum(TreeNode root) {
        maxSum = Integer.MIN_VALUE;
        calculateSum(root);
        return  maxSum;
    }

    private int calculateSum(TreeNode root){
        if (root == null) return 0;

        int gainFromLeft = Math.max(calculateSum(root.left), 0);
        int gainFromRight = Math.max(calculateSum(root.right), 0);

        maxSum = Math.max(maxSum, gainFromRight+gainFromLeft+root.val);
        return Math.max(gainFromLeft + root.val, gainFromRight + root.val);
    }

    HashMap<Integer, Integer> map;
    int preOrderIndex;
    // https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
    public TreeNode buildTreeUsingPreOrder(int[] preorder, int[] inorder) {
        preOrderIndex = 0;
        map = new HashMap<>();
        for(int i = 0; i<inorder.length; i++){
            map.put(inorder[i], i);
        }
        return builder(preorder,  0, preorder.length-1);
    }

    private TreeNode builder(int[] preorder, int left, int right){
        if(left > right) return null;

        int rootValue = preorder[preOrderIndex++];
        TreeNode root = new TreeNode();
        root.val = rootValue;

        root.left = builder(preorder, left, map.get(rootValue)-1);
        root.right = builder(preorder, map.get(rootValue)+1, right);
        return root;
    }

    int postOrderIndex;
    // mind blown ... just build the right node first .. there is no other change .. even in the rec call
    // postOrder index reduces 1 everytime
    // https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/
    public TreeNode buildTreeUsingPostOrder(int[] postorder, int[] inorder) {
        postOrderIndex = postorder.length-1;
        map = new HashMap<>();
        for(int i = 0; i<inorder.length; i++){
            map.put(inorder[i], i);
        }
        return builderPost(postorder,  0, postorder.length-1);
    }

    private TreeNode builderPost(int[] postorder, int left, int right){
        if(left > right) return null;

        int rootValue = postorder[postOrderIndex--];
        TreeNode root = new TreeNode();
        root.val = rootValue;

        root.right = builderPost(postorder, map.get(rootValue)+1, right);
        root.left = builderPost(postorder, left, map.get(rootValue)-1);
        return root;
    }

    // https://leetcode.com/problems/kth-smallest-element-in-a-bst/description/
    public int kthSmallest(TreeNode root, int k) {
        Stack<TreeNode> stack = new Stack<>();
        while(!stack.isEmpty() || root!= null){
            while(root!= null){
                stack.push(root);
                root = root.left;
            }

            root = stack.pop();
            if(--k == 0) break;
            root = root.right;
        }
        return root.val;
    }

    // https://leetcode.com/problems/populating-next-right-pointers-in-each-node
    public TreeNode connectNextRightPointer(TreeNode root) {
        if(root == null) return root;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int size = queue.size();

            TreeNode prev = null;
            for(int i =0; i< size; i++){
                TreeNode curr = queue.remove();
                curr.next = prev;
                prev = curr;

                if(curr.right!= null) queue.offer(curr.right);
                if(curr.left!= null) queue.offer(curr.left);
            }
        }
        return root;
    }

    // O(1) space - awesome ... set right pointers for next level in prev step
    public TreeNode connectNextRightPointerBetter(TreeNode root) {
        if (root == null) {
            return root;
        }
        // Start with the root node. There are no next pointers
        // that need to be set up on the first level
        TreeNode leftmost = root;

        // Once we reach the final level, we are done
        while (leftmost.left != null) {
            // Iterate the "linked list" starting from the head
            // TreeNode and using the next pointers, establish the
            // corresponding links for the next level
            TreeNode head = leftmost;
            while (head != null) {
                // CONNECTION 1
                head.left.next = head.right;

                // CONNECTION 2
                if (head.next != null) {
                    head.right.next = head.next.left;
                }

                // Progress along the list (nodes on the current level)
                head = head.next;
            }

            // Move onto the next level
            leftmost = leftmost.left;
        }
        return root;
    }

    // https://leetcode.com/problems/count-good-nodes-in-binary-tree/description/
    public int goodNodes(TreeNode root) {
        count = 0;
        if(root == null) return count;
        countGoodNodes(root, Integer.MIN_VALUE);
        return count;
    }
    private void countGoodNodes(TreeNode root, int maxSoFar){
        if(root == null) return;
        if(root.val >= maxSoFar){
            count++;
        }
        maxSoFar = Math.max(maxSoFar, root.val);
        countGoodNodes(root.left, maxSoFar);
        countGoodNodes(root.right, maxSoFar);
    }

    // don't assume or rush the solution, know the why
    // good explanation here - https://www.youtube.com/watch?v=_-QHfMDde90 on why
    // https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree
    public TreeNode lowestCommonAncestorBT(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null || root == p || root == q){
            return root;
        }

        TreeNode left = lowestCommonAncestorBT(root.left, p, q);
        TreeNode right = lowestCommonAncestorBT(root.right, p, q);
        if(left == null){
            return right;
        }else if (right == null){
            return left;
        }else{
            return root;
        }

    }

    // https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/description/
    // O(n) in worst case O(logN) if tree is balanced
    public TreeNode lowestCommonAncestorBST(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null) return root;
        if(root.val > p.val && root.val > q.val){
            return lowestCommonAncestorBST(root.left, p, q);
        }
        else if(root.val < p.val && root.val < q.val){
            return lowestCommonAncestorBST(root.right, p, q);
        }
        else{
            return root;
        }
    }

    // https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-iii
    // with parent
    // /*
    //1 ------o---    1 + 2:  ------o-----o---
    //2     --o---    2 + 1:  --o---------o---
    //*/
    public TreeNode lowestCommonAncestorIII(TreeNode p, TreeNode q) {
        TreeNode first = p;
        TreeNode sec = q;

        while(first != sec){
            first = first == null ? q : first.parent;
            sec = sec == null ? p : sec.parent;
        }

        return first;
    }

    // #meta
    // they gave us individual nodes without parent pointers
    // build a parentMap, traverse all nodes and for each left and right, parent is the node
    // then just use the lowestCommonAncestorIII to finish the job
    public TreeNode lowestCommonAncestorIIIMeta(List<TreeNode> nodes, TreeNode p_start, TreeNode q_start) {
        Map<TreeNode, TreeNode> childToParent = new HashMap<>();

        // Build the child-to-parent map
        for (TreeNode node : nodes) {
            if (node.left != null) {
                childToParent.put(node.left, node);
            }
            if (node.right != null) {
                childToParent.put(node.right, node);
            }
        }

        TreeNode p = p_start;
        TreeNode q = q_start;

        // Traverse until p and q meet
        while (p != q) {
            if (childToParent.containsKey(p)) {
                p = childToParent.get(p);
            } else {
                p = q_start;
            }

            if (childToParent.containsKey(q)) {
                q = childToParent.get(q);
            } else {
                q = p_start;
            }
        }

        return p;
    }

    private boolean found_p;
    private boolean found_q;
    // one where nodes may not exist
    // in that case, you gotta look for the node in whole tree, not just exit when one node is found
    // https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-ii/
    public TreeNode lowestCommonAncestorII(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode lca = lca(root, p, q);
        if(found_p && found_q){
            return lca;
        }
        return null;
    }
    private TreeNode lca(TreeNode root, TreeNode p, TreeNode q){
        if(root == null) return null;

        TreeNode left = lca(root.left, p, q);
        TreeNode right = lca(root.right, p, q);

        if(root == p){
            found_p = true;
            return root;
        }
        if(root == q){
            found_q = true;
            return root;
        }
        if(left != null && right != null){
            return root;
        }
        return left == null ? right : left;
    }


    // you can't access the tree-node besides it's left and right child
    // idea is to create a parent map so that you can traverse backwards
    // and start dfs-ing from target node in all directions including parent (left right parent)
    // https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/
    HashMap<TreeNode, TreeNode> parentMap;
    List<Integer> kres;
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        parentMap = new HashMap<>();
        kres = new ArrayList<>();
        if(root == null) return kres;

        buildParentMap(root, null);
        dfsKDistance(target, k, new HashSet<>());
        return kres;
    }

    private void dfsKDistance(TreeNode target, int k, Set<TreeNode> visited){
        if(target == null || visited.contains(target)) return;

        visited.add(target);
        if(k == 0){
            kres.add(target.val);
            return;
        }

        dfsKDistance(target.left, k-1, visited);
        dfsKDistance(target.right, k-1, visited);
        dfsKDistance(parentMap.get(target), k-1, visited);
    }

    private void buildParentMap(TreeNode root, TreeNode parent){
        if(root == null) return;

        parentMap.put(root, parent);

        buildParentMap(root.left, root);
        buildParentMap(root.right, root);
    }

    // https://leetcode.com/problems/binary-tree-right-side-view/
    // O(N), O(H)
    //         1
    //       / \
    //      2   3
    //       \    \
    //        5    4
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if(root == null) return res;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int size = queue.size();

            List<Integer> tmp = new ArrayList<>();
            for(int i = 0; i<size; i++){
                TreeNode node = queue.poll();

                if(node.left != null) queue.offer(node.left);
                if(node.right != null) queue.offer(node.right);

                if(i == size -1){
                    res.add(node.val);
                }
            }
        }
        return res;
    }

    // get both left and right side view , add root only once
    // meta variant
    // also they can ask to just print-output in lots of cases
    public static List<Integer> rightSideViewMeta(TreeNode root) {
        if (root == null) return new ArrayList<>();

        List<Integer> rightView = new ArrayList<>();
        List<Integer> leftView = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();

        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            Integer leftMost = null, rightMost = null;

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                if (i == 0) leftMost = node.val;  // Leftmost node
                if (i == levelSize - 1) rightMost = node.val;  // Rightmost node

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            if (leftMost != null) leftView.add(leftMost);
            if (rightMost != null) rightView.add(rightMost);
        }

        // Reverse the left view for bottom-to-top order
        Collections.reverse(leftView);

        // Combine both views
        leftView.addAll(rightView);
        return leftView;
    }

    // right-side-view-DFS
    // // O(N), O(H)
    public List<Integer> rightSideViewDFS(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        rightView(root, result, 0);
        return result;
    }

    public void rightView(TreeNode curr, List<Integer> result, int currDepth){
        if(curr == null){
            return;
        }
        if(currDepth == result.size()){
            result.add(curr.val);
        }

        rightView(curr.right, result, currDepth + 1);
        rightView(curr.left, result, currDepth + 1);

    }

    // https://leetcode.com/problems/binary-tree-level-order-traversal/
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
         if(root == null) return res;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int size = queue.size();

            List<Integer> tmp = new ArrayList<>();
            for(int i = 0; i<size; i++){
                TreeNode node = queue.poll();
                tmp.add(node.val);

                if(node.left != null) queue.offer(node.left);
                if(node.right != null) queue.offer(node.right);
            }
            res.add(tmp);
        }
        return res;
    }

    // https://leetcode.com/problems/binary-tree-level-order-traversal-ii/
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if(root == null) return res;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int size = queue.size();

            List<Integer> local = new ArrayList<>();
            for(int i=0; i<size; i++){
                TreeNode curr = queue.poll();
                local.add(curr.val);

                if(curr.left!= null) queue.offer(curr.left);
                if(curr.right!= null) queue.offer(curr.right);
            }
            res.add(0, local);
        }
        return res;
    }

    // https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if(root == null) return res;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean zigzag = true;

        while(!queue.isEmpty()){
            int size = queue.size();

            List<Integer> tmp = new ArrayList<>();
            for(int i=0; i<size; i++){
                TreeNode curr = queue.poll();

                if(zigzag){
                    tmp.add(curr.val);
                }else{
                    tmp.add(0, curr.val);
                }

                if(curr.left!= null) queue.offer(curr.left);
                if(curr.right!= null) queue.offer(curr.right);
            }
            zigzag = !zigzag;
            res.add(tmp);
        }

        return res;
    }

    // did you see the subtle diff from preOrder (it's like rev the ans so add to the start of list)
    // https://leetcode.com/problems/binary-tree-postorder-traversal/
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();

        if(root == null) return res;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while(!stack.isEmpty()){
            TreeNode node = stack.pop();
            res.add(0, node.val);

            if(node.left != null){
                stack.push(node.left);
            }

            if(node.right!= null){
                stack.push(node.right);
            }
        }
        return res;
    }

    // https://leetcode.com/problems/binary-tree-preorder-traversal/
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();

        if(root == null) return res;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while(!stack.isEmpty()){
            TreeNode node = stack.pop();
            res.add(node.val);

            if(node.right!= null){
                stack.push(node.right);
            }

            if(node.left != null){
                stack.push(node.left);
            }
        }
        return res;
    }

    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if(root == null) return false;

        if(check(root, subRoot)) return true;
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    private boolean check(TreeNode root, TreeNode subRoot){
         if(root == null && subRoot == null) return true;
        if(root == null || subRoot == null) return false;
        if(root.val != subRoot.val) return false;

        return check(root.left, subRoot.left) && check(root.right, subRoot.right);

    }

    // https://leetcode.com/problems/same-tree/
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p == null && q == null) return true;
        if(p == null || q == null) return false;

        if(p.val != q.val) return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    // https://leetcode.com/problems/balanced-binary-tree/description/
    public boolean isBalanced(TreeNode root) {
         if(root == null) return true;

        return Math.abs(maxDepth(root.left) - maxDepth(root.right)) < 2
        && isBalanced(root.left)
        && isBalanced(root.right);
    }

    private int diameter;
    // https://leetcode.com/problems/diameter-of-binary-tree
    public int diameterOfBinaryTree(TreeNode root) {
         diameter = 0;
         longestPath(root);
         return diameter;
    }

    private int longestPath(TreeNode node){
        if(node == null) return 0;

        int leftPath = longestPath(node.left);
        int rightPath = longestPath(node.right);

        diameter = Math.max(diameter, leftPath+rightPath);
        return Math.max(leftPath, rightPath)+1;

    }

    int maxDiameterNAry = 0;
    // https://leetcode.com/problems/diameter-of-n-ary-tree/
    public int diameterNaryTree(TreeNode root) {
        getHeightNary(root);
        return maxDiameterNAry;
    }
//            1
//         / | \
//         2  3  4     ->>
//         / \   |
//         5   6  7
//                1 (H=3)
//            /  |   \
//            (H=2)  (H=2)  (H=1)
//            2     3      4
//            / \     |
//            (H=1) (H=1) (H=1)
//            5    6     7
    public int getHeightNary(TreeNode root) {
        if(root == null)
            return 0;

        int max1 = 0;
        int max2 = 0;
        /* this is needed
        for(TreeNode child : root.children) {
            int height = getHeightNary(child);
            if(height > max1) {
                max2 = max1;
                max1 = height;
            }
            else if(height > max2) {
                max2 = height;
            }
        }
        */
        maxDiameterNAry = Math.max(maxDiameterNAry, max1+max2);
        return max1+1;
    }

    // https://leetcode.com/problems/invert-binary-tree/
    private TreeNode invertTree(TreeNode root) {
        if(root == null) return root;

        TreeNode tmpNode = root.left;
        root.left = root.right;
        root.right = tmpNode;

        invertTree(root.left);
        invertTree(root.right);

        return root;
    }

    List<String> resPaths;
    // https://leetcode.com/problems/binary-tree-paths/
    public List<String> binaryTreePaths(TreeNode root) {
        resPaths = new ArrayList<>();
        paths(root, "");
        return resPaths;
    }

    private void paths(TreeNode root, String sofar){
        if(root == null) return;

        sofar += root.val;
        if(root.left == null && root.right == null){
            resPaths.add(sofar);
        }

        sofar += "->";
        paths(root.left, sofar);
        paths(root.right, sofar);
    }

    private class SNode{
        TreeNode node;
        int loc;
        SNode(TreeNode node, int loc){
            this.node = node;
            this.loc = loc;
        }
    }

    // https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/description/
    // HARD - sorting one
    // The comparator compares columns first, then rows, then values. Thus we'll get the most left nodes first and at
    // the same row sorted by value
    // DFS the tree and put a triplet of {row, col, val} in to the PriorityQueue
    // Pull triplets from the PQ layer by layer by comparing the column field.
    public List<List<Integer>> verticalOrder(TreeNode root) {
        if (root == null)
            return Collections.emptyList();
        var queue = new PriorityQueue<int[]>((a, b) -> {
            // int[] {row, col, val}
            if (a[1] == b[1]) { // col
                if (a[0] == b[0]) { // row
                    return Integer.compare(a[2], b[2]); // values
                }
                return Integer.compare(a[0], b[0]);
            }
            return Integer.compare(a[1], b[1]);
        });
        helper(root, 0, 0, queue);
        var result = new ArrayList<List<Integer>>();
        while (!queue.isEmpty()) {
            var layer = new ArrayList<Integer>();
            int[] node = queue.poll();
            layer.add(node[2]);
            while (!queue.isEmpty() && queue.peek()[1] == node[1]) {
                int[] tmp = queue.poll();
                layer.add(tmp[2]);
            }
            result.add(layer);
        }
        return result;
    }

    private void helper(TreeNode root, int row, int col, PriorityQueue<int[]> queue) {
        if (root == null)
            return;
        helper(root.left, row + 1, col - 1, queue);
        queue.offer(new int[] { row, col, root.val });
        helper(root.right, row + 1, col + 1, queue);
    }

    // https://leetcode.com/problems/binary-tree-vertical-order-traversal/
    // without-sorting one - (easy)
    public List<List<Integer>> verticalOrderOG(TreeNode root) {
        if (root == null) return new ArrayList<>();
        Queue<SNode> queue = new LinkedList<>();
        HashMap<Integer, List<Integer>> map = new HashMap<>();

        queue.offer(new SNode(root, 0));

        int min = 0, max = 0;

        while(!queue.isEmpty()){
            int size = queue.size();

            for(int i =0; i<size; i++){
                SNode curr = queue.poll();

                if(!map.containsKey(curr.loc)){
                    map.put(curr.loc, new ArrayList<>());
                }

                map.get(curr.loc).add(curr.node.val);

                TreeNode temp = curr.node;

                if(temp.left!= null){
                    queue.offer(new SNode(temp.left, curr.loc-1));
                    min = Math.min(min, curr.loc-1);
                }
                if(temp.right!= null) {
                    queue.offer(new SNode(temp.right, curr.loc+1));
                    max = Math.max(max, curr.loc+1);
                }
            }
        }

        List<List<Integer>> res = new ArrayList<>();

        for(int i =min; i<= max; i++){
            res.add(map.get(i));
        }

        return res;
    }

    // https://leetcode.com/problems/average-of-levels-in-binary-tree/
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> res = new ArrayList<>();
        if (root == null) return res;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            int size = queue.size();

            double val = 0.0;
            for(int i =0 ; i< size; i++){
                TreeNode node = queue.remove();
                val += node.val;

                if(node.left != null) queue.offer(node.left);
                if(node.right != null) queue.offer(node.right);
            }
            res.add(val/size);
        }
        return res;
    }
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null || root == p || root == q) return root;

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if(left == null) return right;
        if(right == null) return left;
        else{
            return root;
        }
    }

    // https://leetcode.com/problems/closest-binary-search-tree-value/
    // binary search - O(H), space - O(1)
    // #meta - well explained in the end by chatgpt
    public int closestValue(TreeNode root, double target) {
        int result = root.val;
        while (root != null) {
            // if I get closer, only then I update the result... else res would have been already found ... still we do check
            // till leaf node and that's why O(H) complexity
            if (Math.abs(target - root.val) < Math.abs(target - result) ||
                    Math.abs(target - root.val) == Math.abs(target - result) && root.val < result) {
                result = root.val;
            }

            root = target > root.val ? root.right : root.left;
        }
        return result;
    }

    // https://leetcode.com/problems/flatten-binary-tree-to-linked-list/
    // binary tree to linkedList
    // used postOrder traversal
    private TreeNode prev = null;
    public void flatten(TreeNode root) {
        if (root == null)
            return;
        flatten(root.right);
        flatten(root.left);
        root.right = prev;
        root.left = null;
        prev = root;
    }

    int total;
    // https://leetcode.com/problems/sum-root-to-leaf-numbers
    public int sumRootToLeafPaths(TreeNode root) {
        total = 0;
        dfsHelper(root, 0);
        return total;
    }

    private void dfsHelper(TreeNode root, int sum){
        if(root == null) return;

        sum = sum * 10 + root.val;

        if(root.left == null && root.right == null){
            total += sum;
            return;
        }

        dfsHelper(root.left, sum);
        dfsHelper(root.right, sum);
    }

    // #meta - sum-root-to-leaf-numbers
    private void dfsHelperForLargerVal(TreeNode root, int sum){
        if(root == null) return;

        // to handle 3 + 79 => 379 (else would become 3*10 + 79), we want to prefix prev value
        int places = root.val == 0 ? 10 : 1;
        int node_val = root.val;
        while (node_val > 0) {
            node_val /= 10;
            places *= 10;
        }
        sum = sum * places + root.val;

        if(root.left == null && root.right == null){
            total += sum;
            return;
        }

        dfsHelperForLargerVal(root.left, sum);
        dfsHelperForLargerVal(root.right, sum);
    }

    // VARIANT: What if you had to ignore negative signs in your calculations until
    // you reached a leaf node, and instead, only consider signage if a root-to-leaf path is
    // a "negative path"?
    private static void sumRootToLeafPathsMeta(TreeNode root, int[] totalSum, int currSum, int numNegatives) {
        if (root == null) return;

        currSum = (currSum * 10) + Math.abs(root.val);
        if (root.val < 0) {
            numNegatives++;
        }

        // If it's a leaf node
        if (root.left == null && root.right == null) {
            int sign = (numNegatives % 2 == 1) ? -1 : 1;
            totalSum[0] += currSum * sign;
            return;
        }

        // Recursive traversal
        sumRootToLeafPathsMeta(root.left, totalSum, currSum, numNegatives);
        sumRootToLeafPathsMeta(root.right, totalSum, currSum, numNegatives);
    }

    private int max = 0;
    // https://leetcode.com/problems/binary-tree-longest-consecutive-sequence
    // #meta
    public int longestConsecutive(TreeNode root) {
        if(root == null) return 0;
        helper(root, 0, root.val);
        return max;
    }

    public void helper(TreeNode root, int cur, int target){
        if(root == null) return;
        if(root.val == target) cur++;
        else cur = 1;
        max = Math.max(cur, max);
        helper(root.left, cur, root.val + 1);
        helper(root.right, cur, root.val + 1);
    }
}
