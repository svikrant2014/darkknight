package org.darkknight;

import java.util.*;
import java.util.concurrent.locks.*;

// https://leetcode.com/problems/logger-rate-limiter/description/
public class Logger {

    Map<String, Integer> oldMap;
    Map<String, Integer> newMap;
    int oldestTimestamp;
    Lock lock;

    public Logger() {
        lock = new ReentrantLock();
        oldMap = new HashMap<>();
        newMap = new HashMap<>();
        oldestTimestamp = 0;
    }

    // idea is to keep refreshing memory which is older than oldMap + 10 seconds
    public boolean shouldPrintMessage(int timestamp, String message) {
        if(timestamp >= oldestTimestamp + 10){
            oldMap = new HashMap<>(newMap);
            newMap.clear();
            oldestTimestamp = timestamp;
        }

        if(newMap.containsKey(message)) return false;

        if(oldMap.containsKey(message)){
            int time = oldMap.get(message);
            if(time +10 > timestamp) return false;
        }

        newMap.put(message, timestamp);
        return true;
    }

    HashMap<String, Integer> msgMap = new HashMap<>();
    // Thread-issue considered
    public boolean shouldPrintMessageLocking(int timestamp, String message) {
        Integer ts = msgMap.get(message);
        if (ts == null || timestamp - ts >= 10) {
            synchronized (lock) {
                Integer ts2 = msgMap.get(message);
                if (ts == null || timestamp - ts2 >= 10) {
                    msgMap.put(message, timestamp);
                    return true;
                }
            }
        }
        return false;
    }

    class Log{
        int time;
        String msg;

        public Log(int t, String s){
            this.time = t;
            this.msg = s;
        }
    }

    /** Initialize your data structure here. */
    Queue<Log> queue;
    Set<String> set;
    public void Logger1() {
        queue = new LinkedList<>();
        set = new HashSet<>();
    }

    // why this feels betters
    /** Returns true if the message should be printed in the given timestamp, otherwise returns false.
     If this method returns false, the message will not be printed.
     The timestamp is in seconds granularity. */
    public boolean shouldPrintMessageAlternate(int timestamp, String message) {
        while(!queue.isEmpty() && (timestamp - queue.peek().time) >= 10){
            set.remove(queue.poll().msg);
        }

        if(set.contains(message)) return false;
        queue.offer(new Log(timestamp, message));
        set.add(message);
        return true;
    }
}
