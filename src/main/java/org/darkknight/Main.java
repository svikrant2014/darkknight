package org.darkknight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Main test = new Main();
        for(int[] row : test.memo){
            Arrays.fill(row, -1);
        }
        var ans = test.maxVal(new int[]{1,3,4,5,7}, new int[]{1,4,5,7, 6}, 7, 4);
    }

    int[][] memo = new int[101][1001];
    public int maxVal(int[] ws, int[] vals, int target, int i){
        if(i < 0 || target == 0) return 0;

        if(memo[target][i] != -1) return memo[target][i];
        if(ws[i] > target) return memo[target][i] =  maxVal(ws, vals, target, i-1);
        else{
            return memo[target][i] = Math.max(maxVal(ws, vals, target, i-1), vals[i] + maxVal(ws, vals, target-ws[i], i-1));
        }
    }
    public int lengthOfLongestSubstringTwoDistinct(String s){
        HashMap<Character, Integer> map = new HashMap<>();
        int maxLen = Integer.MIN_VALUE;
        int i=0, j=0;
        int len = s.length();

        while(j<len){
            char curr = s.charAt(j);
            map.put(curr, map.getOrDefault(curr, 0)+1);
            if(map.size() > 2){
                while(map.size() > 2){
                    char ch = s.charAt(i);
                    map.put(ch, map.get(ch)-1);
                    if(map.get(ch) == 0) map.remove(ch);
                }
            }
            maxLen = Math.max(maxLen, j-i+1);
            j++;
        }
        return maxLen;
    }
}