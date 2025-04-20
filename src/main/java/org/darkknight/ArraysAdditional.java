package org.darkknight;

import java.util.*;

public class ArraysAdditional {
    public static void main(String[] args) {
        ArraysAdditional test = new ArraysAdditional();
        var val3 = test.getSecondLargestNumber(new ArrayList<>(Arrays.asList(1,2,3,4))); // 4321
        int val2 = test.maximumSwapMeta(597663966);

        int val = test.maximumSwap(2736);
    }

    // maximum-Swap-Meta
    // // VARIANT: What if you had to build the second largest number?
    public int maximumSwapMeta(int num) {
        if(num <= 11) return num;
        char[] nums = Integer.toString(num).toCharArray();
        int maxIdx = nums.length-1;

        for(int i = nums.length-1; i > 0; i--){
            if (nums[i] > nums[maxIdx]) {
                maxIdx = i;
            }
        }

        for(int i =0; i < nums.length; i++){
            if(i < maxIdx && nums[i] < nums[maxIdx]){
                char temp = nums[i];
                nums[i] = nums[maxIdx];
                nums[maxIdx] = temp;
                break;
            }
        }
        return Integer.parseInt(new String(nums));
    }

    // 99901 (corner case)
    // https://leetcode.com/problems/maximum-swap/
    public int maximumSwap(int num) {
        char[] numStr = Integer.toString(num).toCharArray();
        int n = numStr.length;
        int maxDigitIndex = -1, swapIdx1 = -1, swapIdx2 = -1;

        // Traverse the string from right to left, tracking the max digit and
        // potential swap
        for (int i = n - 1; i >= 0; --i) {
            if (maxDigitIndex == -1 || numStr[i] > numStr[maxDigitIndex]) {
                maxDigitIndex = i; // Update the index of the max digit
            } else if (numStr[i] < numStr[maxDigitIndex]) {
                swapIdx1 = i; // Mark the smaller digit for swapping
                swapIdx2 = maxDigitIndex; // Mark the larger digit for swapping
            }
        }

        // Perform the swap if a valid swap is found
        if (swapIdx1 != -1 && swapIdx2 != -1) {
            char temp = numStr[swapIdx1];
            numStr[swapIdx1] = numStr[swapIdx2];
            numStr[swapIdx2] = temp;
        }

        return Integer.parseInt(new String(numStr)); // Return the new number or the original if no
        // swap occurred
    }

    public static List<Integer> getSecondLargestNumber(List<Integer> num) {
        if (num == null || num.size() <= 1) {
            return new ArrayList<>();
        }

        int[] freqs = new int[10];
        for (int digit : num) {
            freqs[digit]++;
        }

        List<Integer> largestNum = new ArrayList<>();
        // Build the largest number by picking digits from 9 to 0
        for (int digit = 9; digit >= 0; digit--) {
            for (int times = 0; times < freqs[digit]; times++) {
                largestNum.add(digit);
            }
        }

        // Try to find two adjacent digits to swap
        for (int i = largestNum.size() - 1; i > 0; i--) {
            if (!largestNum.get(i).equals(largestNum.get(i - 1))) {
                // Swap
                int temp = largestNum.get(i);
                largestNum.set(i, largestNum.get(i - 1));
                largestNum.set(i - 1, temp);
                return largestNum;
            }
        }

        return new ArrayList<>(); // No second largest possible
    }

    // https://leetcode.com/problems/identify-the-largest-outlier-in-an-array
    // idea is that (totalSum-outlier)/2 should be there in count array.. then that num can be outlier
    public int getLargestOutlier(int[] nums) {
        Map<Integer, Integer> count = new HashMap<>();
        int total = 0, res = Integer.MIN_VALUE;
        for (int a : nums) {
            total += a;
            count.put(a, count.getOrDefault(a, 0) + 1);
        }
        for (int a : nums) {
            int outlier = total - a - a;
            if (count.getOrDefault(outlier, 0) > (outlier == a ? 1 : 0)) {
                res = Math.max(res, outlier);
            }
        }
        return res;
    }

    // https://leetcode.com/problems/continuous-subarray-sum
    // For e.g. in case of the array [23,2,6,4,7] the running sum is [23,25,31,35,42] and the remainders are
    // [5,1,1,5,0]. We got remainder 5 at index 0 and at index 3. That means, in between these two indexes we must have
    // added a number which is multiple of the k. Hope this clarifies your doubt :)
    public boolean checkSubarraySum(int[] nums, int k) {
        if(nums.length == 0 || nums.length < 2) return false;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);

        int sum =0;

        for(int i=0; i<nums.length; i++){
            sum += nums[i];

            int rem = sum%k;
            if(rem < 0 ){
                rem += k;
            }

            if(map.containsKey(rem)){
                int j = map.get(rem);
                if(i-j > 1) return true;
            }else{
                map.put(rem, i);
            }
        }
        return false;
    }

    // https://leetcode.com/problems/find-pivot-index
    public int pivotIndexLeftAndRightSum(int[] nums) {
        // Initialize total sum of the given array...
        int totalSum = 0;
        // Initialize 'leftsum' as sum of first i numbers, not including nums[i]...
        int leftsum = 0;
        // Traverse the elements and add them to store the totalSum...
        for (int ele : nums)
            totalSum += ele;
        // Again traverse all the elements through the for loop and store the sum of i numbers from left to right...
        for (int i = 0; i < nums.length; leftsum += nums[i++])
            // sum to the left == leftsum.
            // sum to the right === totalSum - leftsum - nums[i]..
            // check if leftsum == totalSum - leftsum - nums[i]...
            if (leftsum * 2 == totalSum - nums[i])
                return i;       // Return the pivot index...
        return -1;      // If there is no index that satisfies the conditions in the problem statement...
    }

    // meta Q
    // pair of integers in a sorted array (all integers are positive) that, when summed up, bring you the closest to the value of k
    // simple 2 pointer
    public static int[] findClosestPairArray(int[] arr, int k) {
        int left = 0, right = arr.length - 1;
        int closestSum = Integer.MAX_VALUE;
        int[] result = new int[2];

        while (left < right) {
            int sum = arr[left] + arr[right];

            // If this sum is closer to k, update result
            if (Math.abs(k - sum) < Math.abs(k - closestSum)) {
                closestSum = sum;
                result[0] = arr[left];
                result[1] = arr[right];
            }

            // Move pointers based on sum comparison
            if (sum < k) {
                left++; // Increase sum
            } else {
                right--; // Decrease sum
            }
        }
        return result;
    }

    // https://leetcode.com/problems/strobogrammatic-number
    public boolean isStrobogrammatic(String num) {
        Map<Character, Character> map = new HashMap<>();
        map.put('6', '9');
        map.put('9', '6');
        map.put('0', '0');
        map.put('1', '1');
        map.put('8', '8');

        int left =0, right = num.length()-1;

        while(left <= right){
            if(!map.containsKey(num.charAt(left))) return false;

            if(map.get(num.charAt(left)) != num.charAt(right)){
                return false;
            }
            left++;
            right--;
        }

        return true;
    }
}
