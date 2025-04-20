package org.darkknight;

import java.util.PriorityQueue;

public class MedianFinder {

    public static void main(String[] args) {

    }

    PriorityQueue<Integer> minHeap;
    PriorityQueue<Integer> maxHeap;
    boolean isOdd;

    public MedianFinder() {
        minHeap = new PriorityQueue<>();
        maxHeap = new PriorityQueue<>((a,b) -> b-a);
        isOdd = false;
    }

    public void addNum(int num) {
        minHeap.offer(num);
        maxHeap.offer(minHeap.poll());

        if(minHeap.size() < maxHeap.size()){
            minHeap.offer(maxHeap.poll());
        }
        isOdd = !isOdd;
    }

    public double findMedian() {
        if(isOdd) return minHeap.peek();
        else{
            return (minHeap.peek() + maxHeap.peek())/2.0;
        }
    }
}
