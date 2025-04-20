package org.darkknight;

import java.util.*;

public class Greedy {
    public static void main(String[] args) {
        Greedy test = new Greedy();
        // [[1,6],[2,8],[7,12],[10,16]]
        test.findMinArrowShots(new int[][]{{1,2}, {2,3}, {3,4}, {4,5}});
        test.maxProfitIII(new int[]{3,3,5,0,0,3,1,4});
    }

    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii
    public int maxProfitIII(int[] arr){
        if(arr.length == 0) return 0;
        int[] left = new int[arr.length];
        int[] right = new int[arr.length];

        int minSoFar = arr[0];
        for(int i =1; i<arr.length; i++){
            minSoFar = Math.min(minSoFar, arr[i]);
            left[i] = Math.max(left[i-1], arr[i] - minSoFar);
        }

        int maxSoFar = arr[arr.length-1];
        for(int i = arr.length-2; i >=0; i--){
            maxSoFar = Math.max(maxSoFar, arr[i]);
            right[i] = Math.max(right[i+1], maxSoFar - arr[i]);
        }

        int maxProfit = 0;
        for(int i=0; i<arr.length; i++){
            maxProfit = Math.max(maxProfit, left[i]+right[i]);
        }
        return maxProfit;
    }


    public int maxProfitIIIAlternate(int[] prices){
        if(prices.length == 0) return 0;

        int t1Min = Integer.MAX_VALUE;
        int t1Profit = 0;

        int t2Min = Integer.MAX_VALUE;
        int t2Profit = 0;

        for(int price : prices){
            t1Min = Math.min(t1Min, price);
            t1Profit = Math.max(t1Profit, price - t1Min);

            // use t1Profit to reduce cost in 2nd transaction
            t2Min = Math.min(t2Min, price - t1Profit);
            t2Profit = Math.max(t2Profit, price - t2Min);
        }
        return t2Profit;
    }

    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv
    // based on above alternate solution for III
    public int maxProfit4(int k, int[] prices) {
        if(k == 0 || prices.length == 0) return 0;

        int[] profit = new int[k+1];
        int[] minSoFar = new int[k+1];
        Arrays.fill(minSoFar, Integer.MAX_VALUE);

        for(int price : prices){
            for(int i =0; i<k; i++){
                minSoFar[i+1] = Math.min(minSoFar[i+1], price - profit[i]);
                profit[i+1] = Math.max(profit[i+1], price- minSoFar[i+1]);
            }
        }
        return profit[k];
    }

    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock
    public int maxProfit(int[] arr){
        if(arr.length == 0) return 0;

        int maxProfit = 0;
        int minSoFar = Integer.MAX_VALUE;

        for(int i =0; i<arr.length; i++){
            minSoFar = Math.min(minSoFar, arr[i]);

            if(arr[i] > minSoFar){
                maxProfit += arr[i] - minSoFar;
                minSoFar = arr[i];
            }
        }
        return maxProfit;
    }

    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/
    // you can buy and sell n number of times
    public int maxProfitII(int[] prices) {
        int curr = prices[0];
        int profit = 0;
        for(int i=1; i<prices.length; i++){
            if(prices[i] > curr){
                profit = profit+(prices[i]-curr);
                curr = prices[i];
            }else{
                curr  = Math.min(curr, prices[i]);
            }
        }
        return profit;
    }

    // similiar to buyandsell but asked this way in #meta
    // VARIANT: What if you had to return the minimum cost to buy a roundtrip flight?
    // Note you cannot fly somewhere and back on the same day.
    // i	Action	minDepartureCost	returns[i] + minDepartureCost	minCost
    //1	returns[1]=150 + minDepartureCost=100 → 250	min(100, 80)=80	250	250
    //2	returns[2]=100 + minDepartureCost=80 → 180	min(80, 70)=70	180	180
    //3	returns[3]=80 + minDepartureCost=70 → 150	min(70, 90)=70	150	150
    public static int findCheapestTickets(int[] departures, int[] returns) {
        if (departures == null || returns == null || departures.length == 0 || returns.length == 0) {
            return Integer.MAX_VALUE; // Handle edge cases
        }

        int minDepartureCost = departures[0];
        int minCost = Integer.MAX_VALUE;

        for (int i = 1; i < departures.length; i++) {
            minCost = Math.min(minCost, returns[i] + minDepartureCost);
            minDepartureCost = Math.min(minDepartureCost, departures[i]);
        }

        return minCost;
    }

    // https://leetcode.com/problems/find-the-celebrity
    // that graph input is totally stupid MF
    public int findCelebrity(int n) {
        int candidate = 0;

        // The first loop is to find the candidate as the author explains. In detail, suppose the candidate after the first for
        // loop is person k,
        // it means 0 to k-1 cannot be the celebrity, because they know a previous or current candidate.
        // Also, since k knows no one between k+1 and n-1, k+1 to n-1 can not be the celebrity either.
        // Therefore, k is the only possible celebrity, if there exists one.
        for(int i =1; i<n; i++){
            if(knows(candidate, i)){
                candidate = i;
            }
        }

        // just check once again from beginning, we could avoid up to k-1 though
        for(int i =0; i<n; i++){
            if(i == candidate) continue;
            if(knows(candidate, i) || !knows(i, candidate)){
                return -1;
            }
        }
        return candidate;
    }

