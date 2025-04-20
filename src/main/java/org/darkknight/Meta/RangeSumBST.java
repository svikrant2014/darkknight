package org.darkknight.Meta;

import java.util.*;

public class RangeSumBST {
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

    // #meta
    // prefix-range-sum-BST

    List<Integer> values = new ArrayList<>();
    List<Integer> prefixSums = new ArrayList<>();

    public void preprocess(TreeNode root) {
        inOrder(root);
        buildPrefixSum();
    }

    private void inOrder(TreeNode root) {
        if (root == null) return;
        inOrder(root.left);
        values.add(root.val);
        inOrder(root.right);
    }

    private void buildPrefixSum() {
        int sum = 0;
        for (int val : values) {
            sum += val;
            prefixSums.add(sum);
        }
    }

    public int rangeSumBST(int low, int high) {
        int left = lowerBound(values, low);
        int right = upperBound(values, high);

        if (left >= values.size() || right < 0 || left > right) {
            return 0;
        }

        int sumRight = prefixSums.get(right);
        int sumLeft = (left > 0) ? prefixSums.get(left - 1) : 0;
        return sumRight - sumLeft;
    }

    private int lowerBound(List<Integer> arr, int target) {
        int left = 0, right = arr.size() - 1;
        int ans = arr.size();
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr.get(mid) >= target) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }

    private int upperBound(List<Integer> arr, int target) {
        int left = 0, right = arr.size() - 1;
        int ans = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr.get(mid) <= target) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return ans;
    }
}
