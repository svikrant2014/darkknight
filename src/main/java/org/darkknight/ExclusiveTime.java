package org.darkknight;

import java.util.*;

public class ExclusiveTime {

    // https://leetcode.com/problems/exclusive-time-of-functions
    public int[] exclusiveTime(int n, List<String> logs) {
        Stack<Log> stack = new Stack<>();
        int[] res = new int[n];

        for(String content : logs){
            Log log = new Log(content);
            if(log.isStart){
                stack.push(log);
            }else{
                Log top = stack.pop();
                res[top.id] += (log.time - top.time +1);

                if(!stack.isEmpty()){
                    res[stack.peek().id] -= (log.time - top.time +1);
                }
            }
        }
        return res;
    }

    private class Log {
        public int id;
        public boolean isStart;
        public int time;

        public Log(String content) {
            String[] strs = content.split(":");
            id = Integer.valueOf(strs[0]);
            isStart = strs[1].equals("start");
            time = Integer.valueOf(strs[2]);
        }
    }
}
