package org.darkknight.Meta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MissingRanges {

    // https://leetcode.com/problems/missing-ranges
    public List<List<Integer>> findMissingRanges(int[] nums, int lower, int upper) {
        List<List<Integer>> res = new ArrayList<>();

        int lo = lower;
        for(int i : nums){
            if(i > lo){
                res.add(Arrays.asList(lo, i-1));
            }
            lo = i+1;
        }

        if(upper >= lo) res.add(Arrays.asList(lo, upper));
        return res;
    }


    // #meta ... here we have to custom format the output ... that's the only difference
    // missing-ranges-meta
    public static List<String> missingRangesMeta(int[] nums, int lower, int upper) {
        List<String> result = new ArrayList<>();
        int prev = lower - 1;  // Start before the range

        for (int i = 0; i <= nums.length; i++) {
            int curr = (i < nums.length) ? nums[i] : upper + 1; // Extend beyond upper for final gap

            if (curr - prev > 1) {
                result.add(formatRange(prev + 1, curr - 1));
            }
            prev = curr; // Move to the next element
        }

        return result;
    }

    private static String formatRange(int start, int end) {
        if (start == end) return String.valueOf(start);
        if (start + 1 == end) return start + "," + end;
        return start + "-" + end;
    }


}
