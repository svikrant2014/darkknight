package org.darkknight;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BinarySearch {
    public static void main(String[] args) {
        int val = findMin(new int[]{4,5,6,7,0,1,2});
        int res = search(new int[]{ -1,0,3,5,9,12 }, 9);
    }

    // https://leetcode.com/problems/kth-missing-positive-number/
    public int findKthPositive(int[] arr, int k) {
        if(arr.length == 0) return 0;

        int lo = 0;
        int hi = arr.length-1;

        while(lo <= hi){
            int mid = lo + (hi-lo)/2;
            if(arr[mid] - mid-1 < k){
                lo = mid+1;
            }else{
                hi = mid-1;
            }
        }
        return lo+k;
    }

    // VARIANT: What if you had to return the Kth missing positive number from the leftmost element?
    public int findKthPositiveMeta(int[] arr, int k) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int missing = arr[mid] - arr[0] - mid;

            if (missing < k) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return arr[0] + k + right;
    }

    // https://leetcode.com/problems/missing-element-in-sorted-array/
    public int missingElement(int[] nums, int k) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {

            int mid = left + (right - left) / 2;

            // If number of positive integers
            // which are missing before arr[mid]
            // is less than k -->
            // continue to search on the right.
            if (missingElementsBeforeIndex(mid, nums) < k) {
                left = mid + 1;
                // Otherwise, go left.
            } else {
                right = mid - 1;
            }
        }

        // nums[right] + k - (nums[right] - nums[0] - right) = k - (nums[0] - right) = k + nums[0] + right

        return k + nums[0] + right;

    }
    private int missingElementsBeforeIndex(int index, int[] nums) {
        return nums[index] - nums[0] - index;
    }

    // https://leetcode.com/problems/koko-eating-bananas/
    public int kokoEatingBanana(int[] piles, int h) {
        int hi = Integer.MIN_VALUE;
        for(int i = 0; i<piles.length; i++){
            hi = Math.max(hi, piles[i]);
        }

        int lo = 1;
        int res = hi;

        while(lo < hi){
            int mid = lo + (hi - lo)/2;

            int totalHrs = 0;
            for (int pile : piles){
                totalHrs += Math.ceil((double)pile/mid);
            }

            if(totalHrs <= h){
                res = Math.min(res, mid);
                hi = mid;
            }
            else{
                lo = mid+1;
            }
        }
        return res;
    }

    // https://leetcode.com/problems/search-in-rotated-sorted-array/
    // find point of rotation
    // then check if target exists on left or right side of por
    // then just search that side
    public int searchRotatedArray(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length-1;

        while(lo < hi){
            int mid = lo + (hi - lo)/2;
            int val = nums[mid];

            if(val > nums[hi]){
                lo = mid+1;
            }
            else{
                hi = mid;
            }
        }

        hi = nums.length-1;
        if(target >= nums[lo] && target <= nums[hi]){
            lo = lo;
        }else{
            hi =lo;
            lo = 0;
        }

        while(lo <= hi){
            int mid = lo + (hi-lo)/2;

            if(nums[mid] == target) return mid;
            else if (nums[mid] > target) hi = mid-1;
            else lo = mid+1;
        }

        return -1;
    }

    //https://leetcode.com/problems/search-in-rotated-sorted-array-ii/
    // with duplicates
    public boolean searchRotatedII(int[] nums, int target) {
        if(nums.length == 0) return false;
        int lo = 0, hi = nums.length-1;
        while(lo<= hi){
            int mid = lo + (hi-lo)/2;

            if(nums[mid] == target) return true;

            if(nums[lo] < nums[mid]){
                if(target >= nums[lo] && target < nums[mid]){
                    hi = mid-1;
                }else{
                    lo = mid+1;
                }
            } else if(nums[lo] == nums[mid]){
                lo++;
            }else{
                if(target > nums[mid] && target<= nums[hi]){
                    lo = mid+1;
                }else{
                    hi = mid-1;
                }
            }
        }
        return false;
    }

    // https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/description/
    public static int findMin(int[] nums) {
        int lo = 0;
        int hi = nums.length-1;

        while(lo < hi){
            int mid = lo + (hi - lo)/2;
            int val = nums[mid];

            if(val > nums[hi]){
                lo = mid+1;
            }
            else{
                hi = mid;
            }
        }
        return nums[lo];
    }

    // https://leetcode.com/problems/search-a-2d-matrix/description/
    // O(log(m * n))
    public static boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;

        int left = 0;
        int right = (m * n) - 1;


        while(left < right){
            int mid = left + (right - left)/2;
            int val = matrix[mid/n][mid%n];

            if(val == target) return true;
            if(val < target) left = mid+1;
            else{
                right = mid-1;
            }
        }

        return false;
    }

    // https://leetcode.com/problems/search-a-2d-matrix-ii
    // O((m + n)) .. every row or col is searched only once
    // rows are sorted ... but cols are too
    // this is simpler ... you either move in row or col
    public boolean searchMatrixII(int[][] matrix, int target) {
        int rows = matrix.length; int cols = matrix[0].length;

        int m = 0; int n = cols-1;

        while(m < rows && n >=0){
            int vals = matrix[m][n];

            if(vals == target) return true;
            else if (vals > target) n--;
            else m++;
        }
        return false;
    }

    public static int search(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length-1;

        while(lo <= hi){
            int mid = lo + (hi - lo)/2;
            if(nums[mid] == target) return mid;
            else if (nums[mid] < target){
                lo = mid + 1;
            }
            else{
                hi = mid-1;
            }
        }
        return -1;
    }

    // https://leetcode.com/problems/find-k-closest-elements
    // Perform a binary search. At each operation, calculate mid = (left + right) / 2 and compare the two elements located at arr[mid] and arr[mid + k].
    // If the element at arr[mid] is closer to x, then move the right pointer. If the element at arr[mid + k] is closer to x,
    // then move the left pointer. Remember, the smaller element always wins when there is a tie.
    public List<Integer> findKClosestElements(int[] arr, int k, int x) {
        int lo = 0;
        int hi = arr.length-k;

        while(lo < hi){
            int mid = lo + (hi-lo)/2;
            if(x - arr[mid] > arr[mid+k] -x){
                lo = mid+1;
            }else{
                hi = mid;
            }
        }

        List<Integer> res = new ArrayList();
        for(int i=lo; i< lo+k; i++){
            res.add(arr[i]);
        }
        return res;
    }

    // Striver explains
    // https://leetcode.com/problems/median-of-two-sorted-arrays/
    public double medianSortedArrays(int[] a, int[] b){
        int n1 = a.length;
        int n2 = b.length;

        // just to make sure we run binary search on smaller array
        if(n1 > n2) return medianSortedArrays(b, a);

        int low =0, high = n1;
        int left =( n1+n2+1)/2;
        int n = n1+n2;

        while(low <= high){
            int mid1 = (low+high)/2;
            int mid2 = left - mid1;

            int l1 = Integer.MIN_VALUE, l2 = Integer.MIN_VALUE;
            int r1 = Integer.MAX_VALUE, r2 = Integer.MAX_VALUE;

            if(mid1 < n1) r1 = a[mid1];
            if(mid2 < n2) r2 = b[mid2];

            if(mid1-1 >= 0) l1 = a[mid1-1];
            if(mid2-1 >= 0) l2 = b[mid2-1];

            if(l1 <= r2 && l2 <= r1){
                if( n %2 == 1 ) return Math.max(l1, l2);
                return ((double) (Math.max(l1, l2) + Math.min(r1, r2))) / 2.0;
            }

            else if (l1 > r2) high = mid1-1;
            else low = mid1 +1;
        }
        return 0;
    }

    // https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/
    // so you wanna find where a number starts and ends in a sorted array
    // idea is to find the min pos where it starts and find the pos where it's next bigger number starts
    // for e.g. while looking for 8, search for something <= 8 and something >8 and in between is the range
    // #meta
    public int[] searchRange(int[] nums, int target){
        int first = firstPosBinarySearch(nums, target);
        int last = firstPosBinarySearch(nums, target+1) -1;

        if(first <= last){
            return new int[]{first, last};
        }
        return new int[]{-1, -1};
    }

    // modified binary search
    // not looking for exact match, just something >=
    private int firstPosBinarySearch(int[] nums, int target){
        int n = nums.length;
        int first_pos = n; // just init with max boundary

        int low = 0, high = n-1;
        while(low <= high){
            int mid = low + (high-low)/2;
            if(nums[mid] >= target){
                first_pos = mid;
                high = mid -1;
            }
            else{
                low = mid+1;
            }
        }
        return first_pos;
    }

    // #meta - it's using the above modified binary-search approach
    // VARIANT: What if you had to return the number of unique elements in an integer array?
    // Note this must be done in K LOG N time complexity (unless the input has all unique integers)
    public static int countUniqueElements(int[] nums) {
        int count = 0;
        int i = 0;

        while (i < nums.length) {
            int target = nums[i];
            int left = i, right = nums.length - 1;

            // Binary search to find the rightmost occurrence of target
            while (left <= right) {
                int mid = (left + right) / 2;
                if (nums[mid] <= target)
                    left = mid + 1;
                else
                    right = mid - 1;
            }

            count++;
            i = right + 1;  // Move to the next unique element
        }

        return count;
    }

    // https://leetcode.com/problems/powx-n/
    public double power(double x, int n){
        if(n ==0) return 1;
        if(x ==0) return 0;

        if(n < 0){
            return 1/x * power(1/x, -(n+1));
        }

        return (n%2 == 0) ? power(x*x , n/2) : x*power(x*x , n/2);
    }

    // every time you are doing half and multiply the res by itself
    // kind of binary search
    // use double to get decimal precision
    // check of negative
    // to handle n == INT.MIN_VALUE, convert it to long as absolute val
    public double powerIterative(double x, int n){
        if(n ==0) return 1;

        double res =1;
        long absN = Math.abs((long)n);
        if(n < 0) {
            x = 1/x;
        }

        while(absN > 0){
            // if n is odd, multiply x with res
            if((absN & 1) ==1){
                res = res *x;
            }
            // n must be even now
            absN = absN>>1;
            x = x*x;
        }
        return res;
    }

    // https://leetcode.com/problems/single-element-in-a-sorted-array/
    public int singleNonDuplicate(int[] arr) {
        if(arr == null || arr.length == 0) return -1;
        int lo = 0;
        int hi = arr.length-1;

        while(lo < hi){
            int mid = lo + (hi-lo)/2;

            // if mid is even, you expect to land at a paired place, just check the next one to make sure
            // else single one is on left side
            if(mid % 2 == 0){
                if(arr[mid] == arr[mid+1]){
                    lo = mid +2;
                }
                else{
                    hi = mid;
                }
            }
            else{
                // mid is odd, so just check this one where you landed and prev one
                if(arr[mid] == arr[mid-1]){
                    lo = mid+1;
                }
                else{
                    hi = mid;
                }
            }
        }

        // 'start' should always be at the beginning of a pair.
        // When 'start > end', start must be the single element.
        return arr[lo];
    }

    // array is rotated multiple times
    // but the idea is -> if first ele is smaller than last ele, means it's sorted and you can return the first ele
    // else check the middle one, if mid one is greater than last one, means that half is sorted and we need to search in other half
    // https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/
    public static int findMinInRotatedArray(int[] nums){
        if(nums.length == 0) return -1;
        int lo = 0, hi = nums.length-1;

        while(lo < hi){
            if(nums[lo] < nums[hi]){
                return nums[lo];
            }

            int mid = lo + (hi-lo)/2;
            if(nums[mid] >= nums[lo]){
                lo = mid+1;
            }
            else{
                hi = mid;
            }
        }
        return nums[lo];
    }

    // https://leetcode.com/problems/number-of-steps-to-reduce-a-number-to-zero/
    public int numberOfSteps(int num) {
        int steps = 0; // We need to keep track of how many steps this takes.
        while (num != 0) { // Remember, we're taking steps until num is 0.
            if (num % 2 == 0) { // Modulus operator tells us num is *even*.
                num = num / 2; // So we divide num by 2.
            } else { // Otherwise, num must be *odd*.
                num = num - 1; // So we subtract 1 from num.
            }
            steps = steps + 1; // We *always* increment steps by 1.
        }
        return steps; // And at the end, the answer is in steps so we return it.
    }

    // https://leetcode.com/problems/capacity-to-ship-packages-within-d-days
    public int shipWithinDays(int[] weights, int days) {
        int totalLoad =0, maxLoad = 0;

        for(int w : weights){
            maxLoad = Math.max(maxLoad, w);
            totalLoad += w;
        }

        int lo = maxLoad, hi = totalLoad;

        while(lo < hi){
            int mid = lo + (hi-lo)/2;

            if(feasible(weights, mid, days)){
                hi = mid;
            }
            else{
                lo = mid+1;
            }
        }
        return lo;
    }

    private boolean feasible(int[] weights, int cap, int days){
        int daysNeeded = 1, currentLoad = 0;

        for(int w : weights){
            currentLoad += w;
            if(currentLoad > cap){
                daysNeeded++;
                currentLoad = w;
            }
        }

        return daysNeeded <= days;
    }

    // very similar to above but weird
    // we gonna do a BS between 1 and max of array to find is this len can be cut keeping k cuts
    // Instead of finding the last valid answer (upper bound of valid answer), I try to find the first invalid answer (lower bound of invalid answer).
    //Then lastly, decrement by 1, obviously.
    // Because we are asked to find the maximum valid answer, if you try to guess from 1 to Integer.MAX_VALUE, you will get this:
    //1:valid, 2: valid, 3: valid, ..., x: valid, x+1: invalid, x+2: invalid, x+3: invalid (x is the answer)
    //Now it's looks like a legit binary search: find the the last valid value
    // https://leetcode.com/problems/cutting-ribbons
    public int maxLength(int[] ribbons, int k) {
        int lo = 1, hi = Arrays.stream(ribbons).max().getAsInt()+1;

        while(lo < hi){
            int mid = lo + (hi-lo)/2;

            if(!canCut(ribbons, mid, k)){
                hi = mid;
            }
            else{
                lo = mid+1;
            }
        }

        return lo-1;
    }

    private boolean canCut(int[] ribbons, int len, int k){
        int count = 0;
        for(int ribbon : ribbons){
            count += (ribbon/len);
        }
        return count >= k;
    }

}
