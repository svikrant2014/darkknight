package org.darkknight;

import java.util.*;

public class SlidingWindow {

    public static void main(String[] args) {
        SlidingWindow ss = new SlidingWindow();
        ss.minWindow("ABAACBAB", "ABC");
        ss.maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3);
        ss.longestSubarray(new int[]{0,1,1,1,0,1,1,0,1});
        // Input: s1 = "ab", s2 = "eidbaooo"
        ss.characterReplacement("AABABBA", 1);
        ss.maxVowels("abciiidef", 3);
        ss.longestOnes(new int[]{0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1}, 3);
        ss.totalFruit(new int[]{3, 3, 3, 1, 2, 1, 1, 2, 3, 3, 4});
        ss.minSizeSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3});
        int val = ss.contiguousArray(new int[]{0, 0, 1, 0, 1, 1, 1});
        String res = ss.longestPalindromeSubstring("tabacabf");
        ss.subarraySum(new int[]{1, 2, 3}, 3);
        ss.lengthOfLongestSubstring("abcabbc");
    }

    // nums = [1,2,3], k = 5
    // https://leetcode.com/problems/subarray-sum-equals-k/
    // don't try the sliding win one coz that can't handle negative  cases
    // they can just ask to return T/F, can optimize this to just use HashSet instead coz don't need count as well
    public int subarraySum(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        int sum = 0;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            int val = sum - k;

            if (map.containsKey(val)) {
                count += map.get(val);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }

    // #meta
    public boolean subarraySumOnlyPositives(int[] nums, int k) {
        if(nums.length == 0) return false;

        int sum = 0;
        int left =0, right = 0;
        while(right < nums.length){
            sum += nums[right];
            while(sum > k){
                sum -= nums[left];
                left++;
            }

            if(sum == k) return true;
            right++;
        }
        return false;
    }

    // https://leetcode.com/problems/maximum-size-subarray-sum-equals-k/
    // idea is to find the sum first, and then check prefix sum as remainder if that was there ... and count maxLength
    public int maxSubArrayLen(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int sum = 0;
        int maxlen = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];

            if (sum == k) {
                maxlen = i + 1;
            }

            int val = sum - k;
            if (map.containsKey(val)) {
                maxlen = Math.max(maxlen, i - map.get(val));
            }

            if (!map.containsKey(sum)) {
                map.put(sum, i);
            }
        }
        return maxlen;
    }

    // https://leetcode.com/problems/longest-substring-without-repeating-characters/description/
    // f1/f2 style went out of the window
    public int lengthOfLongestSubstring(String s) {
        if(s.length() == 0) return 0;
        HashMap<Character, Integer> map = new HashMap<>();

        int maxLen = Integer.MIN_VALUE;
        int i=0, j =0;
        while(j < s.length()){
            char ch = s.charAt(j);
            map.put(ch, map.getOrDefault(ch, 0)+1);

            if(map.get(ch) > 1){
                while(i < j){
                    char left = s.charAt(i++);
                    map.put(left, map.get(left)-1);
                    if(map.get(left) == 1){
                        break;
                    }
                }
            }
            maxLen = Math.max(maxLen, j-i+1);
            j++;
        }
        return maxLen;
    }

    // https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if(s == null || s.length() == 0) return 0;
        HashMap<Character, Integer> map = new HashMap<>();
        int maxLen = Integer.MIN_VALUE;
        int i=0, j=0;
        int len = s.length();

        while(j<len){
            char curr = s.charAt(j);
            map.put(curr, map.getOrDefault(curr, 0)+1);
            if(map.size() > k){
                while(map.size() > k){
                    char ch = s.charAt(i++);
                    map.put(ch, map.get(ch)-1);
                    if(map.get(ch) == 0) map.remove(ch);
                }
            }
            maxLen = Math.max(maxLen, j-i+1);
            j++;
        }
        return maxLen;
    }

    // https://leetcode.com/problems/longest-substring-with-at-most-two-distinct-characters/
    // this was f1/f2, now see the smaller version using sliding-window template
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        if(s == null || s.length() == 0) return 0;
        HashMap<Character, Integer> map = new HashMap<>();
        int maxLen = Integer.MIN_VALUE;
        int i=0, j=0;
        int len = s.length();

        while(j<len){
            char curr = s.charAt(j);
            map.put(curr, map.getOrDefault(curr, 0)+1);
            if(map.size() > 2){
                while(map.size() > 2){
                    char ch = s.charAt(i++);
                    map.put(ch, map.get(ch)-1);
                    if(map.get(ch) == 0) map.remove(ch);
                }
            }
            maxLen = Math.max(maxLen, j-i+1);
            j++;
        }
        return maxLen;
    }

    // 0, 0, 1, 0, 1,1,1
    // https://leetcode.com/problems/contiguous-array
    public int contiguousArray(int[] nums) {
        if (nums.length == 0) return 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int sum = 0;
        int maxLen = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                sum += -1;
            } else {
                sum += 1;
            }

            if (map.containsKey(sum)) {
                int idx = map.get(sum);
                maxLen = Math.max(maxLen, i - idx);
            } else {
                map.put(sum, i);
            }
        }
        return maxLen;
    }

    // https://leetcode.com/problems/longest-palindromic-substring/
    public String longestPalindromeSubstring(String str) {
        if (str == null || str.length() < 1) return "";
        int start = 0;
        int end = 0;
        int length = str.length();

        for (int i = 0; i < length; i++) {
            int len1 = expandAroundCenter(str, i, i);
            int len2 = expandAroundCenter(str, i, i + 1);
            int len = Math.max(len1, len2);

            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }

        return str.substring(start, end + 1);
    }

    private static int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }

        return right - left - 1;
    }

    // same as above, this is all count
    // https://leetcode.com/problems/palindromic-substrings
    // O(n^2)
    public int countPalindromicSubstrings(String s) {
        int res = 0;

        for(int i=0; i<s.length(); i++){
            int l = i;
            int r = i;

            res += countPal(s, l, r);

            l = i;
            r = i+1;
            res += countPal(s, l, r);
        }
        return res;
    }

    private int countPal(String s, int l, int r){
        int res = 0;
        while(l >= 0 && r<s.length() && s.charAt(l) == s.charAt(r)){
            res ++;
            l -=1;
            r += 1;
        }
        return res;
    }

    // https://leetcode.com/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/?envType=list&envId=xixy4dq7
    public int longestSubarray(int[] nums, int limit) {
        Deque<Integer> maxd = new ArrayDeque<>();
        Deque<Integer> mind = new ArrayDeque<>();

        int res = 1;
        int left = 0;

        for (int i = 0; i < nums.length; i++) {
            while (!maxd.isEmpty() && maxd.peekLast() < nums[i]) {
                maxd.removeLast();
            }
            maxd.addLast(nums[i]);

            while (!mind.isEmpty() && mind.peekLast() > nums[i]) {
                mind.removeLast();
            }
            mind.addLast(nums[i]);

            while (maxd.peekFirst() - mind.peekFirst() > limit) {
                if (maxd.peekFirst() == nums[left]) maxd.pollFirst();
                if (mind.peekFirst() == nums[left]) mind.pollFirst();
                ++left;
            }

            res = Math.max(res, i - left + 1);
        }
        return res;
    }

    // https://leetcode.com/problems/maximum-average-subarray-i/
    public double findMaxAverage(int[] nums, int k) {
        long sum = 0;

        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }
        long maxSum = sum;
        int i = 0, j = k;
        while (j < nums.length) {
            sum = sum + nums[j++] - nums[i++];
            maxSum = Math.max(maxSum, sum);
        }

        return (double) maxSum / k;
    }

    // {2,3,1,2,4,3}
    // https://leetcode.com/problems/minimum-size-subarray-sum
    public int minSizeSubArrayLen(int target, int[] nums) {
        int minLen = Integer.MAX_VALUE;
        int i = 0, j = 0;
        int sum = 0;

        while (j < nums.length) {
            sum += nums[j++];

            if (sum >= target) {
                while (sum >= target) {
                    sum -= nums[i++];
                }
                minLen = Math.min(minLen, j - i + 1);
            }
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }


    // Input: fruits = [3,3,3,1,2,1,1,2,3,3,4]
    // Output: 4
    // https://leetcode.com/problems/fruit-into-baskets/
    // have to keep the frequency map as well coz when we start removing from left, we don't stop unless only 2 are left
    public int totalFruit(int[] fruits) {
        HashMap<Integer, Integer> map = new HashMap<>();

        int i = 0, j = 0;
        int maxLen = Integer.MIN_VALUE;
        while (j < fruits.length) {
            if (map.containsKey(fruits[j])) {
                map.put(fruits[j], map.get(fruits[j]) + 1);
                maxLen = Math.max(maxLen, j - i + 1);
            } else {
                map.put(fruits[j], 1);
                while (map.size() > 2) {
                    int left = fruits[i++];
                    map.put(left, map.get(left) - 1);
                    if (map.get(left) == 0) map.remove(left);
                }
                maxLen = Math.max(maxLen, j - i + 1);
            }
            j++;
        }
        return maxLen;
    }

    // Input: s1 = "ab", s2 = "eidbaooo"
    // https://leetcode.com/problems/permutation-in-string
    public boolean permutationInAString(String s1, String s2) {
        if (s2.length() < s1.length()) return false;
        HashMap<Character, Integer> map1 = new HashMap<>();
        HashMap<Character, Integer> map2 = new HashMap<>();

        for (char c : s1.toCharArray()) {
            map1.put(c, map1.getOrDefault(c, 0) + 1);
        }

        for (int i = 0; i < s1.length(); i++) {
            map2.put(s2.charAt(i), map2.getOrDefault(s2.charAt(i), 0) + 1);
        }

        if (map1.equals(map2)) return true;
        int i = 0, j = s1.length();

        while (j < s2.length()) {
            char curr = s2.charAt(i++);
            map2.put(curr, map2.get(curr) - 1);
            if (map2.get(curr) == 0) map2.remove(curr);

            char right = s2.charAt(j++);
            map2.put(right, map2.getOrDefault(right, 0) + 1);

            if (map1.equals(map2)) return true;
        }
        return false;
    }

    // nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2
    //https://leetcode.com/problems/max-consecutive-ones-iii/
    // sliding window
    public int longestOnes(int[] nums, int k) {
        if (nums.length == 0) return 0;
        int maxLen = Integer.MIN_VALUE;

        int count = 0;
        int i = 0, j = 0;
        while (j < nums.length) {
            if (nums[j] == 0) count++;
            while (count > k) {
                if (nums[i] == 0) --count;
                i++;
            }
            maxLen = Math.max(maxLen, j - i + 1);
            j++;
        }

        return maxLen;
    }

    // https://leetcode.com/problems/max-consecutive-ones-ii/
    // if you can flip max one 0
    public int findMaxConsecutiveOnesII(int[] nums) {
        if (nums.length == 0) return 0;
        int maxLen = Integer.MIN_VALUE;

        int count = 0;
        int i = 0, j = 0;
        while (j < nums.length) {
            if (nums[j] == 0) count++;
            while (count > 1) {
                if (nums[i] == 0) --count;
                i++;
            }
            maxLen = Math.max(maxLen, j - i + 1);
            j++;
        }

        return maxLen;
    }

    // https://leetcode.com/problems/max-consecutive-ones/
    public int findMaxConsecutiveOnes(int[] nums) {
        int count =0;
        int maxCount = 0;

        for(int i=0; i<nums.length; i++){
            if(nums[i] == 1){
                count++;
            }
            else{
                maxCount = Math.max(maxCount, count);
                count =0;
            }
        }
        return Math.max(maxCount, count);
    }

    // Input: s = "abciiidef", k = 3
    // https://leetcode.com/problems/maximum-number-of-vowels-in-a-substring-of-given-length/
    public int maxVowels(String s, int k) {
        if (s.length() == 0) return 0;
        int cnt = 0;

        int i = 0;
        while (i < k) {
            if (isVowel(s.charAt(i++))) ++cnt;
        }

        i = 0;
        int j = k, maxCount = cnt;

        while (j < s.length()) {
            if (isVowel(s.charAt(i++))) --cnt;
            if (isVowel(s.charAt(j++))) ++cnt;
            maxCount = Math.max(maxCount, cnt);
        }

        return maxCount;
    }

    private boolean isVowel(char c) {
        HashSet<Character> set = new HashSet<>();
        set.add('a');
        set.add('e');
        set.add('i');
        set.add('o');
        set.add('u');
        return set.contains(c);
    }

    // Input: s = "AABABBA", k = 1
    // https://leetcode.com/problems/longest-repeating-character-replacement/
    public int characterReplacement(String s, int k) {
        HashMap<Character, Integer> map = new HashMap<>();
        int i=0, j=0;
        int winSize = 0;
        int maxFreq = Integer.MIN_VALUE;
        while(j < s.length()){
            char ch = s.charAt(j);
            map.put(ch, map.getOrDefault(ch, 0)+1);
            maxFreq = Math.max(maxFreq, map.get(ch));

            while((j-i+1) - maxFreq > k){
                char left = s.charAt(i++);
                map.put(left, map.get(left)-1);
            }
            winSize = Math.max(winSize, j-i+1);
            j++;
        }

        return winSize;
    }

    // Input: nums = [0,1,1,1,0,1,1,0,1]
    // https://leetcode.com/problems/longest-subarray-of-1s-after-deleting-one-element/
    public int longestSubarray(int[] nums) {
        if(nums.length == 0) return 0;
        int i=0, j=0;
        int maxlen = Integer.MIN_VALUE;
        int ocount = 0;
        while(j < nums.length){
            int curr = nums[j];
            if(curr == 0) ocount++;
            while(ocount > 1){
                int left = nums[i++];
                if(left == 0) --ocount;
            }
            maxlen = Math.max(maxlen, j-i-1);
            j++;
        }
        return maxlen-1;
    }

    // Example: A = [2,1,3,4,6,3,8,9,10,12,56], w=4
    // https://leetcode.com/problems/sliding-window-maximum/
    // there is not much other way but doing Deque (forget about standard sliding window template for this)
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || k <= 0) {
            return new int[0];
        }
        int n = nums.length;
        int[] ans = new int[n-k+1];
        int ri = 0;
        // store index
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < nums.length; i++) {
            // remove numbers out of range k
            while (!q.isEmpty() && q.peek() < i - k + 1) {
                q.poll();
            }
            // remove smaller numbers in k range as they are useless
            while (!q.isEmpty() && nums[q.peekLast()] < nums[i]) {
                q.pollLast();
            }
            // q contains index... r contains content
            q.offer(i);
            if (i >= k - 1) {
                ans[ri++] = nums[q.peek()];
            }
        }
        return ans;
    }

    public String minWindow(String s, String t) {
        if(s.length() == 0 || t.length() == 0) return "";

        HashMap<Character, Integer> map1 = new HashMap<>();
        HashMap<Character, Integer> map2 = new HashMap<>();

        for(char c: t.toCharArray()){
            map2.put(c, map2.getOrDefault(c, 0) + 1);
        }
        String ans = "";
        int mCount = 0;
        int count = t.length();
        int i=-1;
        int j=-1;
        while(true){
            boolean f1 = false;
            boolean f2 = false;
            //acquire
            while(i< s.length()-1 && mCount < count){
                i++;
                char c = s.charAt(i);
                map1.put(c, map1.getOrDefault(c, 0) + 1);
                if(map1.getOrDefault(c, 0) <= map2.getOrDefault(c, 0)){
                    mCount++;
                }
                f1 = true;
            }
            // get ans and release
            while(j<i && mCount == count){
                String pans = s.substring(j+1, i+1);
                if(ans.length() == 0 || pans.length() < ans.length()){
                    ans = pans;
                }

                j++;
                char c = s.charAt(j);
                if(map1.get(c) == 1){
                    map1.remove(c);
                }else{
                    map1.put(c, map1.get(c)-1);
                }
                if(map1.getOrDefault(c, 0) < map2.getOrDefault(c,0)){
                    mCount--;
                }
                f2 = true;
            }
            if(!f1 && !f2) break;
        }
        return ans;
    }

    // forget about the above, use this template method
    // S = "ABAACBAB" T = "ABC"
    // s = "ADOBECODEBANC", t = "ABC"
    // https://leetcode.com/problems/minimum-window-substring
    public String minWindowSubstring(String str, String target){
        int[] map = new int[128];

        for(char c : target.toCharArray()){
            map[c]++;
        }

        int left=0, right =0;
        int minStart =0, minLen = Integer.MAX_VALUE;
        int counter = target.length();

        while(right < str.length()){
            char curr = str.charAt(right);
            if(map[curr] > 0) counter--;
            map[curr]--;
            right++;

            while(counter == 0){
                if(minLen > right - left){
                    minLen = right-left;
                    minStart = left;
                }

                char leftChar = str.charAt(left);
                map[leftChar]++;
                if(map[leftChar] > 0) counter++;
                left++;
            }
        }

        if(minLen == Integer.MAX_VALUE) return "";
        return str.substring(minStart, minStart + minLen);
    }

    // https://leetcode.com/problems/valid-number
    public boolean isNumber(String str) {
        str = str.trim();

        boolean decimalSeen  = false;
        boolean eSeen = false;
        boolean numberSeen = false;

        for(int i=0; i< str.length(); i++){
            char curr = str.charAt(i);
            if(curr >= '0' && curr <= '9'){
                numberSeen = true;
            }
            else if (curr == '.'){
                if(eSeen || decimalSeen) return false;
                decimalSeen = true;
            }
            else if (curr == 'e' || curr == 'E'){
                if(eSeen || !numberSeen) return false;
                numberSeen = false;
                eSeen = true;
            }
            else if (curr == '-' || curr == '+'){
                if(i > 0 && str.charAt(i-1) != 'e' &&  str.charAt(i-1) != 'E'){
                    return false;
                }
            }
            else{
                return false;
            }
        }
        return numberSeen;
    }

}
