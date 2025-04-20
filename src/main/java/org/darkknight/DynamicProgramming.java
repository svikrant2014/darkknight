package org.darkknight;

import java.util.Arrays;

public class DynamicProgramming {
    public static void main(String[] args) {
        DynamicProgramming dp = new DynamicProgramming();
        dp.maxSubArray(new int[] {-2,1,-3,4,-1,2,1,-5,4});
        dp.rob(new int[] {1,2,1,1});
        dp.rob(new int[] {200,3,140,20,10});
        dp.rob(new int[] {1,2});
    }

    // https://leetcode.com/problems/longest-increasing-subsequence
    // O(N^2)
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];

        int finalMaxLength = 0;

        for (int i = 0; i < dp.length; i++) {
            int max = 0;

            // search in previous part of array if there is a larger val
            // and check if it's dp val is bigger than the current max found
            for (int j = 0; j < i; j++) {
                if(nums[j] < nums[i]){
                    if(dp[j] > max){
                        max = dp[j];
                    }
                }
            }

            dp[i] = max+1;

            finalMaxLength = Math.max(finalMaxLength, dp[i]);
        }

        return finalMaxLength;
    }

    public int rob(int[] nums1) {
        if(nums1.length == 0) return 0;
        if(nums1.length ==1 ) return nums1[0];
        if(nums1.length ==2 ) return Math.max(nums1[0], nums1[1]);

        int[] nums = new int[nums1.length*2-1];
        for(int i = 0, j=0; i < nums.length; i++, j++)
        {
            nums[i] = nums1[j];
            if(j == nums1.length-1) j = -1;
        }

        if(nums.length ==1 ) return nums[0];
        if(nums.length ==2 ) return Math.max(nums[0], nums[1]);

        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        for(int i =2; i< nums.length; i++){
            dp[i] = Math.max(nums[i] + dp[i-2], dp[i-1]);
        }
        return dp[nums.length-1]/2;
    }

    // https://leetcode.com/problems/longest-continuous-increasing-subsequence
    // ** remember this is continuous (other one uses DP for LIS)
    public int findLengthOfLCIS(int[] nums) {
        if(nums.length <= 1) return nums.length;

        int maxLen = 1;
        int sofar = 1;

        for(int i =1; i<nums.length; i++){
            if(nums[i-1] < nums[i]){
                sofar++;
            }else {
                sofar = 1;
            }
            maxLen = Math.max(maxLen, sofar);
        }
        return maxLen;
    }

    // https://leetcode.com/problems/house-robber
    public int houseRobber(int[] nums) {
        if(nums.length == 0) return 0;
        if(nums.length ==1 ) return nums[0];
        if(nums.length ==2 ) return Math.max(nums[0], nums[1]);

        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        for(int i =2; i< nums.length; i++){
            dp[i] = Math.max(nums[i] + dp[i-2], dp[i-1]);
        }
        return dp[nums.length-1];
    }

    // https://leetcode.com/problems/house-robber-ii
    public int houseRobberII(int[] nums) {
        if(nums.length == 0) return 0;
        if(nums.length == 1) return nums[0];

        int attempt1 = robSimple(nums, 0, nums.length-2);
        int attempt2 = robSimple(nums, 1, nums.length-1);

        return Math.max(attempt1, attempt2);
    }

    private int robSimple(int[] nums, int start, int end){
        int minus2 = 0;
        int minus1 = 0;

        int ans = 0;
        for(int i =start; i<=end; i++){
            ans = Math.max(minus2+nums[i], minus1);
            minus2 = minus1;
            minus1 = ans;
        }
        return ans;
    }

    // -2,1,-3,4,-1,2,1,-5,4
    // https://leetcode.com/problems/maximum-subarray
    public int maxSubArray(int[] arr) {
        if(arr == null || arr.length ==0) return 0;

        int sum = arr[0];
        int maxSum = arr[0];
        for(int i=1; i< arr.length; i++){
            sum = Math.max(arr[i], sum + arr[i]);
            maxSum = Math.max(maxSum, sum);
        }
        return maxSum;
    }

    // https://leetcode.com/problems/decode-ways
    public int numDecodings(String s) {
        if(s == null || s.length() == 0) return 0;
        int n = s.length();

        int[] dp = new int [n+1];
        dp[0] = 1;
        dp[1] = s.charAt(0) != '0' ? 1: 0;

        for(int i=2; i<= n; i++){
            int first = Integer.valueOf(s.substring(i-1, i));
            int sec = Integer.valueOf(s.substring(i-2, i));

            if(first >= 1 && first <=9){
                dp[i] += dp[i-1];
            }

            if(sec >=10 && sec <=26){
                dp[i] += dp[i-2];
            }
        }
        return dp[n];
    }

    // https://leetcode.com/problems/climbing-stairs
    // fibonacci style
    public int climbStairs(int n) {
        if(n == 1) return 1;
        if(n == 2) return 2;

        int[] dp = new int[n+1];

        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 2;

        for(int i=3; i<=n; i++){
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];
    }
}
