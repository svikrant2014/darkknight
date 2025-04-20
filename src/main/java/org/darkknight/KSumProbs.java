package org.darkknight;

import java.util.*;

// 2Sum, 3Sum, 4Sum
public class KSumProbs {
    public static void main(String[] args) {

    }

    // https://leetcode.com/problems/two-sum/
    private static int[] twoSum(int [] arr, int target){
        HashMap<Integer, Integer> set = new HashMap<>();
        int x = 0, y = 0;

        for(int i= 0; i< arr.length; i++){
            int val = target - arr[i];
            if(set.containsKey(val)){
                x = set.get(val);
                y = i;
            }
            else{
                set.put(arr[i], i);
            }
        }
        return new int[] { x, y };
    }

    // 2, 7, 8, 9, 11, 15, 25 ... target = 18
    // https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/
    private static int[] twoSumSorted(int[] nums, int target){
        if(nums.length < 2) return new int[2];
        int i = 0, j = nums.length-1;

        while(i<j){
            int val = nums[i] + nums[j];
            if(nums[i] + nums[j] == target){
                return new int[] { i+1, j+1 };
            }
            else if (val > target){
                j--;
            }
            else{
                i++;
            }
        }
        return new int[2];
    }

    // 2 sum variant asked by meta
    // create a hashmap key combining both values of each pair and save it to a map with freq
    public static int twoSumMeta(int[][] dominoes, int target) {
        Map<String, Integer> pairMap = new HashMap<>();
        int count = 0;

        for (int[] pair : dominoes) {
            int a = pair[0], b = pair[1];

            // Compute complement keys
            String complement1 = (target - a) + "," + (target - b);
            String complement2 = (target - b) + "," + (target - a);

            // Check if the complement exists in the map
            count += pairMap.getOrDefault(complement1, 0);
            count += pairMap.getOrDefault(complement2, 0);

            // Store the current pair in the map
            String key = a + "," + b;
            pairMap.put(key, pairMap.getOrDefault(key, 0) + 1);
        }
        return count;
    }

    public int secondVariantTwoSum(List<int[]> dominoes, int target) {
        Map<Integer, Integer> dominoToFreq = new HashMap<>();
        int result = 0;

        for (int[] domino : dominoes) {
            int a1 = domino[0];
            int a2 = domino[1];

            int b1 = target - a1;
            int b2 = target - a2;
            int bKey = b1 * 10 + b2;

            if (dominoToFreq.containsKey(bKey)) {
                result += dominoToFreq.get(bKey);
            }

            int aKey = a1 * 10 + a2;
            dominoToFreq.put(aKey, dominoToFreq.getOrDefault(aKey, 0) + 1);
        }

        return result;
    }

    // sum of 3 ints == 0
    // return all triplets
    // [-1,0,1,2,-1,-4] -> [-4, -1, -1, 0, 1, 2]
    // O(n^2)
    private static List<List<Integer>> threeSum(int[] nums){
        List<List<Integer>> res = new ArrayList<>();
        if(nums.length < 3) return res;

        Arrays.sort(nums);

        for(int i = 0; i< nums.length-2; i++){
            if(i > 0 && nums[i] == nums[i-1]) continue;
            int sum = 0 - nums[i];

            int j = i+1; int k = nums.length-1;
            while(j < k){
                int localSum = nums[j] + nums[k];
                if(localSum == sum){
                    res.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    while(j < k && nums[j] == nums[j+1]){
                        j++;
                    }
                    while(j < k && nums[k] == nums[k-1]){
                        k--;
                    }
                    j++;
                    k--;
                }
                else if (localSum < sum) j++;
                else k--;
            }
        }
        return res;
    }

    // Time Complexity: O(n^k-1) or O(n^3)
    // https://leetcode.com/problems/4sum
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        return kSum(nums, target, 0, 4);
    }

    private List<List<Integer>> kSum(int[] nums, long target, int start, int k){
        List<List<Integer>> res = new ArrayList<>();

        // If we have run out of numbers to add, return res.
        if(start == nums.length) return res;

        // There are k remaining values to add to the sum. The
        // average of these values is at least target / k.
        long avg = target/k;
        if(nums[start] > avg || avg > nums[nums.length-1]) return res;

        if(k==2) return twoSumII(nums, target, start);

        for(int i =start; i<nums.length; ++i){
            if(i > start && nums[i-1] == nums[i]) continue;

            for(List<Integer> subset : kSum(nums, target-nums[i], i+1, k-1)){
                res.add(new ArrayList<>(Arrays.asList(nums[i])));
                res.get(res.size()-1).addAll(subset);
            }
        }
        return res;
    }


    // this is for k-sum, simple TwoSumII is above
    private List<List<Integer>> twoSumII(int[] nums, long target, int start){
        List<List<Integer>> res = new ArrayList<>();
        int i = start, j = nums.length-1;

        while(i < j){
            if(i > start && nums[i] == nums[i-1]) {
                i++;
                continue;
            };
            int val = nums[i] + nums[j];
            if(val == target) {
                res.add(Arrays.asList(nums[i], nums[j]));

                while (i < j && nums[i] == nums[i+1]){
                    i++;
                }
                while (i < j && nums[j] == nums[j-1]){
                    j--;
                }
                i++;
                j--;
            }else if (val < target){
                i++;
            }else{
                j--;
            }

        }
        return res;
    }

    // there is a kSum generic approach also
    // https://leetcode.com/problems/4sum-ii/
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        int cnt = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for(int a : nums1){
            for(int b : nums2){
                map.put(a+b, map.getOrDefault(a+b, 0)+1);
            }
        }

        for(int c : nums3){
            for(int d : nums4){
                cnt += map.getOrDefault(-(c+d), 0);
            }
        }
        return cnt;
    }
}
