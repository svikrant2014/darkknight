package org.darkknight;

import java.util.Deque;
import java.util.LinkedList;

public class HitCounter {

    class TimeNode{
        int time;
        int val;

        TimeNode(int time, int val){
            this.time = time;
            this.val = val;
        }
    }

    Deque<TimeNode> queue;
    int ctr;

    public HitCounter() {
        queue = new LinkedList();
        ctr = 0;
    }

    public void hit(int timestamp) {
        if(!queue.isEmpty() && queue.peekLast().time == timestamp){
            int val = queue.removeLast().val;
            queue.offer(new TimeNode(timestamp, val+1));
        }
        else{
            queue.offer(new TimeNode(timestamp, 1));
        }
        ctr++;
    }

    public int getHits(int timestamp) {
        while(!queue.isEmpty()){
            int diff = timestamp - queue.peekFirst().time;
            if(diff >= 300){
                ctr -= queue.removeFirst().val;
            }
            else{
                break;
            }
        }
        return ctr;
    }
}
