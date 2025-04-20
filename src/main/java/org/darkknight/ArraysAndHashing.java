package org.darkknight;

import java.util.*;

public class ArraysAndHashing {

    public static void main(String[] args) {
        ArraysAndHashing ah = new ArraysAndHashing();
        ah.isIsomorphic("badc", "baba");
        int count = ah.countPrimes(20);
        int val = ah.minDeletionCost("aaabbbabbbb", new int[]{3,5,10,7,5,3,5,5,4,8,1});
        String str = encode(new String[] {"helloduniyaj", "world"});
        // List<String> list = decode("12#helloduniyaj5#world");

        int [] val1 = arrayProduct(new int[] {1,1});

        boolean res = containsDuplicate(new int[]{ 5,3,4,1,2});

        boolean res1 = validAnagram("rac", "car");
        System.out.println(res1);

    }

    // ("aaabbbabbbb", new int[]{3,5,10,7,5,3,5,5,4,8,1})
    // https://leetcode.com/problems/minimum-time-to-make-rope-colorful/
    // renamed from: Minimum Deletion Cost to Avoid Repeating Letters
    public int minDeletionCost(String colors, int[] neededTime) {
        int len = colors.length();
        int minCost = 0;
        int maxCost = 0;
        for(int i =0; i< len; ++i){
            if(i>0 && colors.charAt(i) != colors.charAt(i-1)){
                maxCost = 0;
            }

            minCost += Math.min(maxCost, neededTime[i]);
            maxCost = Math.max(maxCost, neededTime[i]);
        }
        return minCost;
    }

    // https://leetcode.com/problems/sort-characters-by-frequency/description/
    public String frequencySort(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for(char c : s.toCharArray()){
            map.put(c, map.getOrDefault(c, 0)+1);
        }

        List<Character>[] bucket = new List[s.length()+1];

        for(char key : map.keySet()){
            int fq = map.get(key);
            if(bucket[fq] == null){
                bucket[fq] = new ArrayList<>();
            }
            bucket[fq].add(key);
        }

        StringBuilder sb = new StringBuilder();
        for(int pos = bucket.length-1; pos >=0; pos--){
            if(bucket[pos] != null){
                for(char c : bucket[pos]){
                    for(int i=0; i< pos; i++){
                        sb.append(c);
                    }
                }
            }
        }

        return sb.toString();
    }

    // https://leetcode.com/problems/kth-largest-element-in-an-array
    // count sort Approach (best approach I guess)
    // count the elements frequencies and walk back from max until kth element
    // using minValue as well to handle -ves
    public int findKthLargest3(int[] nums, int k) {
        int minValue = Integer.MAX_VALUE;
        int maxValue = Integer.MIN_VALUE;

        for(int num : nums){
            minValue = Math.min(minValue, num);
            maxValue = Math.max(maxValue, num);
        }

        int[] count = new int[maxValue - minValue+1];

        for(int num : nums){
            count[num-minValue]++;
        }

        int remain = k;

        for(int num = count.length-1; num >=0 ; num--){
            remain -= count[num];
            if(remain <= 0 ){
                return num+minValue;
            }
        }
        return -1;
    }

    // https://leetcode.com/problems/longest-consecutive-sequence
    // working & easy as well
    public int longestConsecutiveSequence(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for(int n : nums) {
            set.add(n);
        }
        int best = 0;
        for(int n : set) {
            if(!set.contains(n - 1)) {  // only check for one direction
                int m = n + 1;
                while(set.contains(m)) {
                    m++;
                }
                best = Math.max(best, m - n);
            }
        }
        return best;
    }

