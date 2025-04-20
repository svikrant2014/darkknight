package org.darkknight;

import java.util.PriorityQueue;

public class SortingAlgos {

    public static void main(String[] args) {
        SortingAlgos sorting = new SortingAlgos();
        int[] nums = new int[]{3,5,1,1,5,6,9};
        sorting.quickSort(nums, 0, 6);
    }


    // https://leetcode.com/problems/kth-largest-element-in-an-array/
    // O(N) best case / O(N^2) worst case running time + O(1) memory
    public int findKthLargestSimple(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int val : nums) {
            pq.offer(val);

            if(pq.size() > k) {
                pq.poll();
            }
        }
        return pq.peek();
    }

    // this is better and works for all leetcode tests besides 1 (forget about that)
    // selection sort based on partition (same as quick sort)
    // better soln for above problem
    // O(N) guaranteed running time + O(1) space
    public int findKthLargestBetter(int[] nums, int k){
         k = nums.length-k; // k = k - 1 for kth smallest
         int lo = 0;
         int hi = nums.length-1;

         while(lo<hi){
             int j = partition(nums, lo, hi);
             if(j < k){
                 lo = j+1;
             }
             else if (j > k){
                 hi = j-1;
             }else{
                 break;
             }
         }
         return nums[k];
    }

    private int partition(int[] nums, int start, int end){
        int pivot = nums[end];
        int pIndex = start;

        for(int i=start; i<end; i++){
            if(nums[i]<=pivot){
                swap(nums, i, pIndex++);
            }
        }
        swap(nums, pIndex, end);
        return pIndex;
    }

    public void quickSort(int[] nums, int start, int end){
        if(nums.length <= 1) return;
        if(start >= end) return;

        int pIndex = partition(nums, start, end);
        quickSort(nums, start, pIndex-1);
        quickSort(nums, pIndex+1, end);
    }

    private void swap(int[] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    // is a non-comparison sorting algorithm. It can be used to sort an array of positive integers
    // to handle negatives, we use (max-min)
    // O(N) ... this is tricky though
    public int findKthLargestCounting(int[] nums, int k) {
        int minValue = Integer.MAX_VALUE;
        int maxValue = Integer.MIN_VALUE;

        for(int num : nums){
            minValue = Math.min(minValue, num);
            maxValue = Math.max(maxValue, num);
        }

        int[] count = new int[maxValue - minValue+1];

        for(int num : nums){
            count[num-minValue]++;
        }

        int remain = k;

        for(int num = count.length-1; num >=0 ; num--){
            remain -= count[num];
            if(remain <= 0 ){
                return num+minValue;
            }
        }
        return -1;
    }

     public class ListNode {
          int val;
          ListNode next;
          ListNode() {}
          ListNode(int val) { this.val = val; }
          ListNode(int val, ListNode next) { this.val = val; this.next = next; }
      }

      // https://leetcode.com/problems/insertion-sort-list/
    // idea is to find right position for every node you pick to place (O(N^2))
    public ListNode insertionSortList(ListNode head) {
        ListNode dummy = new ListNode();
        ListNode prev = dummy;

        while(head!= null){
            ListNode next = head.next;

            // if(prev.val >= head.val) prev = temp; // this saves some iterations ... if you remember this (only set to head for if cond)
            while(prev.next != null && prev.next.val < head.val){
                prev = prev.next;
            }

            head.next = prev.next;
            prev.next = head;

            prev = dummy;
            head = next;

        }
        return dummy.next;
    }
}
