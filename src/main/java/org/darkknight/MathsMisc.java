package org.darkknight;

import java.util.*;

public class MathsMisc {
    public static void main(String[] args) {
        MathsMisc misc = new MathsMisc();
        var val = misc.reverseInteger(-999999991);
    }

    // https://leetcode.com/problems/factorial-trailing-zeroes/
    // coz !5 = 120 (first 0 appears)
    public int trailingZeroes(int n) {
        int zeroCount = 0;
        while(n > 0){
            n /= 5;
            zeroCount+=n;
        }
        return zeroCount;
    }

    // https://leetcode.com/problems/reverse-integer/
    public int reverseInteger(int x) {
        boolean neg = x < 0 ? true : false;
        int res = 0;
        x = Math.abs(x);
        int max = Integer.MAX_VALUE;

        while(x > 0){
            if(res > max/10 || res * 10 > (max - x%10)){
                return 0;
            }
             res = res*10 + x%10;
            x = x/10;
        }

        return neg ? (-1) * res : res;
    }

    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> map = new HashMap<>();
        Stack<Integer> stack = new Stack<>();

        for(int i =0; i< nums2.length; i++){
            while(!stack.isEmpty() && nums2[i] > stack.peek()){
                map.put(stack.pop(), nums2[i]);
            }
            stack.push(nums2[i]);
        }

        while(!stack.isEmpty()){
            map.put(stack.pop(), -1);
        }
        int[] res = new int[nums1.length];
        for(int i = 0; i< nums1.length; i++){
            res[i] = map.get(nums1[i]);
        }