    private static String encode(String[] strs){
        if(strs.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (String str : strs){
            sb.append(str.length() + "#").append(str);
        }

        return sb.toString();
    }

    // https://leetcode.com/problems/decode-string
    private List<String> decode(String str){
        List<String> res = new ArrayList<>();
        if(str.length() == 0) return res;

        int i = 0;
        while(i<str.length()){
            int sep = str.indexOf("#", i);
            int len = Integer.valueOf(str.substring(i, sep));
            i = i + sep + len;
            res.add(str.substring(sep+1, i));
        }
        return res;
    }

    // https://leetcode.com/problems/product-of-array-except-self/
    private static int[] arrayProduct(int[] arr){
        if(arr.length <= 1) return arr;

        int[] res = new int[arr.length];

        int left =1;
        res[0] = 1;
        for(int i = 1; i<arr.length; i++){
            left = left * arr[i-1];
            res[i] = left;
        }

        int right = 1;
        for(int i = res.length-1; i >=0; i--){
            if(i< res.length-1){
                right = right * arr[i+1];
            }
            res[i] = res[i] * right;
        }

        return res;
    }

    // https://leetcode.com/problems/palindrome-permutation/
    public boolean canPermutePalindrome(String s) {
        if(s.length() == 0) return true;

        Set<Character> set = new HashSet<>();

        for (char c : s.toCharArray()){
            if(set.contains(c)) set.remove(c);
            else set.add(c);
        }
        return set.size() > 1 ? false : true;
    }

    // https://leetcode.com/problems/repeated-dna-sequences/
    public List<String> findRepeatedDnaSequences(String s) {
        Set<String> set = new HashSet<>();
        Set<String> res = new HashSet<>();

        for (int i = 0; i + 9 < s.length(); i++) {
            String tmp = s.substring(i, i + 10);
            if (set.contains(tmp)) {
                res.add(tmp);
            } else {
                set.add(tmp);
            }
        }

        return new ArrayList(res);
    }

    private static int[] topKFrequent(int[] nums, int k){
        List<Integer> res = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();

        for(int key : nums){
            map.put(key, map.getOrDefault(key, 0)+1);
        }

        List<Integer>[] bucket = new List[nums.length+1];
        for(int key : map.keySet()){
            int freq = map.get(key);
            if(bucket[freq] == null){
                bucket[freq] = new ArrayList<>();
            }
            bucket[freq].add(key);
        }

        for(int i = bucket.length-1; i >=0 && res.size() < k; i--){
            if(bucket[i] != null){
                res.addAll(bucket[i]);
            }
        }

        int[] ans = new int[k];
        int i = 0;
        for(int val : res){
            ans[i++] = val;
        }

        return ans;
    }

    // https://leetcode.com/problems/group-anagrams/description/
    private static List<List<String>> groupAnagrams(String[] strs){
        List<List<String>> res = new ArrayList<>();
        if(strs.length == 0) return res;

        Map<String, List<String>> map = new HashMap<>();

        for(String str : strs){
            char[] arr = new char[26];
            for(char ch : str.toCharArray()){
                arr[ch - 'a']++;
            }

            String key = String.valueOf(arr);
            if(!map.containsKey(key)){
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(str);
        }

        return new ArrayList<>(map.values());
    }

    // https://leetcode.com/problems/reorganize-string/
    // greedy approach
    // can be done in O(N) ... but for later
    // this is O(NlogN) solution using priority queue (remove takes logN in heap)
    public static String reorganizeString(String str) {
        StringBuilder sb = new StringBuilder();
        HashMap<Character, Integer> map = new HashMap<>();

        for(char c : str.toCharArray()){
            map.put(c, map.getOrDefault(c, 0)+1);
        }

        PriorityQueue<Character> maxHeap = new PriorityQueue<>((a,b) -> map.get(b) - map.get(a));
        maxHeap.addAll(map.keySet());

        while(maxHeap.size() > 1){
            char first = maxHeap.poll();
            char sec = maxHeap.poll();

            sb.append(first);
            sb.append(sec);

            map.put(first, map.get(first)-1);
            map.put(sec, map.get(sec)-1);

            if(map.get(first) > 0){
                maxHeap.add(first);
            }
            if(map.get(sec) > 0){
                maxHeap.add(sec);
            }
        }

        if(!maxHeap.isEmpty()){
            char last = maxHeap.remove();
            if(map.get(last) > 1){
                return "";
            }
            sb.append(last);
        }

        return sb.toString();
    }

    // Isomorphic means replace every char of s and make t
    // "foo", "bar" are not coz 'o' maps to both a & r
    // have to check back from t too ... that's why need Set as well
    public boolean isIsomorphic(String s, String t) {
        if(s.length() != t.length()) return false;

        HashMap<Character, Character> map = new HashMap<>();
        HashSet<Character> set = new HashSet<>();
        int len = 0;
        while(len < s.length()) {
            if(map.containsKey(s.charAt(len))) {
                if(t.charAt(len) == map.get(s.charAt(len))){
                    len++;
                    continue;
                }
                else{
                    return false;
                }
            }else{
                if(!set.add(t.charAt(len))) return false;
                map.put(s.charAt(len), t.charAt(len));
            }
            len++;
        }
        return true;
    }

    // https://leetcode.com/problems/count-primes/
    // Sieve of Eratosthenes
    // O(log(logN)), time - O(N)
    public int countPrimes(int n) {
        boolean[] notPrime = new boolean[n];
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (notPrime[i] == false) {
                count++;
                for (int j = 2; i*j < n; j++) {
                    notPrime[i*j] = true;
                }
            }
        }

        return count;
    }

    // https://leetcode.com/problems/minimum-deletions-to-make-character-frequencies-unique/
    public int minDeletions(String s) {
        if(s.length() == 0) return 0;
        int res =0;
        int[] arr = new int[26];
        for(char c : s.toCharArray()){
            arr[c-'a']++;
        }

        Set<Integer> used = new HashSet<>();
        for(int i=0; i<arr.length; i++){
            int freq = arr[i];

            while(freq > 0){
                if(!used.contains(freq)){
                    used.add(freq);
                    break;
                }
                freq--;
                res++;
            }
        }
        return res;
    }

    // https://leetcode.com/problems/find-all-anagrams-in-a-string/
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();

        if(s.length() < p.length()) return res;
        int[] arr1 = new int[26];
        int[] arr2 = new int[26];

        for(int i=0; i<p.length();i++){
            arr1[s.charAt(i)-'a']++;
            arr2[p.charAt(i)-'a']++;
        }

        int start = 0;
        int end = p.length();
        if(Arrays.equals(arr1, arr2)) res.add(0);

        while(end < s.length()){
            arr1[s.charAt(start) - 'a']--;
            arr1[s.charAt(end) - 'a']++;

            if(Arrays.equals(arr1, arr2)) res.add(start+1);

            start++;
            end++;
        }
        return res;
    }

