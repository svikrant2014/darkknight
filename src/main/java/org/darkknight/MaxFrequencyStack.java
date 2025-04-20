package org.darkknight;

import java.util.HashMap;
import java.util.Stack;

public class MaxFrequencyStack {

    static int maxFrequency = 0;

    public static void main(String[] args) {
        push(5);
        push(7);
        push(5);
        push(7);
        push(4);
        push(5);
        push(5);

        pop();
        pop();
        pop();
        pop();
        pop();
        pop();
    }

    static HashMap<Integer, Integer> freq = new HashMap<>();
    static HashMap<Integer, Stack<Integer>> map = new HashMap<>();

    // idea is that we will be using two HashMaps to maintain freq count for certain element
    // and another one against that freq, maintain the stack for all elements having that same freq
    // once you try to pop out, you get the top from second map, just adjust the maxFreq val
    public static void push(int x){
        int f = freq.getOrDefault(x, 0) +1; // add 1 to the current freq
        freq.put(x, f);

        maxFrequency = Math.max(maxFrequency, f);
        if(!map.containsKey(f)){
            map.put(f, new Stack<>());
        }
        map.get(f).add(x);
    }

    public static int pop(){
        int x = map.get(maxFrequency).pop(); // gives the top of stack at that frequency
        freq.put(x, maxFrequency-1);
        if(map.get(maxFrequency).size() == 0){
            maxFrequency--;
        }

        return x;
    }

}
