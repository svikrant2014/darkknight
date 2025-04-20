package org.darkknight.Meta;

import java.util.*;

public class MetaVariants {
    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode next;
        TreeNode() {
        }
        TreeNode(int val) {
            this.val = val;
        }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    
    // when it got different brackets
    public static String minRemoveToMakeValid(String s) {
        Stack<Integer> stackRound = new Stack<>();
        Stack<Integer> stackCurly = new Stack<>();
        Stack<Integer> stackSquare = new Stack<>();
        Set<Integer> toRemove = new HashSet<>();

        // First pass: Identify invalid parentheses
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stackRound.push(i);
            } else if (c == ')') {
                if (stackRound.isEmpty()) {
                    toRemove.add(i);
                } else {
                    stackRound.pop();
                }
            } else if (c == '{') {
                stackCurly.push(i);
            } else if (c == '}') {
                if (stackCurly.isEmpty()) {
                    toRemove.add(i);
                } else {
                    stackCurly.pop();
                }
            } else if (c == '[') {
                stackSquare.push(i);
            } else if (c == ']') {
                if (stackSquare.isEmpty()) {
                    toRemove.add(i);
                } else {
                    stackSquare.pop();
                }
            }
        }

        // Add remaining unbalanced opening brackets to removal set
        toRemove.addAll(stackRound);
        toRemove.addAll(stackCurly);
        toRemove.addAll(stackSquare);

        // Second pass: Build the valid string
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (!toRemove.contains(i)) {
                result.append(s.charAt(i));
            }
        }

        return result.toString();
    }

    // they give 2 string, first one is already good, sec one need to be fixed
    // and then apply on first one and then return ans
    // simple -> if second is empty return first one as is
    // if sec starts with /, first one becomes empty else push first one to stack and then apply rest logic
    public String simplifyPathMeta(String path, String path2) {
        if(path2.isEmpty()) return path;
        if(path2.startsWith("/")) path = "";

        Stack<String> stack = new Stack<>();
        String[] p = path.split("/");
        for (String ch: p){
            stack.push(ch);
        }

        String[] sec = path2.split("/");
        for (String str : sec) {
            if (!stack.isEmpty() && str.equals("..")) {
                stack.pop();
            } else if (!str.equals(".") && !str.equals("") && !str.equals("..")) {
                stack.push(str);
            }
        }

        List<String> res = new ArrayList<>(stack);
        return "/" + String.join("/", res);
    }

    private Map<TreeNode, Integer> subtreeSum = new HashMap<>();

    // Precompute subtree sums
    private int computeSubtreeSum(TreeNode node) {
        if (node == null) return 0;
        int sum = node.val + computeSubtreeSum(node.left) + computeSubtreeSum(node.right);
        subtreeSum.put(node, sum);
        return sum;
    }

    // range-sum-bst-meta
    // VARIANT: What if you had to optimize your solution for 10^4 function invocations?
    public int rangeSumBST(TreeNode root, int low, int high) {
        if (root == null) return 0;

        // If entire subtree is within range, return precomputed sum
        if (root.val >= low && root.val <= high) {
            return subtreeSum.get(root);
        }

        // If the value is too small, search only in the right subtree
        if (root.val < low) {
            return rangeSumBST(root.right, low, high);
        }

        // If the value is too large, search only in the left subtree
        return rangeSumBST(root.left, low, high);
    }

    private int[] prefixSums;
    private String[] cities;
    private int totalPopulation;
    private Random random;

    // random-pick-weight
    // // VARIANT: What if you had to return the city that a person lives in? The input is given very differently.
    public void cityPopulation(String[][] cityPopulations) {
        int n = cityPopulations.length;
        prefixSums = new int[n];
        cities = new String[n];
        random = new Random();

        int sum = 0;
        for (int i = 0; i < n; i++) {
            cities[i] = cityPopulations[i][0]; // Store city names
            sum += Integer.parseInt(cityPopulations[i][1]); // Convert population to integer
            prefixSums[i] = sum; // Store cumulative population
        }
        totalPopulation = sum;
    }

    public String pickIndex() {
        int randPop = random.nextInt(totalPopulation) + 1;
        int cityIndex = binarySearch(randPop);
        return cities[cityIndex];
    }

    private int binarySearch(int target) {
        int left = 0, right = prefixSums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (prefixSums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    // OceanView - when you can see both left and right
    public static List<Integer> OceanViewMeta(int[] heights) {
        int left = 0, right = heights.length - 1;
        int leftMax = Integer.MIN_VALUE, rightMax = Integer.MIN_VALUE;
        Set<Integer> resultSet = new HashSet<>();

        while (left <= right) {
            if (leftMax <= rightMax) {
                // Process left side
                if (heights[left] > leftMax) {
                    resultSet.add(left);
                    leftMax = heights[left];
                }
                left++;
            } else {
                // Process right side
                if (heights[right] > rightMax) {
                    resultSet.add(right);
                    rightMax = heights[right];
                }
                right--;
            }
        }

        // Convert to sorted list
        List<Integer> result = new ArrayList<>(resultSet);
        Collections.sort(result);
        return result;
    }

    // #meta - chars are to be from a given list else exclude everything else
    // valid-palindrome-meta
    public static boolean validPalindromeMeta(String s, char[] include) {
        Set<Character> allowed = new HashSet<>();
        for (char c : include) {
            allowed.add(c);
        }

        int left = 0, right = s.length() - 1;
        while (left < right) {
            // Move left pointer until it finds an allowed character
            while (left < right && !allowed.contains(s.charAt(left))) {
                left++;
            }
            // Move right pointer until it finds an allowed character
            while (left < right && !allowed.contains(s.charAt(right))) {
                right--;
            }

            // Compare characters, ignoring case
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    // #meta ... data is not incoming as a queue ... array is already given
    // simple sliding window
    // moving-average
    public static List<Integer> slidingWindowAverages(int[] nums, int size) {
        List<Integer> result = new ArrayList<>();
        if (nums == null || nums.length == 0 || size <= 0 || size > nums.length) {
            return result; // Return empty if invalid input
        }

        int windowSum = 0;
        // Calculate sum of first 'size' elements
        for (int i = 0; i < size; i++) {
            windowSum += nums[i];
        }
        // Add average of first window
        result.add(windowSum / size);

        // Slide the window across the array
        for (int i = size; i < nums.length; i++) {
            windowSum += nums[i] - nums[i - size];
            result.add(windowSum / size);
        }

        return result;
    }

    // #meta - shortest-Path-Binary-Matrix
    // they want the path, you gotta keep a parent matrix for reconstruction of path
    // saves space - O(N) vs O(N^2)
    // traversal is N^2
    public static List<int[]> shortestClearPath(int[][] grid) {
        int n = grid.length;
        if (n == 0 || grid[0].length == 0 || grid[0][0] == 1 || grid[n-1][n-1] == 1) {
            return Collections.emptyList();
        }

        // 8 possible movement directions
        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };

        // Parent tracking using a HashMap instead of a 2D array
        Map<String, String> parentMap = new HashMap<>();

        // BFS queue
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        parentMap.put("0,0", null); // Mark the starting point

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int r = current[0], c = current[1];

            // If we reached the bottom-right corner, reconstruct the path
            if (r == n - 1 && c == n - 1) {
                return reconstructPath(parentMap, r, c);
            }

            // Explore all 8 directions
            for (int[] d : directions) {
                int nr = r + d[0];
                int nc = c + d[1];

                if (nr >= 0 && nr < n && nc >= 0 && nc < n && grid[nr][nc] == 0) {
                    String newKey = nr + "," + nc;
                    if (!parentMap.containsKey(newKey)) { // Not visited yet
                        parentMap.put(newKey, r + "," + c); // Store parent
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }
        }

        // If no path found, return empty list
        return Collections.emptyList();
    }

    /**
     * Reconstructs the shortest path using the parentMap.
     */
    private static List<int[]> reconstructPath(Map<String, String> parentMap, int endR, int endC) {
        List<int[]> path = new ArrayList<>();
        String currentKey = endR + "," + endC;

        while (currentKey != null) {
            String[] parts = currentKey.split(",");
            path.add(new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])});
            currentKey = parentMap.get(currentKey); // Move to parent
        }

        Collections.reverse(path);
        return path;
    }

    // find any path and not the shortest path
    // DFS will be better for space complexity
    // #meta
    // O(n^2) , space - O(n^2)
    public static List<int[]> findAnyPath(int[][] grid) {
        int n = grid.length;
        if (n == 0 || grid[0].length == 0 || grid[0][0] == 1 || grid[n-1][n-1] == 1) {
            return Collections.emptyList();
        }

        // Stores the path found
        List<int[]> path = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        // Start DFS from (0,0)
        if (dfs(grid, 0, 0, path, visited)) {
            return path; // If we found a path, return it
        } else {
            return Collections.emptyList(); // No valid path found
        }
    }

    private static boolean dfs(int[][] grid, int r, int c, List<int[]> path, Set<String> visited) {
        int n = grid.length;

        // Out of bounds or hitting a blocked cell or already visited cell
        if (r < 0 || c < 0 || r >= n || c >= n || grid[r][c] == 1 || visited.contains(r + "," + c)) {
            return false;
        }

        // Add current cell to path
        path.add(new int[]{r, c});
        visited.add(r + "," + c);

        // If we reached the destination, return true
        if (r == n - 1 && c == n - 1) {
            return true;
        }

        // Possible 8 directions to move
        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };

        // Try all directions
        for (int[] d : directions) {
            if (dfs(grid, r + d[0], c + d[1], path, visited)) {
                return true; // If DFS finds a path, return immediately
            }
        }

        // If no path was found in this route, backtrack
        path.remove(path.size() - 1);
        return false;
    }

    // opposite of https://leetcode.com/problems/next-permutation/
    // idea is to first find a point of pivot, from back which is the first number which is not in ascending order
    // if you can't find one, which means already at the last permutations of that array, we just reverse and return
    // find the first bigger number than pivotElement again searching from back
    // swap those two
    // finally reverse the rest of the array from pivotPoint+1
    // [9 4 8 3 5 5 8 9] -> [9 4 5 3 5 8 8 9] - [9 4 5 9 8 8 5 3]
    // [9 4 5 9 8 8 5 3]
    public void previousPermutation(int[] nums){
        int len = nums.length;
        if(nums.length <=1) return;

        int pivot = len-1; // we will look for ele which is greater than it's next one (look for peak not valley)
        while(pivot>0){
            if(nums[pivot-1] < nums[pivot]) break;
            pivot--;
        }

        if(pivot == 0){
            reverse(nums, 0, len-1);
            return;
        }

        // now again, we will start from end and look for ele which is smaller than pivot
        int pivotVal = nums[pivot-1];
        int j=len-1;
        while(j >= pivot){
            if(nums[j] < pivotVal) break;
            j--;
        }
        swap(nums, j, pivot-1);
        reverse(nums, pivot, len-1);
    }

    private static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
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


    // variant for max swaps to construct largest digit
    // here ... we create second largest and it doesn't say we swap anything
    // idea is to find freq of 0-9 letters how many times they occur
    // -> build largest num
    // -> swap least sig digits from right to get 2nd largest
    public static int[] getSecondLargestNumber(int[] num) {
        if (num == null || num.length <= 1) {
            return new int[0]; // Return empty array if no valid second largest number
        }

        // Frequency array to count occurrences of digits (0-9)
        int[] freqs = new int[10];
        for (int digit : num) {
            freqs[digit]++;
        }

        // Construct the largest number from highest to lowest digit
        List<Integer> largestNum = new ArrayList<>();
        for (int digit = 9; digit >= 0; digit--) {
            for (int count = 0; count < freqs[digit]; count++) {
                largestNum.add(digit);
            }
        }

        // Find the last two distinct digits and swap them
        for (int i = largestNum.size() - 1; i > 0; i--) {
            if (!largestNum.get(i).equals(largestNum.get(i - 1))) {
                Collections.swap(largestNum, i, i - 1);
                return largestNum.stream().mapToInt(Integer::intValue).toArray();
            }
        }

        return new int[0]; // Return empty array if no second largest number exists
    }

    // Given a list of intervals, find any point K that occurs in the maximum number of intervals
    // sweep-line algo
    // could be applied here - https://leetcode.com/problems/divide-intervals-into-minimum-number-of-groups
    // intervals = [[1,4], [2,7], [3,4], [8,11]]
    // events - [(1, +1), (4+1, -1), (2, +1), (7+1, -1), (3, +1), (4+1, -1), (8, +1), (11+1, -1)]
    // then sort and sweep line +1 for start -1 for end
    public static int findMaxOverlapPoint(int[][] intervals) {
        List<int[]> events = new ArrayList<>();

        // Mark the start (+1) and end (-1) events
        for (int[] interval : intervals) {
            events.add(new int[]{interval[0], 1});  // Start of an interval
            events.add(new int[]{interval[1] + 1, -1}); // End of an interval (exclusive)
        }

        // Sort by position; if equal, process end event first
        events.sort((a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);

        int maxCount = 0, currCount = 0, bestPoint = -1;

        // Sweep through sorted events
        for (int[] event : events) {
            currCount += event[1]; // Update active interval count
            if (currCount > maxCount) {
                maxCount = currCount;
                bestPoint = event[0]; // Store the most overlapping point
            }
        }

        return bestPoint;
    }

    // // VARIANT: What if Meta presents the OG problem in the context of PTO days you want to take off?
    //// Specifically, you're given a char array of 'W' for work days and 'H' for weekends.
    public static int longestVacationTimeFirstVariant(char[] days, int pto) {
        int maxVacation = 0;
        int left = 0;

        for (int right = 0; right < days.length; right++) {
            if (days[right] == 'W') {
                pto--; // Use PTO for a workday
            }

            // If PTO goes negative, adjust the window
            while (pto < 0) {
                if (days[left] == 'W') {
                    pto++; // Recover PTO if we move past a workday
                }
                left++; // Shrink window from the left
            }

            maxVacation = Math.max(maxVacation, right - left + 1);
        }

        return maxVacation;
    }

    // if they used boolean array /
    public static int longestVacationTimeSecondVariant(boolean[] year, int pto) {
        int maxVacation = 0;
        int left = 0;

        for (int right = 0; right < year.length; right++) {
            if (!year[right]) { // If it's not a vacation day, use PTO
                pto--;
            }

            // If PTO goes negative, shrink the window
            while (pto < 0) {
                if (!year[left]) { // Reclaim PTO if moving past a non-vacation day
                    pto++;
                }
                left++; // Move window from left
            }

            maxVacation = Math.max(maxVacation, right - left + 1);
        }

        return maxVacation;
    }

    // meta - merge non-overlapping intervals but this time 2 different lists
    // very close to above one - but just merge the 2 lists first to make them one and then sort
    // meta - merge-interval
    public static List<int[]> mergeIntervalsMeta(int[][] A, int[][] B) {
        List<int[]> intervals = new ArrayList<>();
        intervals.addAll(Arrays.asList(A)); // Add first list
        intervals.addAll(Arrays.asList(B)); // Add second list

        // Sort intervals based on the start value
        intervals.sort(Comparator.comparingInt(a -> a[0]));

        List<int[]> merged = new ArrayList<>();
        int[] prev = intervals.get(0);

        for (int i = 1; i < intervals.size(); i++) {
            int[] curr = intervals.get(i);
            if (prev[1] >= curr[0]) { // Overlapping intervals
                prev[1] = Math.max(prev[1], curr[1]);
            } else {
                merged.add(prev);
                prev = curr;
            }
        }
        merged.add(prev); // Add the last merged interval

        return merged;
    }

    // #meta - merge-interval
    // // VARIANT: What if you had to merge two interval lists instead of one?
    public static List<int[]> mergeIntervalsSortedOnes(int[][] A, int[][] B) {
        List<int[]> result = new ArrayList<>();
        int ptrA = 0, ptrB = 0;

        while (ptrA < A.length || ptrB < B.length) {
            int[] interval;

            // If A is exhausted, take from B
            if (ptrA >= A.length) {
                interval = B[ptrB++];
            }
            // If B is exhausted, take from A
            else if (ptrB >= B.length) {
                interval = A[ptrA++];
            }
            // Take the interval with smaller start
            else if (A[ptrA][0] < B[ptrB][0]) {
                interval = A[ptrA++];
            } else {
                interval = B[ptrB++];
            }

            // Merge with last interval in result if overlapping
            if (!result.isEmpty() && result.get(result.size() - 1)[1] >= interval[0]) {
                result.get(result.size() - 1)[1] = Math.max(result.get(result.size() - 1)[1], interval[1]);
            } else {
                result.add(interval);
            }
        }

        return result;
    }

    // https://leetcode.com/problems/group-shifted-strings/
    public List<List<String>> groupShiftedStrings(String[] strings) {
        Map<String, List<String>> mapHashToList = new HashMap<>();

        // Create a hash_value (hashKey) for each string and append the string
        // to the list of hash values i.e. mapHashToList["cd"] = ["acf", "gil", "xzc"]
        for (String str : strings ) {
            String hashKey = getKey(str);
            if (mapHashToList.get(hashKey) == null) {
                mapHashToList.put(hashKey, new ArrayList<>());
            }
            mapHashToList.get(hashKey).add(str);
        }

        // Iterate over the map, and add the values to groups
        List<List<String>> groups = new ArrayList<>();
        for (List<String> group : mapHashToList.values()) {
            groups.add(group);
        }

        // Return a list of all of the grouped strings
        return groups;
    }

    private String getKey(String str){
        StringBuilder key = new StringBuilder();
        for(int i =1; i< str.length(); i++){
            char curr = str.charAt(i);
            char prev = str.charAt(i-1);
            int diff = curr-prev;
            if(diff < 0) diff += 26;
            key.append(diff + "#");
        }

        //key.append(".");
        return key.toString();
    }

    // group-shift string #meta
    // they give a number to shift by ... careful for the numbers
    public static String rotationalCipher(String input, int rotationFactor) {
        if (rotationFactor == 0) {
            return input;
        }

        StringBuilder result = new StringBuilder();

        for (char ch : input.toCharArray()) {
            if (Character.isLowerCase(ch)) {
                result.append((char) ((ch - 'a' + rotationFactor) % 26 + 'a'));
            } else if (Character.isUpperCase(ch)) {
                result.append((char) ((ch - 'A' + rotationFactor) % 26 + 'A'));
            } else if (Character.isDigit(ch)) {
                result.append((char) ((ch - '0' + rotationFactor) % 10 + '0'));
            } else {
                result.append(ch);
            }
        }

        return result.toString();
    }

    // https://leetcode.com/problems/construct-string-from-binary-tree
    public String tree2str(TreeNode t) {
        if (t == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        helper(t, sb);
        return sb.toString();
    }

    private void helper(TreeNode root, StringBuilder sb) {
        sb.append(root.val);
        if (root.left == null && root.right == null) {
            return;
        }
        if (root.left != null) {
            sb.append("(");
            helper(root.left, sb);
            sb.append(")");
        }
        if (root.right != null) {
            if (root.left == null) {
                sb.append("()");
            }
            sb.append("(");
            helper(root.right, sb);
            sb.append(")");
        }
    }

    // https://leetcode.com/problems/check-completeness-of-a-binary-tree
    public boolean isCompleteTree(TreeNode root) {
        boolean end = false;
        Queue<TreeNode> queue = new LinkedList<>();

        queue.offer(root);

        while(!queue.isEmpty()){
            TreeNode curr = queue.poll();
            if(curr == null) end = true;
            else{
                if(end) return false;
                queue.add(curr.left);
                queue.add(curr.right);
            }
        }
        return true;
    }
}
