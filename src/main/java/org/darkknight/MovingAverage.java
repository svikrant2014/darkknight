package org.darkknight;

import java.util.LinkedList;
import java.util.Queue;

// https://leetcode.com/problems/moving-average-from-data-stream/
public class MovingAverage {
    Queue<Integer> queue;
    int cap;
    double sum;
    public MovingAverage(int size) {
        queue = new LinkedList<>();
        cap = size;
        sum = 0;
    }

    public double next(int val) {
        if(queue.size() == cap){
            sum = sum - queue.remove();
        }

        queue.add(val);
        sum += val;
        return sum/queue.size();
    }
}
