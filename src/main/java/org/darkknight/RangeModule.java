package org.darkknight;

import java.util.TreeMap;

public class RangeModule {

    TreeMap<Integer, Integer> map;

    public RangeModule() {
        map = new TreeMap<>();
    }

    public static void main(String[] args) {
        RangeModule rangeTest = new RangeModule();
        rangeTest.addRange(10, 20);
        rangeTest.addRange(18, 22);
        rangeTest.removeRange(14, 16);
        rangeTest.queryRange(10, 14);
        rangeTest.queryRange(13, 15);
        rangeTest.queryRange(16, 17);
    }

    public void addRange(int left, int right) {
        Integer start = map.floorKey(left);
        Integer end = map.floorKey(right);

        if(start != null && map.get(start) >= left){
            left = start;
        }

        if(end != null && map.get(end) > right){
            right = map.get(end);
        }

        map.put(left, right);
        map.subMap(left, false, right, true).clear();
    }

    public boolean queryRange(int left, int right) {
        Integer start = map.floorKey(left);

        if(start == null) return false;

        return map.get(start) >= right;
    }

    public void removeRange(int left, int right) {
        Integer start = map.floorKey(left);
        Integer end = map.floorKey(right);

        if(end != null && map.get(end) > right){
            map.put(right, map.get(end));
        }

        if(start != null && map.get(start) > left){
            map.put(start, left);
        }

        map.subMap(left, true, right, false).clear();
    }
}
