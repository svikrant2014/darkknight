package org.darkknight;

import java.util.*;

public class TimeIntervals {

    public static void main(String[] args) {
        int[][] qq = {{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}};

        int[][] qq2 = {{1, 2}, {2, 3}, {3, 4}, {1, 3}};
        int[][] input = {{0, 30}, {5, 10}, {15, 20}};
        int[][] input2 = {{1, 5}, {8, 9}, {8, 9}};


        int[][] qq1 = {{1, 5}};
        TimeIntervals tt = new TimeIntervals();

        tt.test11("aaabbc");
        tt.minMeetingRooms(input2);
        tt.insertInterval(qq1, new int[]{0, 0});
        tt.eraseOverlapIntervals(qq2);

        int[] tmp = new int[]{1, 2, 3};
    }

    private String test11(String str){
        StringBuilder res = new StringBuilder();

        for(int i =0; i<str.length(); i++){
            if(i+1 < str.length() && str.charAt(i) == str.charAt(i+1)){
                res.append(str.charAt(i));
                i++;
            }
            else{
                res.append(str.charAt(i));
            }
        }
        return res.toString();
    }

    public List<Interval> employeeFreeTime2(List<List<Interval>> schedule) {
        List<Interval> res = new ArrayList<>();
        if(schedule.size() == 0) return res;

        List<Interval> timeline = new ArrayList<>();
        for(List<Interval> list : schedule){
            timeline.addAll(list);
        }

        timeline.sort((a, b) -> a.start - b.start);

        int maxEndtime = timeline.get(0).end;

        for(Interval interval : timeline){
            int currStartTime = interval.start;

            if(currStartTime <= maxEndtime){
                maxEndtime = Math.max(maxEndtime, interval.end);
            }
            else{
                res.add(new Interval(maxEndtime, interval.start));
                maxEndtime  = interval.end;
            }
        }
        return res;
    }

    public int minMeetingRoomsX(int[][] intervals) {
        if (intervals.length == 0) return 0;
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        int count = 1;
        int minEndTime = intervals[0][1];

        for (int i = 1; i < intervals.length; i++) {
            int[] curr = intervals[i];

            if (minEndTime > curr[0]) {
                count++;
            }
            minEndTime = Math.min(minEndTime, curr[1]);
        }
        return count;
    }

    //https://leetcode.com/problems/merge-intervals/description/
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a,b) -> a[0] - b[0]);
        List<int[]> res = new ArrayList<>();

        int currEnd = intervals[0][1];
        int start = intervals[0][0];
        for(int i=1; i<intervals.length; i++){
            int[] tmp = intervals[i];

            if(currEnd >= tmp[0]){
                currEnd = Math.max(currEnd, tmp[1]);
            }else{
                res.add(new int[]{start, currEnd});
                start = tmp[0];
                currEnd = tmp[1];
            }
        }
        res.add(new int[]{start, currEnd});
        return res.toArray(new int[res.size()][2]);
    }

    // https://leetcode.com/problems/insert-interval/
    public int[][] insertInterval(int[][] intervals, int[] newInterval) {
        int[] prev = newInterval;

        ArrayList<int[]> res = new ArrayList<>();

        for (int i = 0; i < intervals.length; i++) {
            int[] curr = intervals[i];

            if (prev[1] < curr[0]) {
                res.add(prev);
                prev = curr;
            } else if (curr[1] < prev[0]) {
                res.add(curr);
            } else {
                prev[0] = Math.min(prev[0], curr[0]);
                prev[1] = Math.max(prev[1], curr[1]);
            }
        }
        res.add(prev);

        int[][] merged = new int[res.size()][2];

        int i = 0;
        for (int[] val : res) {
            merged[i++] = val;
        }
        return merged;
    }

    class Interval {
        public int start;
        public int end;

        public Interval() {
        }

        public Interval(int _start, int _end) {
            start = _start;
            end = _end;
        }
    }

    // sort by start time helps, for e.g. [1,4] overlaps everything in between even if it starts after and end early
    // https://leetcode.com/problems/employee-free-time/
    public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
        List<Interval> res = new ArrayList<>();
        if (schedule.size() == 0) return res;

        List<Interval> timeline = new ArrayList<>();
        schedule.forEach(e -> timeline.addAll(e));

        timeline.sort((a, b) -> a.start - b.start);
        int maxEndTime = timeline.get(0).end;

        for (Interval interval : timeline) {
            if (interval.start <= maxEndTime) {
                maxEndTime = Math.max(maxEndTime, interval.end);
            } else {
                res.add(new Interval(maxEndTime, interval.start));
                maxEndTime = interval.end;
            }
        }
        return res;
    }

    // https://leetcode.com/problems/non-overlapping-intervals/
    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> (a[1] - b[1]));
        int count = 0;
        int[] prev = intervals[0];

        for (int i = 1; i < intervals.length; i++) {
            int[] curr = intervals[i];

            if (curr[0] < prev[1]) count++;
            else {
                prev = curr;
            }
        }

        return count;
    }

    // https://leetcode.com/problems/meeting-rooms/
    // determine if a person could attend all meetings.
    public boolean canAttendMeetings(int[][] intervals) {
        if (intervals.length == 0) return true;

        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
        int currentEndTime = intervals[0][1];

        for (int i = 1; i < intervals.length; i++) {
            if (currentEndTime > intervals[i][0]) return false;
            else currentEndTime = intervals[i][1];
        }
        return true;
    }

    // https://leetcode.com/problems/meeting-rooms-ii/
    // minimum number of conference rooms required
    public int minMeetingRooms(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        PriorityQueue<int[]> rooms = new PriorityQueue<>((int[] a, int[] b) -> a[1] - b[1]);
        rooms.offer(intervals[0]);

        for (int i = 1; i < intervals.length; i++) {
            int[] curr = intervals[i];
            int[] earliest = rooms.remove();

            if (curr[0] < earliest[1]) {
                rooms.offer(curr);
            } else {
                earliest[1] = curr[1];
            }
            rooms.offer(earliest);
        }
        return rooms.size();
    }

    // [[0,30],[5,10],[15,20]]
    // min conf rooms req
    public int minMeetingRooms2(int[][] intervals) {
        Arrays.sort(intervals, (a,b) -> a[0] - b[0]);

        PriorityQueue<int[]> queue = new PriorityQueue<>((a,b) -> a[1]-b[1]);

        queue.offer(intervals[0]);

        for(int i=1; i<intervals.length; i++){
            int[] curr = intervals[i];
            int[] earliest = queue.remove();

            if(curr[0] < earliest[1]){
                queue.offer(curr);
            }
            else{
                earliest[1] = curr[1];
            }
            queue.offer(earliest);
        }

        return queue.size();
    }

    // https://leetcode.com/problems/interval-list-intersections
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        if(firstList.length==0 || secondList.length==0) return new int[0][0];
        int i = 0;
        int j = 0;
        int startMax = 0, endMin = 0;
        List<int[]> ans = new ArrayList<>();

        while(i<firstList.length && j<secondList.length){
            startMax = Math.max(firstList[i][0],secondList[j][0]);
            endMin = Math.min(firstList[i][1],secondList[j][1]);

            //you have end greater than start and you already know that this interval is sorrounded with
            // startMin and endMax so this must be the intersection
            if(endMin>=startMax){
                ans.add(new int[]{startMax,endMin});
            }

            //the interval with min end has been covered completely and have no chance to intersect with
            // any other interval so move that list's pointer
            if(endMin == firstList[i][1]) i++;
            if(endMin == secondList[j][1]) j++;
        }

        return ans.toArray(new int[ans.size()][2]);
    }
}
