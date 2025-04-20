package org.darkknight;

import java.util.*;
import java.util.jar.JarOutputStream;

public class PriorityQBased {
    public static void main(String[] args) {
        char[] tasks = new char[] {'A', 'A', 'A', 'B', 'B', 'B'};
        PriorityQBased pq = new PriorityQBased();
        pq.taskScheduler(tasks, 2);
    }

    // https://leetcode.com/problems/task-scheduler/
    // check for O(n) soln in editorial
    public int taskScheduler(char[] tasks, int n) {
        HashMap<Character, Integer> map = new HashMap<>();
        for(char c : tasks){
            map.put(c, map.getOrDefault(c, 0)+1);
        }

        PriorityQueue<Integer> heap = new PriorityQueue<>((a,b) -> b-a);

        heap.addAll(map.values());
        int cycles = 0;

        while(!heap.isEmpty()){
            List<Integer> list = new ArrayList<>();
            for(int i =0; i< n+1; i++){
                if(!heap.isEmpty()){
                    list.add(heap.remove());
                }
            }
            for(int i : list){
                if(--i > 0){
                    heap.add(i);
                }
            }
            cycles += heap.isEmpty() ? list.size() : n+1;
        }
        return cycles;
    }

    // https://leetcode.com/problems/task-scheduler-ii/
    public long taskSchedulerII(int[] tasks, int space) {
        Map<Integer, Long> nextDayMap = new HashMap<>();

        long days = 0;
        for(int task : tasks){
            days = Math.max(nextDayMap.getOrDefault(task, 0L), days+1);
            nextDayMap.put(task, days+space+1);
        }
        return days;
    }

    // https://leetcode.com/problems/k-closest-points-to-origin/
    public int[][] kClosestToOrigin(int[][] points, int k) {
        PriorityQueue<int[]> queue = new PriorityQueue<>((p1, p2) -> p2[0] * p2[0] + p2[1] * p2[1] - p1[0] * p1[0] - p1[1] * p1[1]);

        for(int[] point: points){
            queue.add(point);

            if(queue.size() > k){
                queue.remove();
            }
        }

        int[][] res = new int[k][2];
        while(k>0){
            res[--k] = queue.remove();
        }

        return res;
    }

    // quick select version
    // O(N) time & space, worst case - O(n^2)
    // // https://leetcode.com/problems/k-closest-points-to-origin/
    public class Solution {
        public int[][] kClosest(int[][] points, int k) {
            quickSelect(points, 0, points.length - 1, k);
            return Arrays.copyOfRange(points, 0, k);
        }

        private void quickSelect(int[][] points, int left, int right, int k) {
            if (left >= right) return;

            int pivotIndex = partition(points, left, right);
            int count = pivotIndex - left + 1;

            if (count == k) return;
            else if (count < k)
                quickSelect(points, pivotIndex + 1, right, k - count);
            else
                quickSelect(points, left, pivotIndex - 1, k);
        }

        private int partition(int[][] points, int left, int right) {
            int[] pivot = points[right];
            int pivotDist = dist(pivot);
            int i = left;

            for (int j = left; j < right; j++) {
                if (dist(points[j]) <= pivotDist) {
                    swap(points, i, j);
                    i++;
                }
            }
            swap(points, i, right);
            return i;
        }

        private int dist(int[] point) {
            return point[0] * point[0] + point[1] * point[1];
        }

        private void swap(int[][] points, int i, int j) {
            int[] tmp = points[i];
            points[i] = points[j];
            points[j] = tmp;
        }
    }


    // https://leetcode.com/problems/last-stone-weight/
    // but use bucket sort/counting sort (look for alt in LC)
    public int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> heap = new PriorityQueue<>(Comparator.reverseOrder());
        for(int i : stones){
            heap.add(i);
        }

        while(heap.size() > 1){
            int firstStone = heap.remove();
            int secondStone = heap.remove();

            if(firstStone != secondStone) {
                heap.add(firstStone - secondStone);
            }
        }
        return heap.isEmpty() ? 0 : heap.remove();
    }

    PriorityQueue<Integer> queue1;
    int cap;
    // https://leetcode.com/problems/kth-largest-element-in-a-stream/description/
    // easy one, optimal soln.
    public int KthLargestStream(int k, int[] nums) {
         queue1 = new PriorityQueue<>();
         cap = k;
        for(int i : nums){
            add(i);
        }
        return queue1.peek();
     }

    public int add(int val) {
        queue1.add(val);
        if(queue1.size() > cap){
            queue1.poll();
        }
        return queue1.peek();
    }

    // use a wait queue to delay for K
    // https://leetcode.com/problems/rearrange-string-k-distance-apart/
    public String rearrangeString(String s, int k) {
        HashMap<Character, Integer> map = new HashMap<>();
        for(char c : s.toCharArray()){
            map.put(c, map.getOrDefault(c, 0)+1);
        }

        PriorityQueue<Character> queue = new PriorityQueue<>((a,b) -> map.get(b) - map.get(a));
        queue.addAll(map.keySet());

        Queue<Character> waitQ = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        while(!queue.isEmpty()){
            char c = queue.remove();
            sb.append(c);
            map.put(c, map.get(c)-1);
            waitQ.offer(c);

            if(waitQ.size() < k) continue;

            char curr = waitQ.remove();
            if(map.get(curr) > 0){
                queue.offer(curr);
            }
        }

        String res = sb.toString().length() == s.length() ? sb.toString() : "";
        return res;
    }
}
