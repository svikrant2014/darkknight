package org.darkknight;

import java.util.*;

public class Misc {
    public static void main(String[] args) {
        Misc misc = new Misc();
        misc.MissingNumber(new int[] {9,6,4,2,3,5,7,0,1});
        // misc.singleNumber(new int[] {4,2,1,2,1});
        misc.moveZeroes(new int[] {0,1,0,3,12});
        // misc.trappingRainWater(new int[]{ 0,1,0,2,1,0,1,3,2,1,2,1 });
        int val = misc.singleNumberApproach2(new int[]{1,5,5});
        String[] stocks = new String[]{"amz", "msft", "ttt", "sawas"};
        double[][] vals = new double[][]{{10, 20, 30}, {11, 200, 13}, {12, 12, 12}, {15, 15, 15}};

        misc.findAverage(stocks, vals);

    }

    public int MissingNumber(int[] nums) {
        int missing = 0;
        //Xor of all the ith length
        for (int i = 0; i <= nums.length; i++) {
            missing = missing ^ i;
        }

        //Xor With nums range given
        for(var num : nums)
        {
            missing = missing ^ num;
        }
        return missing;
    }

    public int numIslands(char[][] grid){
        if(grid.length == 0 || grid[0].length == 0) return 0;

        int count = 0;
        int rows = grid.length;
        int cols = grid[0].length;

        for(int i =0; i<rows; i++){
            for(int j =0; j<cols; j++){
                if(grid[i][j] == '1'){
                    count++;
                    dfs(grid, i, j);
                }
            }
        }
        return count;
    }

    private void dfs(char[][] grid, int i, int j){
        if(i< 0 || i > grid.length-1 || j<0 || j > grid[0].length-1 || grid[i][j] == '0'){
            return;
        }
        grid[i][j] = '0';
        dfs(grid, i+1, j);
        dfs(grid, i-1, j);
        dfs(grid, i, j+1);
        dfs(grid, i, j-1);
    }

    private class stock{
        String name;
        double average;
    }
    private List<String> findAverage(String[] stocks, double[][] prices){
        List<String> res = new ArrayList<>();
        stock[] coll = new stock[stocks.length];

        for(int i =0; i<prices.length; i++){
            double avg = 0;
            double sum = 0;
            double[] curr = prices[i];
            for (int j = 0; j < curr.length; j++) {
                sum += curr[j];
            }
            avg = sum / curr.length;
            stock  temp = new stock();
            temp.name = stocks[i];
            temp.average = avg;
            coll[i] = temp;
        }

        PriorityQueue<stock> pq = new PriorityQueue<>(new Comparator<stock>() {
            @Override
            public int compare(stock o1, stock o2) {
                if(o2.average > o1.average)
                    return 1;
                else
                    return -1;
            }
        });

        for(stock st : coll){
            pq.add(st);
        }

        for(int i =0; i<3; i++){
            stock val = pq.poll();
            res.add(val.name);
        }

        return res;
    }

    // https://leetcode.com/problems/single-number/
    // find single number which is not duplicate in array
    // This XOR operation works because it's like XORing all the numbers by itself.
    // So if the array is {2,1,4,5,2,4,1} then it will be like we are performing this operation
    //((2^2)^(1^1)^(4^4)^(5)) => (0^0^0^5) => 5.
    public int singleNumber(int[] nums){
        int result = 0;

        for (int i = 0; i < nums.length; i++) {
            result ^= nums[i];
        }

        return result;
    }

    // [0,1,0,3,12]
    // https://leetcode.com/problems/move-zeroes/description/
    public void moveZeroes(int[] nums){
        if(nums.length <=1) return;

        int i = 0, j=0;

        while(j < nums.length){
            if(nums[j] != 0){
                nums[i++] = nums[j++];
            }else{
                j++;
            }
        }
        while(i < nums.length){
            nums[i++] = 0;
        }
    }

    // optimal operations if most of them are 0's
    private void moveZeroes2(int[] nums){
        if(nums.length <=1 ) return;

        int lastfoundZero = 0;
        for(int i = 0; i<nums.length; i++){
            if(nums[i] != 0){
                int temp = nums[lastfoundZero];
                nums[lastfoundZero] = nums[i];
                nums[i] = temp;
                lastfoundZero++;
            }
        }
    }

    // meta version of move zeroes , move 0s to beginning
    public void moveZeroesMeta(int[] nums) {
        int left = nums.length - 1; // Start from the end
        for (int right = nums.length - 1; right >= 0; right--) {
            if (nums[right] != 0) {
                // Swap non-zero elements to the right
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left--;
            }
        }
    }

