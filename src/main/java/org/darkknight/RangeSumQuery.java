package org.darkknight;

public class RangeSumQuery {
    public static void main(String[] args) {

    }
    int[] sum;
    // https://leetcode.com/problems/range-sum-query
    public void NumArray(int[] nums) {
        sum = new int[nums.length+1];
        for(int i=1; i<nums.length; i++){
            sum[i] = sum[i-1] + nums[i];
        }
    }

    // https://leetcode.com/problems/range-sum-query-2d-immutable
    public int sumRange(int left, int right) {
        return (sum[right+1] - sum[left]);
    }


    int[][] dp;
    // https://leetcode.com/problems/range-sum-query-2d-immutable
    // check editorial for explanation but it's intuitive, isn't it
    public void NumMatrix(int[][] matrix) {
        if(matrix.length == 0 || matrix[0].length == 0) return;
        dp = new int[matrix.length+1][matrix[0].length+1];
        for(int i = 0; i<matrix.length; i++){
            for(int j = 0; j<matrix[0].length; j++){
                dp[i+1][j+1] = dp[i+1][j] + dp[i][j+1] + matrix[i][j] - dp[i][j];
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        return (dp[row2+1][col2+1] - dp[row1][col2+1] - dp[row2+1][col1] + dp[row1][col1]);
    }
}