    // use fixed size arr to maintain the size, that will be O(n) and space = O(1) coz it's just the size of alphabet
    // but this solution is more generic if there are unicode chars
    // https://leetcode.com/problems/valid-anagram/
    private static boolean validAnagram(String str1, String str2){
        if(str1.length() == 0 || str2.length() == 0) return false;

        HashMap<Character, Integer> map = new HashMap<>();
        for(int i= 0; i< str1.length(); i++){
            char ch = str1.charAt(i);
            map.put(ch, map.getOrDefault(ch, 0)+1);
        }

        for(int j = 0; j<str2.length(); j++){
            char ch = str2.charAt(j);
            if(map.containsKey(ch)){
                int count = map.get(ch);
                if(count == 1){
                    map.remove(ch);
                }else{
                    map.put(ch, count-1);
                }
            }
            else{
                return false;
            }
        }
        return true;
    }

    //if this was to be solved using no extra space, sorting can help
    // https://leetcode.com/problems/contains-duplicate
    private static boolean containsDuplicate(int[] arr){
        if(arr.length <= 0) return false;

        HashSet<Integer> set = new HashSet<>();
        for (int i =0; i< arr.length; i++) {
            if (set.contains(arr[i])) {
                return true;
            }
            set.add(arr[i]);
        }
        return false;
    }

    // https://leetcode.com/problems/contains-duplicate-ii
    public boolean containsDuplicateII(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();

        for(int i=0; i<nums.length; i++){
            int curr = nums[i];
            if(set.contains(curr)) return true;
            set.add(curr);

            if(set.size() > k){
                set.remove(nums[i-k]);
            }
        }
        return false;
    }

    // [[0,30],[5,10],[15,20]]
    private int minrooms(int[][] intervals){
        Arrays.sort(intervals, (a,b) -> a[0]-b[0]);
        PriorityQueue<int[]> queue = new PriorityQueue<>((int[] a, int[] b) -> a[1] - b[1]);
        queue.offer(intervals[0]);

        for(int i = 1; i<intervals.length; i++){
            int[] curr = intervals[i];
            int[] earliest = queue.remove();

            if(curr[0] < earliest[1]){
                queue.offer(curr);
            }else{
                earliest[1] = curr[1];
            }
            queue.offer(earliest);
        }
        return queue.size();
    }

    int[] commulativeSum;
    // https://leetcode.com/problems/random-pick-with-weight/
    // #meta
    // we are looking for upper bound
    public void pickRandomIndexWeighted(int[] w) {
        commulativeSum = new int[w.length];
        int commSum = 0;

        for(int i=0; i< w.length; i++){
            commSum += w[i];
            commulativeSum[i] = commSum;
        }
    }

    public int pickIndex() {
        Random random = new Random();
        int len = commulativeSum.length;

        int idx = random.nextInt(commulativeSum[len-1]) + 1;
        int left = 0, right = len - 1;

        // binary search
        while(left < right){
            int mid = left + (right-left)/2;
            if(commulativeSum[mid] == idx)
                return mid;
            else if(commulativeSum[mid] < idx)
                left = mid + 1;
            else
                right = mid;
        }
        return left;
    }
}
