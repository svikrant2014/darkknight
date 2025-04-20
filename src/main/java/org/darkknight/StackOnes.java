package org.darkknight;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Stack;

public class StackOnes {

    public static void main(String[] args) {
        StackOnes stackOnes = new StackOnes();
        stackOnes.dailyTemperatures(new int[] { 73,74,75,71,69,72,76,73 });

        int maxArea = largestRectangleHistogram(new int[] { 1, 1 });
        int[] ans  = nextGreaterElementII(new int[] { 6, 4, 3, 5});
        // int[] ans = nextGreaterElementToRight(new int[] {1,3,0,0,1,2,4});
        // int[] ans = nextGreaterToLeft(new int[] {1,3,0,0,1,2,4});
        System.out.println(ans);
    }

    public String decodeString(String s){
        Stack<Integer> intStack = new Stack<>();
        Stack<StringBuilder> stack = new Stack<>();

        StringBuilder curr = new StringBuilder();
        int k = 0;
        for(char ch : s.toCharArray()){
            if(Character.isDigit(ch)){
                k = k*10 + ch - '0';
            } else if (ch == '[') {
                intStack.push(k);
                stack.push(curr);
                curr = new StringBuilder();
                k=0;
            }
            else if(ch == ']'){
                StringBuilder tmp = curr;
                curr = stack.pop();
                for(int i = intStack.pop(); i >0 ;i--){
                    curr.append(tmp);
                }
            }
            else {
                curr.append(ch);
            }
        }

        return curr.toString();
    }

    // https://leetcode.com/problems/backspace-string-compare/
    public boolean backspaceCompare(String s, String t) {
        if(s.length() == 0 && t.length() == 0) return true;

        Stack<Character> stack1 = new Stack<>();
        Stack<Character> stack2 = new Stack<>();

        for(char c: s.toCharArray()){
            if(c == '#'){
                if(!stack1.isEmpty()){
                    stack1.pop();
                }
            }
            else{
                stack1.push(c);
            }
        }

        for(char c: t.toCharArray()){
            if(c == '#'){
                if(!stack2.isEmpty()){
                    stack2.pop();
                }
            }
            else{
                stack2.push(c);
            }
        }
        return stack1.equals(stack2);

    }

    public static int reversePolishNotation(String[] tokens) {
        int a, b;
        Stack<Integer> stack = new Stack<>();

        for(String token : tokens){
            if(token.equals("+")){
                a = stack.pop();
                b = stack.pop();
                stack.push(a + b);
            }
            else if(token.equals("-")){
                a = stack.pop();
                b = stack.pop();
                stack.push(b-a);
            }
            else if(token.equals("*")){
                a = stack.pop();
                b = stack.pop();
                stack.push(a * b);
            }
            else if(token.equals("/")){
                a = stack.pop();
                b = stack.pop();
                stack.push(b/a);
            }
            else{
                stack.push(Integer.parseInt(token));
            }
        }

        return stack.pop();
    }

    // https://leetcode.com/problems/daily-temperatures/
    public static int[] dailyTemperatures(int[] temperatures) {
        int[] ans = new int[temperatures.length];
        Stack<Integer> stack = new Stack<>();
        int n = temperatures.length;
        for(int i = n-1; i>=0; i--){
            while(!stack.isEmpty() && temperatures[i]  >= temperatures[stack.peek()]){
                stack.pop();
            }

            if(!stack.isEmpty()){
                ans[i] = stack.peek() - i;
            }
            stack.push(i);
        }

        return ans;
    }

