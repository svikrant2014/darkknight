package org.darkknight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TimeBasedKeyStore {
    class  TimeNode{
        int time;
        String val;

        public TimeNode(int time, String str){
            this.time = time;
            this.val  = str;
        }
    }
    HashMap<String, List<TimeNode>> map;
    public TimeBasedKeyStore() {
        map = new HashMap<>();
    }

    public void set(String key, String value, int timestamp) {
        map.putIfAbsent(key, new ArrayList<>());
        map.get(key).add(new TimeNode(timestamp, value));
    }

    public String get(String key, int timestamp) {
        if(!map.containsKey(key)) return "";
        String val = bs(map.get(key), timestamp);
        return val;
    }

    private String bs(List<TimeNode> list, int time){
        int lo = 0;
        int hi = list.size()-1;

        while(lo < hi){
            int mid = lo + (hi-lo)/2;
            TimeNode node = list.get(mid);
            if(node.time == time) return node.val;
            else if(node.time > time){
                hi = mid-1;
            }
            else{
                if(list.get(mid+1).time > time) return list.get(mid).val;
                lo = mid+1;
            }
        }
        return list.get(lo).time <= time ? list.get(lo).val : "";
    }
}
