package org.darkknight;

import java.util.*;

public class ArrayProbs {
    public static void main(String[] args) {
        ArrayProbs tst = new ArrayProbs();
        tst.validWordAbbreviation("internationalization", "i12iz4n");
        tst.countAndSay(4);
        tst.search(new int[] {3, 1}, 1);
        int[] val = new int[]{4,8,1,6,12,3};
        // partition(val, 3);
        int[] arr = new int[]{3,2,5,0,12,9,50,0,0, 14, 16, 18, 100};
        // oddEven(arr);
        System.out.println("The list is: " +  Arrays.toString(arr));
    }

    // https://leetcode.com/problems/largest-number/
    public String largestNumber(int[] nums) {
        if(nums.length == 0) return "";
        String[] arr = new String[nums.length];
        int i =0;
        for(int num : nums){
            arr[i++] = String.valueOf(num);
        }

        Comparator<String> comparator =  new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String s1 = o1+o2;
                String s2 = o2+o1;
                return (s2.compareTo(s1));
            }};

        Arrays.sort(arr, comparator);
        if(arr[0].charAt(0) == '0') return "0";
        StringBuilder sb = new StringBuilder();
        for(String str : arr){
            sb.append(str);
        }
        return sb.toString();
    }

    // https://leetcode.com/problems/count-and-say/
    public String countAndSay(int n) {
        if(n == 1) return "1";

        String val = "1";
        for(int i = 1; i<n; i++) {
            StringBuilder sb = new StringBuilder();
            int j =0;
            int cnt=0;
            for(int k =0; k<val.length(); k++){
                char c = val.charAt(k);
                cnt=1;
                while(j+1<val.length() && val.charAt(++j) == c){
                    cnt++;k++;
                }
                sb.append(""+cnt).append(c);
            }
            val = sb.toString();
        }
        return val;
    }

    public int search(int[] nums, int target) {
        int lo = 0, hi = nums.length-1;
        int pivot;

        // [4,5,6,7,0,1,2]
        while(lo < hi){
            int mid = lo + (hi-lo)/2;

            if(nums[mid] == target) return mid;

            if(nums[mid] > nums[hi]){
                lo = mid+1;
            }else{
                hi =mid;
            }
        }

        hi = nums.length-1;
        if(target >= nums[lo] && target <= nums[hi]){
            lo = lo;
        }else{
            hi = lo;
            lo = 0;
        }

        while(lo <= hi){
            int mid = lo + (hi-lo)/2;
            if(nums[mid] == target) return mid;
            else if (target > nums[mid]){
                lo = mid+1;
            }
            else{
                hi = mid-1;
            }
        }

        return -1;
    }

    // https://leetcode.com/problems/merge-sorted-array/
    public void mergeSortedArray(int[] nums1, int m, int[] nums2, int n) {
        int x = m+n-1;

        m = m-1;
        n=n-1;
        while(m>=0 && n>=0){
            if(nums1[m] >= nums2[n]){
                nums1[x--] = nums1[m];
                m--;
            }
            else{
                nums1[x--] = nums2[n];
                n--;
            }
        }

        while(m >=0){
            nums1[x--] = nums1[m];
            m--;
        }
        while(n >=0){
            nums1[x--] = nums2[n];
            n--;
        }
    }

    // https://leetcode.com/problems/majority-element/
    public int majorityElement(int[] nums) {
        int maj = nums[0];
        int len = 1;
        int ctr = 1;
        while(len < nums.length){
            if(ctr == 0){
                maj = nums[len];
            }
            if(nums[len] == maj){
                ctr++;
            }
            else{
                ctr--;
            }
            len++;
        }
        return maj;
    }

    // https://leetcode.com/problems/non-decreasing-array/
    // [3, 4, 5, 3, 6, 8] - here if we make 5 to 3 ... then it's not fixed yet, that's why check with i-2
    public boolean nonDecreasingArray(int[] nums) {
        int ctr = 0;
        int len = nums.length;
        for (int i = 1; i < len && ctr <= 1; i++) {
            if (nums[i] < nums[i - 1]) {
                ctr++;
                if (i - 2 < 0 || nums[i - 2] <= nums[i])
                    nums[i - 1] = nums[i];
                else
                    nums[i] = nums[i - 1];
            }
        }
        return ctr <= 1;
    }

    // careful to reduce only j when swap is needed
    // it's there in elements
    private static void oddEven(int[] arr){
        if(arr.length == 0) return;

        int i = 0, j = arr.length-1;

        while(i <= j){
            if(arr[i]%2 == 0){
                i++;
            }
            else{
                int temp = arr[j];
                arr[j--] = arr[i];
                arr[i] = temp;
            }
        }
    }

    private static void partition(int[] arr, int pivotIndex){
        int val = arr[pivotIndex];
        int smaller = 0;

        for(int i=0; i< arr.length; ++i){
            if(arr[i] < val){
                swap(arr, smaller++, i);
            }
        }

        int larger = arr.length-1;
        for(int i=arr.length-1; i>= 0 && arr[i] >= val; --i){
            if(arr[i] > val){
                swap(arr, larger--, i);
            }
        }
    }

    private static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // https://leetcode.com/problems/peak-index-in-a-mountain-array
    public int peakIndexInMountainArray(int[] arr) {
        int lo = 0;
        int hi = arr.length-1;

        while(lo < hi){
            int mid = lo + (hi-lo)/2;

            if(arr[mid] > arr[mid+1] && arr[mid] > arr[mid-1]) return mid;
            else if (arr[mid] > arr[mid+1]) {
                hi = mid-1;
            }else {
                lo = mid+1;
            }
        }
        return lo;
    }

    // https://leetcode.com/problems/find-peak-element
    // Any peak will lie in a sorted sequence
    //If current element is greater than its right neighbor, we can eliminate the right half of the array
    //If it is less than the right neighbor, the left half can be eliminated
    //Neighbors will never be equal.
    //Thinking of binary search as a tool to eliminate half the array, helped me get a better handle on this problem.
    public int findPeakElement(int[] nums) {
        if (nums.length == 1) {
            return 0; // Only one element
        }

        int n = nums.length;

        // Check if the first or last element is a peak
        if (nums[0] > nums[1]) {
            return 0;
        }
        if (nums[n - 1] > nums[n - 2]) {
            return n - 1;
        }
        int start = 1;
        int end = n - 2;

        while (start <= end) {
            int mid = start + (end - start) / 2;

            if (nums[mid] > nums[mid - 1] && nums[mid] > nums[mid + 1]) {
                return mid; // Found a peak
            } else if (nums[mid] < nums[mid - 1]) {
                end = mid - 1; // Move left
            } else { // nums[mid] < nums[mid + 1]
                start = mid + 1; // Move right
            }
        }
        return -1; // Should never reach here if input has at least one peak
    }

    // https://leetcode.com/problems/next-permutation/
    // next perm - is next higher number
    // idea is to first find a point of pivot, from back which is the first number which is not in ascending order
    // if you can't find one, which means already at the last permutations of that array, we just reverse and return
    // find the first bigger number than pivotElement again searching from back
    // swap those two
    // finally reverse the rest of the array from pivotPoint+1
    public void nextPermutation(int[] nums){
        int len = nums.length;
        if(nums.length <=1) return;

        int pivot = len-1;
        while(pivot>0){
            if(nums[pivot-1] > nums[pivot]) break;
            pivot--;
        }

        if(pivot == 0){
            reverse(nums, 0, len-1);
            return;
        }

        int pivotVal = nums[pivot-1];
        int j=len-1;
        while(j >= pivot){
            if(nums[j] > pivotVal) break;
            j--;
        }
        swap(nums, j, pivot-1);
        reverse(nums, pivot, len-1);
    }

    // https://leetcode.com/problems/first-missing-positive/
    public int firstMissingPositive(int[] arr) {
        int n = arr.length;
        // first let's just update the numbers by adding n to it and making them positive which are negative or outside n
        for (int i = 0; i < n; i++) {
            if(arr[i] <= 0 || arr[i] >n){
                arr[i] = n+1;
            }
        }

        // traverse all nums and update their index location
        // for e.g. if i == 2, set arr[1] ==  -arr[1]
        for (int i = 0; i < n; i++) {
            int num = Math.abs(arr[i]);

            if(num > n){
                continue;
            }
            if(arr[num-1] > 0) { // prevent double negative
                arr[num-1] = -1 * arr[num-1];
            }
        }

        // find the first cell which isn't negative
        for (int i = 0; i < n; i++) {
            if(arr[i] >= 0)
                return i+1;
        }

        // if nothing was returned from above that means all nums are present
        // and next number will be n+1
        return n+1;
    }

    // dutch national flag color problem
    // https://leetcode.com/problems/sort-colors
    public void sortColors(int[] nums) {
        if(nums.length ==0) return;

        int left = 0, right = nums.length-1;
        int curr = 0;

        while(curr <= right){
            if(nums[curr] == 0){
                swap(nums, left, curr);
                left++;
                curr++;
            }
            else if(nums[curr] == 2){
                swap(nums, curr, right);
                right--;
            }
            else{
                curr++;
            }
        }
    }

    // sardar ji wala
    // https://leetcode.com/problems/maximize-distance-to-closest-person
    // idea is simple ... either the corner seats in start or end are empty, then you don't divide by 2
    // if it's in the middle, you take the /2
    public int maxDistToClosest(int[] seats) {
        int i = -1, j = 0;
        int max = 0;
        int len = seats.length - 1;
        while (i < len) {
            while (j < len && seats[j] != 1) {
                j++;
            }

            if (i == -1) {
                max = j;
            } else if (j == len && seats[j] != 1) {
                max = Math.max(max, j - i);
            } else {
                max = Math.max(max, (j - i) / 2);
            }
            i = j;
            j++;
        }
        return max;
    }

    // https://leetcode.com/problems/find-all-numbers-disappeared-in-an-array
    // find missing numbers in an array 1-n
    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> res = new ArrayList<>();

        for(int i = 0; i< nums.length; i++){
            int idx = Math.abs(nums[i])-1;
            if(nums[idx] > 0) nums[idx] = -1 * nums[idx];
        }

        for(int i = 0; i< nums.length; i++){
            if(nums[i] > 0) res.add(i+1);
        }
        return res;
    }

    // https://leetcode.com/problems/find-all-duplicates-in-an-array/
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if(nums.length < 2) return res;

        for(int i = 0; i<nums.length; i++){
            int idx = Math.abs(nums[i])-1;
            if(nums[idx] < 0){
                res.add(Math.abs(nums[i]));
            }
            nums[idx] = -nums[idx];
        }
        return res;
    }

    // https://leetcode.com/problems/find-n-unique-integers-sum-up-to-zero
    // mind you that if last one if left, will be automatically set to 0 (default int array val)
    public int[] sumZero(int n) {
        int[] res = new int[n];
        if(n<=1) return res;
        int j = 0;

        for(int i=1; i<= n/2; i++){
            res[j++] = i;
            res[j++] = -i;
        }
        return res;
    }

    // https://leetcode.com/problems/shuffle-an-array/
    // randomize array and reset
    public static int[] shuffle(int[] nums){
        if(nums.length == 0) return nums;
        int[] res = nums.clone();
        Random rand = new Random();

        for (int i = 1; i < res.length; i++) {
            int j = rand.nextInt(i+1);

            int tmp = res[j];
            res[j] = res[i];
            res[i] = tmp;
        }

        return res;
    }

    // https://leetcode.com/problems/rotate-array
    public void rotateArray(int[] nums, int k) {
        if(nums.length <=1) return;

        k = k % nums.length;

        int len = nums.length-1;
        reverse(nums, 0, len);
        reverse(nums, 0, k-1);
        reverse(nums, k, len);

    }
    private void reverse(int[] arr, int s, int e){
        while(s < e){
            int temp = arr[s];
            arr[s] = arr[e];
            arr[e] = temp;
            s++;
            e--;
        }
    }

    // https://leetcode.com/problems/remove-duplicates-from-sorted-array
    public int removeDuplicatesArray(int[] arr) {
        if(arr.length == 0) return 0;
        int count =0;

        for (int i = 1; i < arr.length; i++) {
            if(arr[i] == arr[i-1]){
                count++;
            }
            else{
                arr[i-count] = arr[i];
            }
        }
        return arr.length-count;
    }

    // https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii
    public int removeDuplicatesArrayII(int[] nums) {
        int len = nums.length;
        if(len < 3) return len;

        int curr =2;
        for(int i=2; i<len; i++){
            if(nums[i] != nums[curr-2]){
                nums[curr] = nums[i];
                curr++;
            }
        }
        return curr;
    }

    // https://leetcode.com/problems/number-of-good-pairs
    public int numIdenticalPairs(int[] nums) {
        int res = 0;
        int[] count = new int[101];

        for(int a : nums){
            res += count[a];
            count[a]++;
        }
        return res;
    }

    // https://leetcode.com/problems/maximum-product-subarray
    // follows above approach of Kadane's algo with a tweak
    // you have to keep a track of minSoFar as well and swap the min and max if number you are dealing with is negative
    public int maxProductSubArray(int[] nums) {
        if(nums == null || nums.length == 0) return 0;

        int max_so_far = nums[0];
        int iMax = nums[0];
        int iMin = nums[0];

        for(int i = 1; i< nums.length; i++){
            if(nums[i] < 0){
                int tmp = iMax;
                iMax = iMin;
                iMin = tmp;
            }
            iMax = Math.max(nums[i], iMax * nums[i]);
            iMin = Math.min(nums[i], iMin * nums[i]);

            max_so_far = Math.max(max_so_far, iMax);
        }
        return max_so_far;
    }

    // continuous subarray sum
    public static boolean hasSubarrayWithSum(int[] nums, int target) {
        HashMap<Integer, Integer> prefixSums = new HashMap<>();
        int sum = 0;

        prefixSums.put(0, -1); // Edge case: subarray starts at index 0

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];

            if (prefixSums.containsKey(sum - target)) {
                return true; // Found a subarray that sums to target
            }

            prefixSums.put(sum, i);
        }

        return false;
    }

    // continuous subarray sum if all are positive
    public static boolean hasSubarrayWithSumOnlyPositives(int[] nums, int target) {
        int left = 0, sum = 0;

        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];

            while (sum > target && left <= right) {
                sum -= nums[left];
                left++;
            }

            if (sum == target) return true;
        }
        return false;
    }

    // https://leetcode.com/problems/valid-word-abbreviation
    public boolean validWordAbbreviation(String word, String abbr) {
        int wordPtr = 0;
        int abbrPtr = 0;

        while(wordPtr < word.length() && abbrPtr < abbr.length()){
            char curr = abbr.charAt(abbrPtr);
            if(Character.isDigit(curr)){
                int steps = 0;

                if(curr == '0') return false;

                while(abbrPtr < abbr.length() && Character.isDigit(abbr.charAt(abbrPtr))){
                    steps = steps * 10 + abbr.charAt(abbrPtr) - '0';
                    abbrPtr += 1;
                }
                wordPtr += steps;
            }
            else {
                if(word.charAt(wordPtr) != abbr.charAt(abbrPtr)) return false;

                wordPtr +=1;
                abbrPtr +=1;
            }
        }

        return wordPtr == word.length() && abbrPtr == abbr.length();
    }

    // #meta
    // https://leetcode.com/problems/minimum-remove-to-make-valid-parenthese
    // optimum no space ver.
    public String minRemoveToMakeValid(String s) {
        int open = 0;
        int close = 0;
        StringBuilder res = new StringBuilder();

        for(char ch : s.toCharArray()){
            if(ch == '('){
                open += 1;
                res.append(ch);
            } else if (ch == ')') {
                if(open > close) {
                    close += 1;
                    res.append(ch);
                }
            }
            else{
                res.append(ch);
            }
        }

        if(open == close){
            return res.toString();
        }else{
            StringBuilder sb = new StringBuilder();

            for(int i = res.length(); i>= 0; i--){
                char curr = res.charAt(i);
                if(curr == '('){
                    if(open <= close){
                        sb.append(curr);
                    }
                    else{
                        open--;
                    }
                } else {
                    sb.append(curr);
                }
            }
         return sb.reverse().toString();
        }
    }

    // #meta variant to make valid parenthese (if it was char array & need to return final len of array)
    // minimum-remove-to-make-parenthese-valid
    // e.g. char[] s = {'(', 'a', 'b', ')', 'c', ')', 'd', '(', 'e', '('};
    // first pass - ['(', 'a', 'b', ')', 'c', '*', 'd', '(', 'e', '(']
    // sec pass - ['(', 'a', 'b', ')', 'c', '*', 'd', '(', 'e', '*']
    // result - ['(', 'a', 'b', ')', 'c', 'd', '(', 'e']
    public int minRemoveToMakeValid(char[] s) {
        int open = 0, writeIndex = 0;

        // First pass: Mark extra closing brackets ')' as invalid
        for (int i = 0; i < s.length; i++) {
            if (s[i] == '(') {
                open++;
            } else if (s[i] == ')') {
                if (open == 0) {
                    s[i] = '*'; // Mark invalid ')'
                    continue;
                }
                open--;
            }
        }
        // Second pass: Mark extra opening brackets '(' as invalid
        int n = s.length;
        writeIndex = n - 1;
        open = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (s[i] == '*') continue;
            if (s[i] == ')') {
                open++;
                s[writeIndex--] = s[i];
            } else if (s[i] == '(') {
                if (open == 0) continue; // extra '(', skip
                open--;
                s[writeIndex--] = s[i];
            } else {
                s[writeIndex--] = s[i]; // normal character
            }
        }

        // Return new valid start index
        return n - writeIndex - 1;
    }

    // https://leetcode.com/problems/minimum-add-to-make-parentheses-valid/
    public int minAddToMakeValid(String s) {
        int open = 0, close = 0;

        for(char c : s.toCharArray()){
            if(c == '(')
                open++;
            else if(open > 0)//only subtract open if they are available
                open--;
            else
                close++;
        }
        return open + close;
    }

    // #meta variant to return string instead of just number
    // to add min Parentheses as valid string
    public static String makeValidParentheses(String s) {
        int openNeeded = 0, closeNeeded = 0;
        StringBuilder result = new StringBuilder();

        // Count the number of insertions required
        for (char c : s.toCharArray()) {
            if (c == '(') {
                closeNeeded++; // We expect a closing ')'
            } else { // c == ')'
                if (closeNeeded > 0) {
                    closeNeeded--; // A valid match found
                } else {
                    openNeeded++; // Need an opening '('
                }
            }
        }

        // Construct valid string
        for (int i = 0; i < openNeeded; i++) {
            result.append('(');
        }
        result.append(s);
        for (int i = 0; i < closeNeeded; i++) {
            result.append(')');
        }

        return result.toString();
    }

    // https://leetcode.com/problems/buildings-with-an-ocean-view
    public int[] findBuildings(int[] heights) {
        List<Integer> res = new ArrayList<>();

        int max_so_far = Integer.MIN_VALUE;
        for(int i =heights.length-1; i >=0; i--){
            if(heights[i] > max_so_far){
                res.add(0, i);
                max_so_far = heights[i];
            }
        }

        int[] ans = new int[res.size()];
        for(int i=0; i<res.size(); i++){
            ans[i] = res.get(i);
        }

        return ans;
    }
}