    // https://leetcode.com/problems/largest-rectangle-in-histogram/description/
    public static int largestRectangleHistogram(int[] heights){
        if(heights.length == 0) return 0;
        int[] nextSmallestLeft =  new int[heights.length];
        int[] nextSmallestRight = new int[heights.length];

        Stack<Integer> stack = new Stack<>();
        for(int i = 0; i< heights.length; i++){
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]){
                stack.pop();
            }
            nextSmallestLeft[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        stack = new Stack<>();
        for(int i = heights.length-1; i>=0; i--){
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]){
                stack.pop();
            }
            nextSmallestRight[i] = stack.isEmpty() ? heights.length : stack.peek();
            stack.push(i);
        }

        int maxAreaSoFar = Integer.MIN_VALUE;
        for(int i = 0; i< nextSmallestRight.length; i++){
            int area = (nextSmallestRight[i] - nextSmallestLeft[i]-1) * heights[i];
            maxAreaSoFar = Math.max(maxAreaSoFar, area);
        }

        return maxAreaSoFar;
    }



    public static int[] nextGreaterElementII(int[] nums){
        int[] ans = new int[nums.length];
        Stack<Integer> stack = new Stack<>();
        int n = nums.length;

        for(int i = (n*2-1); i>=0; --i){
            while(!stack.isEmpty() && stack.peek() <= nums[i % n]){
                stack.pop();
            }
            if (i<n){
                ans[i/2] = stack.isEmpty() ? -1 : stack.peek();
            }
            stack.push(nums[i%n]);
        }

        return ans;
    }

    // https://leetcode.com/problems/next-greater-element-i/
    public static int[] nextGreaterElement(int[] nums1, int[] nums2){
        HashMap<Integer, Integer> map = new HashMap<>();
        Stack<Integer> stack = new Stack<>();

        for(int i =0; i< nums2.length; i++){
            while(!stack.isEmpty() && nums2[i] > stack.peek()){
                map.put(stack.pop(), nums2[i]);
            }
            stack.push(nums2[i]);
        }

        while(!stack.isEmpty()){
            map.put(stack.pop(), -1);
        }
        int[] res = new int[nums1.length];
        for(int i = 0; i< nums1.length; i++){
            res[i] = map.get(nums1[i]);
        }

        return res;
    }

    public static int[] nextGreaterToLeft(int[] arr){
        int[] ans = new int[arr.length];
        Stack<Integer> stack = new Stack<>();

        for(int i = 0; i< arr.length; i++){
            if(stack.isEmpty()) ans[i] = -1;
            else if(stack.peek() > arr[i]) ans[i] = stack.peek();
            else{
                while(!stack.isEmpty() && stack.peek() <= arr[i]){
                    stack.pop();
                }
                ans[i] = stack.isEmpty() ? -1 : stack.peek();
            }
            stack.push(arr[i]);
        }
        return ans;
    }

    public static int[] nextGreaterElementToRight(int[] arr){
        int ans[] = new int[arr.length];
        Stack<Integer> stack = new Stack<>();

        for(int i = arr.length-1; i>=0; i--){
            if(stack.isEmpty()) ans[i] = -1;
            else{
                if(stack.peek() > arr[i]) ans[i] = stack.peek();
                else{
                    while(!stack.isEmpty() && stack.peek() <= arr[i]) {
                        stack.pop();
                    }
                    if(stack.isEmpty()) ans[i] = -1;
                    else {
                        ans[i] = stack.peek();
                    }
                }
            }
            stack.push(arr[i]);
        }
        return ans;
    }

    private class Pair{
        int first;
        int sec;
        Pair(int first, int sec){
            this.first = first;
            this.sec = sec;
        }
    }
    // https://leetcode.com/problems/sum-of-subarray-minimums
    public int sumSubarrayMins(int[] arr) {
        Stack<Pair> stack = new Stack<>();
        int[] left = new int[arr.length];
        int[] right = new int[arr.length];

        for(int i = 0; i<arr.length; i++){
            int cnt =1;
            while(!stack.isEmpty() && stack.peek().first > arr[i]){
                cnt+= stack.pop().sec;
            }
            stack.push(new Pair(arr[i], cnt));
            left[i] = cnt;
        }

        stack = new Stack<>();
        for(int i = arr.length-1; i>=0; i--){
            int cnt =1;
            while(!stack.isEmpty() && stack.peek().first >= arr[i]){
                cnt+= stack.pop().sec;
            }
            stack.push(new Pair(arr[i], cnt));
            right[i] = cnt;
        }

        int ans = 0, mod = 1000000007;
        // be careful here, just one test case may fail
        for(int i =0; i<arr.length; i++){
            ans = (int) ((ans + (long)arr[i] * left[i] * right[i])%mod);
        }
        return ans;
    }

    // https://leetcode.com/problems/valid-parentheses
    // check out below without map ... mind blown
    public static boolean validateParenthesis(String str){
        if(str.length() == 0) return true;
        Map<Character, Character> map = new HashMap<>();
        map.put('{', '}');
        map.put('[', ']');
        map.put('(', ')');

        Stack<Character> stack = new Stack<>();
        for (Character c : str.toCharArray()) {
            if(map.containsKey(c)){
                stack.push(map.get(c));
            }
            else{
                if(stack.isEmpty()) return false;
                if(stack.pop() != c){
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    // // https://leetcode.com/problems/valid-parentheses
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<Character>();
        for (char c : s.toCharArray()) {
            if (c == '(')
                stack.push(')');
            else if (c == '{')
                stack.push('}');
            else if (c == '[')
                stack.push(']');
            else if (stack.isEmpty() || stack.pop() != c)
                return false;
        }
        return stack.isEmpty();
    }

    // https://leetcode.com/problems/longest-valid-parentheses/
    public int longestValidParentheses(String str){
        if(str.length() == 0) return 0;

        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int max = 0;

        for (int i = 0; i < str.length(); i++) {
            int top = stack.peek();
            if((top != -1) && str.charAt(top) == '(' && str.charAt(i) == ')'){
                stack.pop();
                int newTop = stack.peek();
                max = Math.max(max, i-newTop);
            }
            else{
                stack.push(i);
            }
        }

        return max;
    }

    // 2 pass alt of above, Without extra space
    public int longestValidParenthesesAlt(String s) {
        if(s.length() == 0) return 0;
        int open = 0, close = 0;

        int maxLen = 0 ;

        for(Character c : s.toCharArray()){
            if(c == '('){
                open++;
            }
            else{
                close++;
            }
            if(open == close){
                maxLen = Math.max(maxLen, 2 * close);
            }else if(close >= open){
                open = close = 0;
            }
        }

        open = close = 0;
        for(int i=s.length()-1; i>=0; i--){
            if(s.charAt(i) == '(') open++;
            else close++;

            if(open  == close){
                maxLen = Math.max(maxLen, 2 * open);
            }else if(open >= close){
                open = close = 0;
            }
        }
        return maxLen;
    }
}
