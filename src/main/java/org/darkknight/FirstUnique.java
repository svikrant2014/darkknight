package org.darkknight;
import java.util.*;

// https://leetcode.com/problems/first-unique-number/
public class FirstUnique {

    HashMap<Integer, Integer> map;
    Queue<Integer> queue;

    public FirstUnique(int[] nums) {
        map = new HashMap<>();
        queue = new LinkedList<>();

        for(int i: nums){
            add(i);
        }
    }

    public int showFirstUnique() {
        while(!queue.isEmpty() && map.get(queue.peek()) > 1){
            queue.poll();
        }

        return queue.isEmpty() ? -1 : queue.peek();
    }

    public void add(int value) {
        map.put(value, map.getOrDefault(value, 0)+1);
        queue.offer(value);
    }
}