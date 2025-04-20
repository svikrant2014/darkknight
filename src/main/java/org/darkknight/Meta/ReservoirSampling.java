package org.darkknight.Meta;

import java.util.*;
import java.util.Random;

public class ReservoirSampling {

    private int[] nums;

    // https://leetcode.com/problems/random-pick-index
    // #meta
    public ReservoirSampling(int[] nums) {
        this.nums = nums;
    }

    public int pick(int target) {
        Random rand = new Random();
        int n = this.nums.length;
        int count = 0;
        int idx = 0;
        for (int i = 0; i < n; ++i) {
            // if nums[i] is equal to target, i is a potential candidate
            // which needs to be chosen uniformly at random
            if (this.nums[i] == target) {
                // increment the count of total candidates
                // available to be chosen uniformly at random
                count++;
                // we pick the current number with probability 1 / count (reservoir sampling)
                if (rand.nextInt(count) == 0) {
                    idx = i;
                }
            }
        }
        return idx;
    }

    private HashMap<Integer, List<Integer>> indices;
    private Random rand;

    public void preCompute(int[] nums) {
        this.rand = new Random();
        this.indices = new HashMap<Integer, List<Integer>>();
        int l = nums.length;
        for (int i = 0; i < l; ++i) {
            if (!this.indices.containsKey(nums[i])) {
                this.indices.put(nums[i], new ArrayList<>());
            }
            this.indices.get(nums[i]).add(i);
        }
    }

    // from pre-compute
    public int pickII(int target) {
        int l = indices.get(target).size();
        // pick an index at random
        int randomIndex = indices.get(target).get(rand.nextInt(l));
        return randomIndex;
    }


    // // VARIANT: What if you had to use reservoir sampling to randomly sample K numbers without replacement probability?
    public int[] randomPickIndicesFirstVariant(int[] nums, int k) {
        int[] result = new int[k];
        Random rand = new Random();

        // Step 1: Fill the result array with the first k elements
        for (int i = 0; i < k; i++) {
            result[i] = nums[i];
        }

        // Step 2: Reservoir Sampling for remaining elements
        for (int i = k; i < nums.length; i++) {
            int n = i + 1;  // Current number of seen elements
            int r = rand.nextInt(n);  // Random index from 0 to n-1

            if (r < k) { // With probability k/n, replace an element in the result
                result[r] = nums[i];
            }
        }

        return result;
    }

    // // VARIANT: What if you had to use reservoir sampling to pick an index of the maximum value in the array?
    public static int randomPickIndexSecondVariant(int[] nums) {
        int maxNumber = Integer.MIN_VALUE;
        int pickedIndex = -1;
        int count = 0;
        Random rand = new Random();

        for (int i = 0; i < nums.length; i++) {
            int currNum = nums[i];

            if (currNum < maxNumber) {
                continue;
            }

            if (currNum > maxNumber) {
                maxNumber = currNum;
                pickedIndex = i;
                count = 1;
            } else if (currNum == maxNumber) {
                count++;
                int r = rand.nextInt(count);  // Generates a number in range [0, count-1]
                if (r == 0) {
                    pickedIndex = i;
                }
            }
        }
        return pickedIndex;
    }
}
