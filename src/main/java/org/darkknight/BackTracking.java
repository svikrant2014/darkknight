package org.darkknight;

import java.sql.Array;
import java.util.*;

public class BackTracking {
    public static void main(String[] args) {
        BackTracking bc = new BackTracking();
        bc.letterCasePermutation("a1b2");
        bc.permuteBetterApproach(new int[]{1,2,3});
        bc.letterCombinations("23");
        List<List<Integer>> res1 = bc.subsetsII(new int[]{1,2,2});
    }

    // https://leetcode.com/problems/letter-case-permutation/submissions
    public List<String> letterCasePermutation(String S) {
        List<String> result = new ArrayList<>();
        recurse(S.toCharArray(), 0, result);
        return result;
    }
        void recurse(char[] str, int pos, List<String> result) {
            if (pos == str.length) {
                result.add(new String(str));
                return;
            }

            if (Character.isLetter(str[pos])) {
                if (Character.isUpperCase(str[pos])) {
                    str[pos] = Character.toLowerCase(str[pos]);
                    recurse(str, pos + 1, result);
                    str[pos] = Character.toUpperCase(str[pos]);
                }
                else {
                    str[pos] = Character.toUpperCase(str[pos]);
                    recurse(str, pos + 1, result);
                    str[pos] = Character.toLowerCase(str[pos]);
                }
            }
            //This branch explores options that are possible only if the previously performed change (if any) hadn't happened.
            recurse(str, pos + 1,  result);
        }

    List<List<Integer>> res;
    // https://leetcode.com/problems/subsets/
    // O(N×2^N)
    public List<List<Integer>> subsets(int[] nums) {
        res = new ArrayList<>();
        List<Integer> subset = new ArrayList<>();
        getSubsets(nums, 0, subset);
        return res;
    }

    private void getSubsets(int[] nums, int index, List<Integer> subset){
        res.add(new ArrayList<>(subset));

        for(int i = index; i < nums.length; i++){
            subset.add(nums[i]);
            getSubsets(nums, i+1, subset);
            subset.remove(subset.size()-1);
        }
    }

    List<List<Integer>> res2;
    // https://leetcode.com/problems/subsets-ii/
    public List<List<Integer>> subsetsII(int[] nums) {
        res2 = new ArrayList<>();
        List<Integer> subset = new ArrayList<>();
        Arrays.sort(nums);
        getSubsetsII(nums, 0, subset);
        return res2;
    }

    private void getSubsetsII(int[] nums, int index, List<Integer> subset){
        res2.add(new ArrayList<>(subset));

        for(int i = index; i < nums.length; i++){
            if(i > index && nums[i] == nums[i-1]) continue;
            subset.add(nums[i]);
            getSubsetsII(nums, i+1, subset);
            subset.remove(subset.size()-1);
        }
    }

