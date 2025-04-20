package org.darkknight.Meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// there are some variations of it too ... check the comments
public class SparseVector {

    List<int[]> pairs;

    // O(N+M)
    // https://leetcode.com/problems/dot-product-of-two-sparse-vectors
    SparseVector(int[] nums) {
        pairs = new ArrayList<>();
        for(int i =0; i<nums.length; i++){
            if(nums[i] != 0){
                pairs.add(new int[] {i, nums[i]});
            }
        }

        // for map version
        mapping = new HashMap<>();
        for (int i = 0; i < nums.length; ++i) {
            if (nums[i] != 0) {
                mapping.put(i, nums[i]);
            }
        }
    }

    // Return the dotProduct of two sparse vectors
    public int dotProduct(SparseVector vec) {
        int res = 0, p=0, q=0;

        while(p < pairs.size() && q < vec.pairs.size()){
            if(pairs.get(p)[0] == vec.pairs.get(q)[0]){
                res += pairs.get(p)[1] * vec.pairs.get(q)[1];
                p++;
                q++;
            }
            else if (pairs.get(p)[0] > vec.pairs.get(q)[0]){
                q++;
            }
            else{
                p++;
            }
        }
        return res;
    }

    // Map the index to value for all non-zero values in the vector
    Map<Integer, Integer> mapping;


    // you can make it better using min length of map keySet() and traversing that one
    //  min(v1.keySet().length, v2.keySet().length)
    public int dotProductMapVersion(SparseVector vec) {
        int result = 0;

        // iterate through each non-zero element in this sparse vector
        // update the dot product if the corresponding index has a non-zero value in the other vector
        for (Integer i : this.mapping.keySet()) {
            if (vec.mapping.containsKey(i)) {
                result += this.mapping.get(i) * vec.mapping.get(i);
            }
        }
        return result;
    }
}
