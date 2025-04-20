package org.darkknight;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Calendar {

    public static void main(String[] args) {

    }

    TreeMap<Integer, Integer> calendar;

    // https://leetcode.com/problems/my-calendar-i/
    public Calendar() {
        calendar = new TreeMap();
    }

    public boolean book(int start, int end) {
        Integer prev = calendar.floorKey(start);
        Integer next = calendar.ceilingKey(start);

        if ((prev == null || calendar.get(prev) <= start) &&
                (next == null || end <= next)) {
            calendar.put(start, end);
            return true;
        }
        return false;
    }

    private class Event
    {
        int start;
        int end;
        public Event(int start, int end)
        {
            this.start = start;
            this.end = end;
        }
    }
    List<Event> overLapBookings;
    List<Event> bookings;

    public void MyCalendarTwo() {
        overLapBookings = new ArrayList<>();
        bookings = new ArrayList<>();
    }

    // https://leetcode.com/problems/my-calendar-ii/
    public boolean bookII(int start, int end) {

        for(Event e : overLapBookings)
        {
            if(isOverlap(e, start, end))
                return false;
        }

        for(Event e : bookings)
        {
            if(isOverlap(e, start, end))
            {
                overLapBookings.add(getOverlapEvent(e, start, end));
            }
        }
        bookings.add(new Event(start, end));

        return true;
    }

    public boolean isOverlap(Event e, int start, int end)
    {
        return Math.max(e.start, start)<Math.min(e.end, end);
    }

    public Event getOverlapEvent(Event e, int start, int end)
    {
        return new Event(Math.max(e.start, start), Math.min(e.end, end));
    }
}
