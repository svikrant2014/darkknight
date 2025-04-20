package org.darkknight;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

// this one comes with follow up like handle dups
// https://leetcode.com/problems/insert-delete-getrandom-o1/discuss/85401/Java-solution-using-a-HashMap-and-an-ArrayList-along-with-a-follow-up.-(131-ms)
// https://leetcode.com/problems/insert-delete-getrandom-o1/
public class RandomizedSet {
    ArrayList<Integer> nums;
    HashMap<Integer, Integer> map;

    public RandomizedSet(){
        nums = new ArrayList<>();
        map = new HashMap<>();
    }

    public boolean insert(int val){
        if (map.containsKey(val)){
            return false;
        }
        else{
            map.put(val, nums.size());
            nums.add(val);
            return true;
        }
    }

    public boolean remove(int val){
        if(!map.containsKey(val)) return false;
        int loc = map.get(val);

        if(loc < nums.size() -1){ // // not the last one than swap the last one with this val
            int lastOne = nums.get(nums.size()-1);
            nums.set(loc, lastOne);
            map.put(lastOne, loc);
        }

        map.remove(val);
        nums.remove(nums.size()-1);
        return true;
    }

    public int getRandom(){
        Random rand = new Random();
        return nums.get(rand.nextInt(nums.size()));
    }
}
