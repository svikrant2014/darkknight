package org.darkknight.Meta;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

interface NestedInteger {
    // @return true if this NestedInteger holds a single integer, rather than a nested list.
    public boolean isInteger();

    // @return the single integer that this NestedInteger holds, if it holds a single integer
    // Return null if this NestedInteger holds a nested list
    public Integer getInteger();

    // Set this NestedInteger to hold a single integer.
    public void setInteger(int value);

    // Set this NestedInteger to hold a nested list and adds a nested integer to it.
    public void add(NestedInteger ni);

    // @return the nested list that this NestedInteger holds, if it holds a nested list
    // Return empty list if this NestedInteger holds a single integer
    public List<NestedInteger> getList();
}
public class NestedList {
    public int depthSum(List<NestedInteger> nestedList) {

        return bfs(nestedList);
        // return dfs(nestedList,1);
    }

    // https://leetcode.com/problems/nested-list-weight-sum
    private int bfs(List<NestedInteger> nestedList){
        if(nestedList.size() == 0) return 0;

        Queue<NestedInteger> queue = new LinkedList<>();
        queue.addAll(nestedList);
        int depth =1;
        int total =0;
        while(!queue.isEmpty()){
            int size = queue.size();

            for(int i=0; i<size; i++){
                NestedInteger curr = queue.poll();

                if(curr.isInteger()){
                    total += curr.getInteger()*depth;
                }else{
                    queue.addAll(curr.getList());
                }
            }
            depth++;
        }

        return total;
    }

//    private int dfs(List<NestedInteger> nestedList, int depth){
//        if(nestedList.size() == 0) return 0;
//
//        int total = 0;
//        for(NestedInteger val : nestedList){
//            if(val.isInteger())
//                total += val.getInteger()depth;
//            else{
//                total += dfs(val.getList(), depth+1);
//            }
//        }
//        return total;
//    }

    // https://leetcode.com/problems/nested-list-weight-sum-ii/
    // just keep adding the current levelSum in a repeated manner
    public int depthSumInverse(List<NestedInteger> list) {
        if(list == null || list.size() == 0) return 0;

        Queue<List<NestedInteger>> queue = new LinkedList<>();
        queue.add(list);
        int singleSum = 0 ;
        int sum = 0;

        while(!queue.isEmpty()){
            int size = queue.size();

            for(int i=0; i<size; i++){
                List<NestedInteger> curr = queue.poll();

                for(NestedInteger ni : curr){
                    if(ni.isInteger()) singleSum += ni.getInteger();
                    else{
                        queue.add(ni.getList());
                    }
                }
            }
            sum += singleSum;
        }

        return sum;
    }
}