    // https://leetcode.com/problems/combination-sum
    // check for memoization ??
    // O(N^(T/M+1))
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> res = new ArrayList<>();
        dfs(res, candidates, target, new ArrayList<>(), 0);
        return res;
    }

    private void dfs(List<List<Integer>> res, int[] candidates, int target, List<Integer> curr, int idx){
        if(target < 0) return;
        if(target == 0){
            if(!res.contains(curr)) res.add(new ArrayList<>(curr));
        }

        for(int i = idx; i<candidates.length; i++){
            curr.add(candidates[i]);
            int rem = target - candidates[i];
            dfs(res, candidates, rem, curr, i);
            curr.remove(curr.size()-1);
        }
    }

    // https://leetcode.com/problems/combination-sum-ii
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {

        Arrays.sort(candidates);
        List<List<Integer>> res = new ArrayList<>();
        dfs2(res, candidates, target, new ArrayList<>(), 0);

        return res;
    }

    private void dfs2(List<List<Integer>> res, int[] candidates, int target, List<Integer> curr, int idx){
        if(target < 0) return;
        if(target == 0){
            if(!res.contains(curr)) res.add(new ArrayList<>(curr));
        }

        for(int i = idx; i<candidates.length; i++){
            if (i > idx && candidates[i] == candidates[i-1]) continue;

            curr.add(candidates[i]);
            int rem = target - candidates[i];
            dfs2(res, candidates, rem, curr, i+1);
            curr.remove(curr.size()-1);
        }
    }

    List<List<Integer>> ans;
    // https://leetcode.com/problems/combination-sum-iii
    public List<List<Integer>> combinationSum3(int k, int n) {
        ans = new ArrayList<>();
        dfsSum(k, n, 1, new ArrayList<>());
        return ans;
    }

    private void dfsSum(int k, int rem, int start, List<Integer> comb){
        if(comb.size() == k && rem == 0){
            ans.add(new ArrayList<>(comb));
            return;
        }

        for(int i =start; i<=9; i++){
            comb.add(i);
            dfsSum(k, rem-i, i+1, comb);
            comb.remove(comb.size()-1);
        }
    }

    List<List<Integer>> perms;

    // SEEMS BETTER & more intuitive
    // O(n !n)
    public List<List<Integer>> permuteBetterApproach(int[] nums) {
        perms = new ArrayList<>();
        if(nums.length == 0) return perms;
        backtrack(new ArrayList<>(), nums);
        return perms;
    }

    private void backtrack(List<Integer> arr, int[] nums){
        if(arr.size() == nums.length){
            perms.add(new ArrayList<>(arr));
            return;
        }

        for(int num : nums){
            if(!arr.contains(num)){
                arr.add(num);
                backtrack(arr, nums);
                arr.remove(arr.size()-1);
            }
        }
    }

    // https://leetcode.com/problems/permutations-ii/
    public List<List<Integer>> permutationsII(int[] nums) {
        perms = new ArrayList<>();
        if(nums.length == 0) return perms;
        Arrays.sort(nums);
        backtrackII(new ArrayList<>(), nums, new boolean[nums.length]);
        return perms;
    }


    private void backtrackII(List<Integer> arr, int[] nums, boolean[] used){
        if(arr.size() == nums.length){
            perms.add(new ArrayList<>(arr));
            return;
        }

        for(int i = 0; i<nums.length; i++) {
            if(used[i] || i>0 && nums[i] == nums[i-1] && !used[i-1]) continue;
            used[i] = true;
            arr.add(nums[i]);
            backtrackII(arr, nums, used);
            used[i] = false;
            arr.remove(arr.size() - 1);
        }
    }

    // O(n !n)
    // https://leetcode.com/problems/permutations/description/
    public List<List<Integer>> permutations(int[] nums) {
        perms = new ArrayList<>();
        permDFS(nums, 0, nums.length-1);
        return perms;
    }

    private void permDFS(int[] nums, int start, int end){
        if(start == end){
            perms.add(addToList(nums));
        }
        else{
            for(int i = start; i<= end; i++){
                swap(nums, start, i);
                permDFS(nums, start+1, end);
                swap(nums, start, i);
            }
        }

    }

    private void swap(int[] nums, int curr, int next){
        int i = nums[curr];
        nums[curr] = nums[next];
        nums[next] = i;
    }

    private List<Integer> addToList(int[] nums){
        List<Integer> tmp = new ArrayList<>();
        for(int i : nums){
            tmp.add(i);
        }
        return tmp;
    }

    // https://leetcode.com/problems/word-search/description/
    // O(Nx3^L)
    public boolean wordSearch(char[][] board, String word) {
        for(int i = 0; i< board.length; i++){
            for(int j=0; j<board[i].length; j++){
                if(board[i][j] == word.charAt(0) && dfsWordSearch(board, i, j, 0, word)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfsWordSearch(char[][] board, int i, int j, int count, String word){
        if(count == word.length()) return true;

        if(i < 0 || i >= board.length || j < 0 || j > board[i].length || board[i][j] != word.charAt(count)){
            return false;
        }

        char temp = board[i][j];
        board[i][j] = ' ';
        boolean found = dfsWordSearch(board, i+1, j, count+1, word)
                || dfsWordSearch(board, i-1, j, count+1, word)
                || dfsWordSearch(board, i, j+1, count+1, word)
                || dfsWordSearch(board, i, j-1, count+1, word);
        board[i][j] = temp;
        return found;
    }

    // https://leetcode.com/problems/letter-combinations-of-a-phone-number/
    public List<String> letterCombinations(String digits) {
        if (digits.length() == 0) return new ArrayList<>();

        String[] map = new String[]{"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs",
                "tuv", "wxyz"};

        Queue<String> queue = new LinkedList<>();
        queue.offer("");
        while(!(queue.peek().length() == digits.length())){
            String curr = queue.poll();
            String val = map[digits.charAt(curr.length()) - '0'];
            for(char c : val.toCharArray()){
                queue.offer(curr+c);
            }
        }
        return (List) queue;
    }

    List<List<String>> pp;
    // https://leetcode.com/problems/palindrome-partitioning/
    // O(N⋅2^N)
    // can do DP
    public List<List<String>> palindromePartition(String s) {
        pp = new ArrayList<>();
        dfsPP(s, 0, new ArrayList<String>());
        return pp;
    }

    private void dfsPP(String s, int index, List<String> curr){
        if(index >= s.length()){
            pp.add(new ArrayList<>(curr));
        }

        for(int i = index; i< s.length(); i++){
            if(isPalindrome(s, index, i)){
                curr.add(s.substring(index, i+1));
                dfsPP(s, i +1, curr);
                curr.remove(curr.size()-1);
            }
        }
    }

    boolean isPalindrome(String s, int low, int high) {
        while (low < high) {
            if (s.charAt(low++) != s.charAt(high--)) return false;
        }
        return true;
    }

    List<List<Integer>> combs;
    // https://leetcode.com/problems/combinations
    // O(n! / (k−1)!⋅(n−k)!)
    public List<List<Integer>> combinations(int n, int k){
        combs = new ArrayList<>();
        dfsCombs(n, k, 1, new ArrayList<>());
        return combs;
    }

    private void dfsCombs(int n, int k, int index, List<Integer> curr){
        if(curr.size() == k){
            combs.add(new ArrayList<>(curr));
        }

        for(int i = index; i<=n; i++){
            curr.add(i);
            dfsCombs(n, k, i+1, curr);
            curr.remove(curr.size()-1);
        }
    }

    // https://leetcode.com/problems/maximum-length-of-a-concatenated-string-with-unique-characters
    // DP problem
    // but it seems like plain recursion
    // O(2^N)
    public int maxLengthConcatenated(List<String> arr) {
        List<String> res = new ArrayList<>();
        res.add("");
        for (String str : arr) {
            if (!isUnique(str)) continue;
            List<String> resList = new ArrayList<>();
            for (String candidate : res) {
                String temp = candidate + str;
                if (isUnique(temp)) resList.add(temp);
            }
            res.addAll(resList);
        }
        int ans = 0;
        for (String str : res) ans = Math.max(ans, str.length());
        return ans;
    }

    private boolean isUnique(String str) {
        if (str.length() > 26) return false;
        boolean[] used = new  boolean [26];
        char[] arr = str.toCharArray();
        for (char ch : arr) {
            if (used[ch - 'a']){
                return false;
            } else {
                used[ch -'a'] = true;
            }
        }
        return true;
    }

    // https://leetcode.com/problems/expression-add-operators
    // you wanna try all combination 1+2+3, 1*2+3, 1*2*3 so ... N x 4^N
    public List<String> expressionAddOperators(String num, int target) {
        List<String> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        dfs(res, sb, num, 0, target, 0, 0);
        return res;

    }
    public void dfs(List<String> res, StringBuilder sb, String num, int pos, int target, long prev, long multi) {
        if (pos == num.length()) {
            if (target == prev) res.add(sb.toString());
            return;
        }
        for (int i = pos; i < num.length(); i++) {
            if (num.charAt(pos) == '0' && i != pos) break;
            long curr = Long.parseLong(num.substring(pos, i + 1));
            int len = sb.length();
            if (pos == 0) {
                dfs(res, sb.append(curr), num, i + 1, target, curr, curr);
                sb.setLength(len);
            } else {
                dfs(res, sb.append("+").append(curr), num, i + 1, target, prev + curr, curr);
                sb.setLength(len);
                dfs(res, sb.append("-").append(curr), num, i + 1, target, prev - curr, -curr);
                sb.setLength(len);
                dfs(res, sb.append("*").append(curr), num, i + 1, target, prev - multi + multi * curr, multi * curr);
                sb.setLength(len);
            }
        }
    }
}