    // https://leetcode.com/problems/goat-latin
    public String toGoatLatin(String sentence) {
        Set<Character> vowel = new HashSet<>();
        for(char c : "aeiouAEIOU".toCharArray()) vowel.add(c);
        StringBuilder res = new StringBuilder();
        int cnt =0;

        for(String curr : sentence.split(" ")){
            cnt++;
            if(cnt > 1) res.append(" ");
            if(vowel.contains(curr.charAt(0))){
                res.append(curr);
            }
            else{
                res.append(curr.substring(1) + curr.charAt(0));
            }
            res.append("ma");
            for(int j=0; j< cnt; j++){
                res.append("a");
            }
        }
        return res.toString();
    }

    public int singleNumberApproach2(int[] nums){
        int sum = 0;
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if(set.add(nums[i]))
                sum += nums[i];
        }
        int otherSum = Arrays.stream(nums).sum();
        return 2*sum - otherSum;
    }

    // https://leetcode.com/problems/single-number-ii
    public int singleNumberII(int[] nums) {
        Set<Long> numsSet = new HashSet<>();
        long sumNums = 0;
        for (int num : nums) {
            numsSet.add((long) num);
            sumNums += num;
        }

        long sumSet = 0;
        for (long num : numsSet) {
            sumSet += num;
        }

        return (int) ((3 * sumSet - sumNums) / 2);
    }

    // https://leetcode.com/problems/convert-1d-array-into-2d-array/
    public int[][] construct2DArray(int[] original, int m, int n) {
        int[][] res = new int[m][n];
        if (original.length == 0) return res;
        if (m * n < original.length || m * n > original.length) return new int[0][0];

        int idx = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res[i][j] = original[idx++];
            }
        }
        return res;
    }

    // https://leetcode.com/problems/first-unique-character-in-a-string/
    public int firstUniqChar(String s) {
        if(s.length() ==0 ) return -1;
        char[] arr = new char[26];

        for(char ch : s.toCharArray()){
            arr[ch - 'a']++;
        }

        for(int i =0; i<s.length(); i++){
            if( arr[s.charAt(i) -'a'] ==1 ) return i;
        }
        return -1;
    }

    // https://leetcode.com/problems/car-fleet/
    public int carFleet(int target, int[] position, int[] speed) {
        double[] time = new double[target];

        for(int i=0; i< position.length; i++){
            time[position[i]] = (double)(target - position[i])/speed[i];
        }

        int fleet = 0;
        double prev = 0.0;

        for(int i = target-1; i>=0; i--){
            double curr = time[i];
            if(curr > prev){
                ++fleet;
                prev = curr;
            }
        }

        return fleet;
    }

    // https://leetcode.com/problems/container-with-most-water
    public int containerWithMostWater(int[] height) {
        if(height.length <=1 ) return 0;

        int left = 0, right = height.length-1;
        int maxArea = 0;

        while(left < right){
            int area = Math.min(height[left], height[right]) * (right-left);
            maxArea = Math.max(maxArea, area);

            if(height[left] > height[right]){
                right--;
            }else
            {
                left++;
            }
        }
        return maxArea;
    }

    // { 0,1,0,2,1,0,1,3,2,1,2,1 }
    // https://leetcode.com/problems/trapping-rain-water/description/
    public int trappingRainWater(int[] arr){
        if(arr.length <= 2) return 0;

        int leftMax = Integer.MIN_VALUE;
        int rightMax = Integer.MIN_VALUE;

        int left = 0;
        int right = arr.length-1;
        int res = 0;
        while(left <= right){
            leftMax = Math.max(leftMax, arr[left]);
            rightMax = Math.max(rightMax, arr[right]);

            if(leftMax < rightMax){
                res += leftMax - arr[left];
                left++;
            }
            else{
                res += rightMax - arr[right];
                right--;
            }
        }
        return res;
    }

    HashMap<Character, Integer> orderMap;
    // https://leetcode.com/problems/verifying-an-alien-dictionary
    public boolean isAlienSorted(String[] words, String order) {
        orderMap = new HashMap<>();
        for(int i=0; i< order.length(); i++){
            orderMap.put(order.charAt(i), i);
        }

        for(int i =0; i< words.length-1; i++){
            if(!compareWords(words[i], words[i+1])) return false;
        }

        return true;
    }

    private boolean compareWords(String str1, String str2){
        int len1 = str1.length(), len2 = str2.length();

        for(int i = 0, j=0; i< len1 && j< len2; i++,j++){
            if(str1.charAt(i) != str2.charAt(j)){
                if(orderMap.get(str1.charAt(i)) > orderMap.get(str2.charAt(j))){
                    return false;
                }else{
                    return true;
                }
            }
        }

        if(len1 > len2) return false;
        return true;
    }
}