    // just symbolic to avoid build error
    private boolean knows(int a, int b){
        return true;
    }

    // https://leetcode.com/problems/gas-station/
    // gasStation
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int totalCost = 0;
        int totalGas = 0;

        int tank = 0;
        int startIndex = 0;
        for(int i = 0; i< gas.length; i++){
            int current = gas[i] - cost[i];
            totalCost += cost[i];
            totalGas += gas[i];

            tank += current;

            if(tank < 0){
                tank = 0;
                startIndex = i+1;
            }
        }

        if(totalGas >= totalCost) return startIndex;
        else return -1;
    }

    private boolean carpool(int[][] trips, int capacity){
        Arrays.sort(trips, (a, b) -> a[1] - b[1]);

        PriorityQueue<int[]> queue = new PriorityQueue<>(((a, b) -> a[2] - b[2]));

        int totalPass = 0;
        for(int i=1; i< trips.length; i++){
            int[] temp = trips[i];
            while(!queue.isEmpty() && queue.peek()[2] <= temp[1]){
                totalPass-= queue.poll()[0];
            }

            totalPass += temp[0];
            queue.offer(temp);
            if(totalPass > capacity) return false;
        }
        return true;
    }

    // https://leetcode.com/problems/jump-game/
    //   // idea is that you are standing at a index, and check if this index plus the value it provides can add and take you to the current last index
    //  // for e.g. of currLastIndex =4, and you are at i=3, and arr[i] =1... it means 3+1 >=4 which means you can still reach the next index
    public boolean jumpGame(int[] nums){
        int lastGoodIndex = nums.length-1;

        for(int i = nums.length-2; i>=0; i--){
            if(i + nums[i] >= lastGoodIndex){
                lastGoodIndex = i;
            }
        }
        return lastGoodIndex == 0;
    }

    // above was just checking, now we need to choose the min path
    // https://leetcode.com/problems/jump-game-ii/
    // ladder and stairs e.g. https://www.youtube.com/watch?v=vBdo7wtwlXs
    public int jumpGameII(int[] nums) {
        if(nums.length <= 1) return 0;

        int ladder = nums[0];
        int stairs = nums[0];
        int jumps = 1;

        for(int i=1; i<nums.length; i++){
            if(i == nums.length-1) return jumps;

            if(i + nums[i] > ladder){
                ladder = i + nums[i];
            }

            stairs--;
            if(stairs == 0){
                jumps++;
                stairs = ladder-i;
            }
        }
        return jumps;
    }

    // check if you can reach any index with value 0
    // https://leetcode.com/problems/jump-game-iii
    public boolean jumpGameIII(int[] arr, int start) {
        if(arr.length == 0) return true;
        return checkDFS(arr, start, new HashSet<>());
    }

    private boolean checkDFS(int[] arr, int start, HashSet<Integer> seen){
        if(start < 0 || start >= arr.length || seen.contains(start)) return false;
        if(arr[start] == 0) return true;

        seen.add(start);
        return checkDFS(arr, start+arr[start], seen) || checkDFS(arr, start-arr[start], seen);
    }

    // https://leetcode.com/problems/jump-game-iv/
    // https://leetcode.com/problems/jump-game-iv/solutions/1690813/best-explanation-ever-possible-for-this-question/
    public int jumpGameIV(int[] arr) {
        if(arr.length <= 1) return 0;
        // create your adjacency matrix
        Map<Integer, List<Integer>> map = new HashMap<>();
        int step =0;

        for(int i =0; i<arr.length; i++){
            map.computeIfAbsent(arr[i], v -> new ArrayList<>()).add(i);
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);

        while(!queue.isEmpty()){
            step++;
            int size = queue.size();

            for(int i =0; i<size; i++){
                int curr = queue.poll();

                if(curr-1 >= 0 && map.containsKey(arr[curr-1])){
                    queue.offer(curr-1);
                }

                if(curr+1 < arr.length && map.containsKey(arr[curr+1])){
                    if(curr+1 == arr.length-1) return step;
                    queue.offer(curr+1);
                }

                if(map.containsKey(arr[curr])){
                    for(int k : map.get(arr[curr])){
                        if(k != curr){
                            if(k == arr.length-1) return step;
                            queue.offer(k);
                        }
                    }
                }
                map.remove(arr[curr]);
            }
        }
        return step;
    }

    // [[1,6],[2,8],[7,12],[10,16]]
    // https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/
    public int findMinArrowShots(int[][] points) {
        int len = points.length;
        if(len <= 1) return len;

        // Arrays.sort(points, (int[] a, int[] b) -> a[1]-b[1]);
        // just one test case for overflow, need modified comparator
        Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));

        int currEnd = points[0][1];
        int arrows = 1;
        for(int i =1; i<points.length; i++){
            int[] curr = points[i];
            if(curr[0] <= currEnd){
                continue;
            }else{
                arrows++;
                currEnd = points[i][1];
            }
        }
        return arrows;
    }
}
