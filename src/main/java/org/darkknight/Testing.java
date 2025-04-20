package org.darkknight;

import java.util.*;

public class Testing {

    public static void main(String[] args) {
        Testing test = new Testing();
        // var rooms = test.meetingRooms(new int[][]{{0,30}, {5,10}, {15,20}});
        // var rooms = test.meetingRooms(new int[][]{{0,30}});
        // var rooms = test.meetingRooms(new int[][]{{29,40}, {40,45}});

        var val = test.myPow(2,-4);
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        int[] indegrees = new int[numCourses];

        for(int[] arr : prerequisites){
            map.computeIfAbsent(arr[1], ele -> new ArrayList<>()).add(arr[0]);
            ++indegrees[arr[0]];
        }

        Queue<Integer> queue = new LinkedList<>();
        for(int degree : indegrees){
            if(indegrees[degree] == 0){
                queue.offer(degree);
            }
        }

        while (!queue.isEmpty()){
            int size = queue.size();

            for(int i=0; i<size; i++){
                int curr = queue.remove();
                --numCourses;
                if(!map.containsKey(curr)) continue;

                for(int course : map.remove(curr)){
                    --indegrees[course];
                    if(indegrees[course] == 0) queue.offer(course);
                }
            }
        }

        return numCourses  == 0 ? true : false;
    }

    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        List<List<Integer>> res = new ArrayList<>();
        if(heights.length == 0 || heights[0].length == 0) return res;
        int m = heights.length;
        int n = heights[0].length;

        boolean[][] pacific = new boolean[m][n];
        boolean[][] atlantic = new boolean[m][n];

        for(int i =0; i<m; i++){
            dfs(heights, pacific, Integer.MIN_VALUE, i, 0);
            dfs(heights, atlantic, Integer.MIN_VALUE, i, m-1);
        }

        for(int i =0; i<n; i++){
            dfs(heights, pacific, Integer.MIN_VALUE, 0, i);
            dfs(heights, pacific, Integer.MIN_VALUE, n-1, i);
        }

        for(int i =0; i<m; i++){
            for(int j =0; j<n; j++){
                if(pacific[i][j] && atlantic[i][j]){
                    res.add(List.of(i, j));
                }
            }
        }
        return res;
    }

    int[][]dirs = new int[][]{{0,1},{0,-1},{1,0},{-1,0}};
    private void dfs(int[][] mat, boolean[][] visited,int height, int row, int col){
        if(row<0 || row >= mat.length || col < 0 || col >= mat[0].length || visited[row][col] || mat[row][col] < height){
            return;
        }
        visited[row][col] = true;
        for(int[] dir : dirs){
            dfs(mat, visited, mat[row][col], row+dir[0], col+dir[1]);
        }
    }

    private double myPow(double x, int n){
        if(x == 0) return 0;
        if(n == 0) return 1;
        double res = 1;
        long absN = Math.abs((long)n);
        if(n < 0) {
            x = 1/x;
        }

        while(absN > 0){
            if(absN % 2 == 1){
                res = res * x;
            }

            x*= x;
            absN = absN>>1;
        }
        return res;
    }
}
