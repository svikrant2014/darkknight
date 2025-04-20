package org.darkknight;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IslandProbs {
    public static void main(String[] args) {

    }

    // idea is to pre-compute the area if all islands and mark them with some unique val starting with 2
    // and later check in all dirs for a '0' if summing up the areas finds a better result
    // o(nxm), o(nxm)
    // https://leetcode.com/problems/making-a-large-island
    public int largestIsland(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;

        Map<Integer, Integer> regionsArea = new HashMap<>();
        regionsArea.put(0, 0);

        int region = 2;

        for(int i=0; i<row; i++){
            for(int j =0; j<col; j++){
                if(grid[i][j] == 1){
                    int area = floodFill(grid, i, j, region);
                    regionsArea.put(region, area);
                    region++;
                }
            }
        }

        //If there is no island 0 from grid, res should be the size of islands of first color
        //If there is no island 1 from grid, res should be 0
        int maxSoFar = regionsArea.getOrDefault(2, 0);

        for(int i =0; i< row; i++){
            for(int j=0; j<col; j++){
                if(grid[i][j] == 0){
                    //We use a set to avoid repeatedly adding islands with the same color
                    Set<Integer> neighbors = new HashSet<>();
                    neighbors.add(i > 0 ? grid[i-1][j] : 0);
                    neighbors.add(j > 0 ? grid[i][j-1] : 0);
                    neighbors.add(i < row-1 ? grid[i+1][j] : 0);
                    neighbors.add(j < col-1 ? grid[i][j+1] : 0);

                    int area = 1;

                    for(int neighbor : neighbors){
                        area += regionsArea.get(neighbor);
                    }

                    maxSoFar = Math.max(maxSoFar, area);
                }
            }
        }

        return maxSoFar;
    }

    //Helper method to paint current island and all its connected neighbors
    //Return the size of all painted islands at the end
    public int floodFill(int[][] grid, int r, int c, int region) {
        int n = grid.length;
        if(r<0||r>=n||c<0||c>=n||grid[r][c]!=1){
            return 0;
        }

        grid[r][c] = region;

        int sum = 1;
        sum+=floodFill(grid, r, c+1, region);
        sum+=floodFill(grid, r+1, c, region);
        sum+=floodFill(grid, r, c-1, region);
        sum+=floodFill(grid, r-1, c, region);

        return sum;
    }
}
