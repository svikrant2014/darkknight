package org.darkknight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Knapsack {
    public static void main(String[] args) {
        Knapsack dp = new Knapsack();
        for (int[] row : dp.memo) {
            Arrays.fill(row, -1);
        }
        // int val = dp.knapsack(new int[]{1, 3, 4, 5, 7}, new int[]{1, 4, 5, 7, 6}, 7, 4);
        var res1 = dp.subsetSumMemo(new int[]{1, 3, 4, 5, 7}, 8);
        int ct = dp.count;
        // int val = dp.knapsackDP(new int[]{1,3,4,5,7}, new int[]{1,4,5,7, 6}, 7, 4);

        // boolean res = dp.subsetSum(new int[]{ 1, 2, 3, 4}, 44);
        int target = dp.targetSum(new int[]{1, 1, 1, 1, 1}, 3);
        var val = dp.minimumDifference(new int[]{3, 9, 7, 3});
        int minCoins = dp.coinChange(new int[]{1, 2, 5}, 11);
        int res = dp.countSubsetsGivenSum(new int[]{1, 2, 3, 4, 5}, 6);
    }

    int[][] memo = new int[101][1001];

    public int knapsack(int[] weights, int[] val, int W, int n) {
        if (n < 0 || W == 0) return 0;

        if (memo[W][n] != -1) return memo[W][n];

        if (weights[n] > W) {
            return memo[W][n] = knapsack(weights, val, W, n - 1);
        } else {
            return memo[W][n] = Math.max(val[n] + knapsack(weights, val, W - weights[n], n - 1), knapsack(weights, val, W, n - 1));
        }
    }

    public int knapsackDP(int[] weights, int[] val, int W, int n) {
        int[][] dp = new int[n + 1][W + 1];

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (weights[i - 1] > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(val[i - 1] + dp[i - 1][j - weights[i - 1]], dp[i - 1][j]);
                }
            }
        }
        return dp[n + 1][W + 1];
    }

    // 2, 3 ,7, 8, 10, target  == 11
    // if there exists a subset whose sum equals target, just return T/F
    boolean[][] dp;

    public boolean subsetSum(int[] nums, int target) {
        if (nums.length == 0) return false;
        int n = nums.length;

        dp = new boolean[n + 1][target + 1];
        for (int i = 0; i < dp[0].length; i++) {
            dp[0][i] = false;
        }
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = true;
        }

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[i].length; j++) {
                if (nums[i - 1] <= j) {
                    dp[i][j] = dp[i - 1][j - nums[i - 1]] || dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[n][target];
    }

    // learned couple of things in memo approach
    // HashMap memo was failing for just 1 case in leetcode, switched to Boolean Array
    // skipping nums[i] > target is not necessary ... can do it (maybe will save some steps but works fine with all cases)
    public boolean subsetSumMemo(int[] nums, int target) {
        Boolean[][] memo = new Boolean[nums.length + 1][target + 1];
        return checkSubset(nums, target, nums.length-1, memo);
    }


    private boolean checkSubset(int[] nums, int target, int i, Boolean[][] memo){
        if(target == 0) {
            return true;
        }
        if(i < 0 || target  < 0) return false;

        if(memo[i][target] != null) return memo[i][target];

        boolean res =  checkSubset(nums, target-nums[i], i-1, memo) || checkSubset(nums, target, i-1,memo);
        memo[i][target] = res;
        return res;
    }

    int count =0;
    private boolean countSubsetSum(int[] nums, int target, int i, Boolean[][] memo){
        if(target == 0) {
            count++;
            // return true; // you don't return else count will not increase, rest is same
        }
        if(i < 0 || target  < 0) return false;

        if(memo[i][target] != null) return memo[i][target];

        boolean res =  checkSubset(nums, target-nums[i], i-1, memo) || checkSubset(nums, target, i-1,memo);
        memo[i][target] = res;
        return res;
    }


    // it's a HARD one and doesn't seem to be working either ... forget it 
    public int minimumDifference(int[] nums) {
        int range = 0;
        for (int i : nums) {
            range += i;
        }
        boolean val = subsetSum(nums, range);

        // check in dp last row where it's T
        List<Integer> list = new ArrayList<>();
        int row = dp.length - 1;
        for (int i = 0; i < dp[0].length; i++) {
            if (dp[row][i] == true) {
                list.add(i);
            }
        }

        int min = Integer.MAX_VALUE;
        int size = list.size() % 2 == 0 ? list.size() / 2 - 1 : list.size() / 2;
        int value = list.get(size);
        min = Math.min(min, range - 2 * value);
        return min;
    }

    // https://leetcode.com/problems/partition-equal-subset-sum/
    // impressive explanation here - https://leetcode.com/problems/partition-equal-subset-sum/solutions/462699/whiteboard-editorial-all-approaches-explained/
    public boolean partitionEqualSubset(int[] nums) {
        if (nums.length == 0) return false;
        int sum = 0;
        for (int i : nums) {
            sum += i;
        }
        if (sum % 2 != 0) return false;

        return subsetSum(nums, sum / 2);
    }

    public int countSubsetsGivenSum(int[] nums, int target) {
        int n = nums.length;
        int[][] dp = new int[n + 1][target + 1];
        for (int i = 0; i < dp[0].length; i++) {
            dp[0][i] = 0;
        }
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (nums[i - 1] > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j - nums[i - 1]] + dp[i - 1][j];
                }
            }
        }
        return dp[n][target];
    }

    // only change from above is start the second loop for j=0 instead of j=1
    // that solved all target sum problem
    public int countSubsetsGivenSumForTargetSum(int[] nums, int target) {
        int n = nums.length;
        int[][] dp = new int[n + 1][target + 1];
        for (int i = 0; i < dp[0].length; i++) {
            dp[0][i] = 0;
        }
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                if (nums[i - 1] > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j - nums[i - 1]] + dp[i - 1][j];
                }
            }
        }
        return dp[n][target];
    }

    // https://leetcode.com/problems/target-sum/
    public int targetSum(int[] nums, int target) {
        int sum  = Arrays.stream(nums).sum();

        if(sum < target) return 0;
        if((sum+target)<0 || ((sum+target) % 2) != 0) return 0;

        int diff = sum + target;
        int count = countSubsetsGivenSum(nums, diff/2);
        return count;
    }

    // https://leetcode.com/problems/coin-change/
    // find the least coins needed to make the change
    public int coinChange(int[] coins, int amount) {
        if (coins.length == 0) return 0;
        int[][] dp = new int[coins.length + 1][amount + 1];
        // first col is already 0

        // first row mark as Int.MAX-1 (special mentioned in video)
        for (int i = 1; i < dp[0].length; i++) {
            dp[0][i] = Integer.MAX_VALUE - 1;
        }

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (coins[i - 1] <= j) {
                    dp[i][j] = Math.min(1 + dp[i][j - coins[i - 1]], dp[i - 1][j]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        int val = dp[coins.length][amount];
        if (val == Integer.MAX_VALUE - 1) return -1;
        else return val;
    }

    // https://leetcode.com/problems/coin-change-ii/
    // find all combinations of coins, that's why we sum it up
    public int coinChangeII(int amount, int[] coins) {
        if (coins.length == 0) return 0;
        int[][] dp = new int[coins.length + 1][amount + 1];

        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = 1;
        }
        dp[0][0] = 0;

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (coins[i - 1] <= j) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - coins[i - 1]];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[coins.length][amount];
    }
}
