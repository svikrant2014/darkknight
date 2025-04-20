package org.darkknight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Memoization {
    public static void main(String[] args) {

    }

    public int longestCommonSubsequence(String str1, String str2){
        for(int[] row : memo){
            Arrays.fill(row, -1);
        }
        return LCS(str1, str2, str1.length()-1, str2.length()-1);
    }

    int[][] memo = new int[1001][1001];
    private int LCS(String s1, String s2, int m, int n){
        if(m < 0 || n <0) return 0;
        if(memo[m][n] != -1) return memo[m][n];

        if(s1.charAt(m) == s2.charAt(n)){
            return memo[m][n] = 1 + LCS(s1, s2, m-1, n-1);
        }
        else{
            return memo[m][n] = Math.max(LCS(s1, s2, m-1, n), LCS(s1, s2, m, n-1));
        }
    }

    HashMap<String, Boolean> map;
    // check if a word can be constructed from a wordDict
    // O(n x m^2) time
    // O(m^2) space
    // https://leetcode.com/problems/word-break/
    public boolean wordBreak(String s, List<String> words) {
        map = new HashMap<>();
        return canConstructMemoized(s, words);
    }

    private boolean canConstructMemoized(String target, List<String> words){
        if(map.containsKey(target)) return map.get(target);
        if (target.length() == 0) return true;

        for (String word: words) {
            if(target.indexOf(word) == 0){
                String sub = target.substring(word.length());
                if((canConstructMemoized(sub, words) == true)){
                    map.put(sub, true);
                    return true;
                }
            }
        }
        map.put(target, false);
        return false;
    }

    // word-break II problem
    // https://leetcode.com/problems/word-break-ii/
    // O(2^n)
    public List<String> wordBreakII(String s, List<String> wordDict) {
        HashMap<String, List<String>> map = new HashMap<>();
        return constructAllMemoized(s, wordDict, map);
    }

    private List<String> constructAllMemoized(String s, List<String> wordDict, HashMap<String, List<String>> wordBreakMap){
        List<String> res = new ArrayList<>();

        if(wordBreakMap.containsKey(s)) return wordBreakMap.get(s);
        if (s.length() == 0) return res;

        if(wordDict.contains(s)){
            res.add(s);
        }

        for (String word: wordDict) {
            if(s.startsWith(word)){
                List<String> subList = constructAllMemoized(s.substring(word.length()), wordDict, wordBreakMap);
                for(String sub : subList){
                    res.add(word +  (sub.isEmpty() ? "" : " ") + sub);
                }
            }
        }
        wordBreakMap.put(s, res);
        return res;
    }
}