        return res;
    }

    // https://leetcode.com/problems/palindrome-number/
    public boolean isPalindrome(int x) {
        if(x<0 || x!=0 && x%10==0) return false;

        int res = 0;
        while(x > res){
            res = res * 10 + x%10;
            x/= 10;
        }

        if(res == x || x == res/10) return true;
        else return false;
    }

    // https://leetcode.com/problems/missing-number/
    public int missingNumber(int[] nums) {
        int len = nums.length;

        int total = len*(len+1)/2;
        int sum = 0;
        for(int i : nums){
            sum+= i;
        }
        return total - sum;
    }

    // https://leetcode.com/problems/happy-number/description/
    public boolean happyNumber(int num){
        Set<Integer> seen = new HashSet<>();

        while(num != 0 && !seen.contains(num)){
            seen.add(num);

            int sum = 0;
            while(num != 0){
                int digit = num%10;
                sum += digit * digit;
                num = num/10;
            }
            num = sum;
        }

        return num == 1;

    }

    // https://leetcode.com/problems/plus-one/description/
    public int[] plusOne(int[] digits) {
        for(int i = digits.length-1; i >=0; i--){
            if(digits[i] < 9){
                digits[i]++;
                return digits;
            }
            digits[i] = 0;
        }

        int nums[] = new int[digits.length+1];
        nums[0] = 1;
        return nums;
    }

    public double myPow(double x, int n) {
        if(n == 0) return 1;
        if(x == 0) return 0;

        if (x < 0){
            return 1/x * myPow(1/x, -(n+1));
        }

        return (n%2 == 0) ? myPow(x*x, n/2) : x * myPow(x*x, n/2);
    }

    // https://leetcode.com/problems/detect-squares/
    class DetectSquares {
        List<int[]> coordinates;
        Map<String, Integer> counts;

        public DetectSquares() {
            coordinates = new ArrayList<>();
            counts = new HashMap<>();
        }

        public void add(int[] point) {
            coordinates.add(point);
            String key = point[0] + "@" + point[1];
            counts.put(key, counts.getOrDefault(key, 0) + 1);

        }

        public int count(int[] point) {
            int sum = 0, px = point[0], py = point[1];
            for (int[] coordinate : coordinates) {
                int x = coordinate[0];
                int y = coordinate[1];
                if (px == x || py == y || (Math.abs(px - x) != Math.abs(py - y)))
                    continue;
                sum += counts.getOrDefault(x + "@" + py, 0) * counts.getOrDefault(px + "@" + y, 0);
            }
            return sum;
        }

        // https://leetcode.com/problems/pascals-triangle-ii/
        public List<Integer> pascalTriangle(int rowIndex) {
            List<Integer> firstRow = new ArrayList<>();
            firstRow.add(1);

            for (int i = 1; i <= rowIndex; i++) {
                for (int j = i-1; j >= 1; j--) {
                    int tmp = firstRow.get(j) + firstRow.get(j-1);
                    firstRow.set(j, tmp);
                }
                firstRow.add(1);
            }

            return firstRow;
        }

        // https://leetcode.com/problems/pascals-triangle/
        // kind of DP (N^2)
        public List<List<Integer>> generatePascalsTriangle(int numRows) {
            List<List<Integer>> allRows = new ArrayList<>();
            List<Integer> row = new ArrayList<>();
            for(int i =0; i<numRows; i++){
                row.add(0, 1);

                for(int j =1; j< row.size()-1; j++){
                    row.set(j, row.get(j) + row.get(j+1));
                }
                allRows.add(new ArrayList<Integer>(row));
            }
            return allRows;
        }

        // https://leetcode.com/problems/plus-one/
        public static int[] plusOne(int[] digits){
            int n = digits.length;

            for (int i = n-1; i >=0 ; i--) {
                if(digits[i] < 9){
                    digits[i]++;
                    return digits;
                }
                digits[i] = 0;
            }

            int[] newNumber = new int[n+1];
            newNumber[0] =1;
            return newNumber;
        }

        // https://leetcode.com/problems/squares-of-a-sorted-array/
        public static int[] sortedSquares(int[] nums){
            int n = nums.length;
            int[] res = new int[n];
            int i = 0, j = n-1;

            for (int p = n-1; p >=0 ; p--) {
                if(Math.abs(nums[i]) > Math.abs(nums[j])){
                    res[p] = nums[i] * nums[i];
                    i++;
                }
                else{
                    res[p] = nums[j] * nums[j];
                    j--;
                }
            }
            return res;
        }

        private static int HALF_INT_MIN = -1073741824;
        // https://leetcode.com/problems/divide-two-integers
        public int divide(int dividend, int divisor) {
            if(dividend == Integer.MIN_VALUE && divisor == -1){
                return Integer.MAX_VALUE;
            }
            int negs = 2;
            if(dividend > 0){
                negs--;
                dividend = -dividend;
            }

            if(divisor > 0){
                negs--;
                divisor = -divisor;
            }

            int quot = 0;
            while(divisor >= dividend){
                int power = -1;
                int value = divisor;

                while(value >= HALF_INT_MIN && value+value >= dividend){
                    value += value;
                    power += power;
                }
                quot += power;
                dividend -= value;
            }
            if(negs != 1){
                return -quot;
            }
            return  quot;
        }
    }

    // https://leetcode.com/problems/angle-between-hands-of-a-clock
    public double angleClock(int hour, int mins) {
        double hrDegree = (60 * hour + mins) * .5;
        double minsDegree = 6 * mins;

        double res = Math.abs(hrDegree - minsDegree);
        return Math.min(res, 360-res);
    }

    //        x3,y2          x4,y2
    //               |--------------|
    //     |--------------|
    //     x1,y1         x2,y1
    //
    //     x1 < x3 < x2  && x3 < x2 < x4
    // or in short x1 < x4 && x3 < x2
    // https://leetcode.com/problems/rectangle-overlap/
    public static boolean isRectangleOverlap(int[] rec1, int[] rec2){
        int x1 = rec1[0], x2 = rec1[2], y1 = rec1[1], y2 = rec1[3];
        int x3 = rec2[0], x4 = rec2[2], y3 = rec2[1], y4 = rec2[3];

        // x1 < x4 && x3 < x2 for rectangle to overlap
        // same for y axis

        return (x1 < x4 && x3 < x2 && y1 < y4 && y3 < y2 && x1 != x2 && x3 != x4 && y1 != y2 && y3 != y4); // these != checks are added to ensure rectangle has +ve area
    }

    public String excelColumnToTitle(int n) {
        StringBuilder result = new StringBuilder();

        while(n>0){
            n--;
            result.insert(0, (char)('A' + n % 26));
            n /= 26;
        }

        return result.toString();
    }

    // https://leetcode.com/problems/excel-sheet-column-number/description/
    public static int excelSheetColumn(String s){
        if(s == null || s.length() == 0 ) return 0;

        int ans = 0; int p = 0;

        for (int i = s.length()-1; i >=0; i--) {
            int val = s.charAt(i) - 'A' + 1;
            ans += val * Math.pow(26, p);
            p++;
        }

        return ans;
    }
}