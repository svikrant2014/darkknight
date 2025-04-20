package org.darkknight;

import java.util.ArrayList;
import java.util.List;

public class BackTrack {

    public static void main(String[] args) {
        BackTrack backTrack = new BackTrack();
        backTrack.generateParenthesis(3);
    }

    // https://leetcode.com/problems/generate-parentheses/
    // So you make a decision by adding a paren. You could say that the "backtracking" happens when "un-adding" the paren,
    // for example, if you use a stack instead of a string and pop the stack after the recursive call.
    // But in this solution, the "undoing" happens by not actually adding the paren to the string s.
    // O((4^n)/!n)
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        backTrack(res, n, 0, 0, "");
        return res;
    }

    private static void backTrack(List<String> res, int max, int open, int closed, String soFar){
        if(soFar.length() == max * 2){
            res.add(soFar);
            return;
        }

        if(open < max){
            backTrack(res, max, open+1, closed, soFar + "(");
        }
        if(closed < open){
            backTrack(res, max, open, closed+1, soFar + ")");
        }
    }
}

// (
// ((
// (((
// ((())) res-1
// ((())
