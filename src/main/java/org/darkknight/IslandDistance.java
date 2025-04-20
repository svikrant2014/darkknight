package org.darkknight;
import java.util.ArrayDeque;
import java.util.Queue;

public class IslandDistance {
    public static void main(String[] args) {
        int[][] grid = { { 1, 0, 0, 0, 1 },
                         { 1, 1, 0, 0, 0 },
                         { 0, 0, 0, 0, 0 },
                         { 0, 0, 1, 1, 1 } };
        closestDistance(grid);
    }

    static int row;
    static int col;
    static class Pair {
        int x;
        int y;
        int identity;
        Pair(int x, int y, int identity)
        {
            this.x = x;
            this.y = y;
            this.identity = identity;
        }
    }

    // Function to find closest distance
    static void closestDistance(int[][] grid)
    {
        row = grid.length;
        col = grid[0].length;

        int id = 1;
        Queue<Pair> q = new ArrayDeque<Pair>();
        int[][] visited = new int[row][col];

        // Distance array to store distance
        // From nearest island
        int[][] distance = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == 1
                        && visited[i][j] == 0) {
                    dfs(grid, visited, q, i, j, id);
                    id++;
                }
            }
        }

        // To store minimal distance
        // b/w closest islands
        int ans = bfs(grid, visited, distance, q);
        System.out.println(ans);
    }

    static int[] dirx = { 0, 1, 0, -1 };
    static int[] diry = { 1, 0, -1, 0 };

    // Dfs function to add all island elements
    // In queue and marking them visited with id
    static void dfs(int[][] grid, int[][] visited,
                    Queue<Pair> q, int i,
                    int j, int id)
    {
        visited[i][j] = id;
        q.add(new Pair(i, j, id));
        for (int idx = 0; idx < 4; idx++) {
            int x = i + dirx[idx];
            int y = j + diry[idx];
            if (isValid(x, y) && grid[x][y] == 1
                    && visited[x][y] == 0) {
                dfs(grid, visited, q, x, y, id);
            }
        }
    }

    // Bfs function to expand every island and
    // Maintaining distance array
    static int bfs(int[][] grid, int[][] visited,
                   int[][] distance, Queue<Pair> q)
    {
        while (q.size() != 0) {
            Pair p = q.remove();
            for (int i = 0; i < 4; i++) {
                int x = p.x + dirx[i];
                int y = p.y + diry[i];
                if (isValid(x, y)
                        && visited[x][y] == 0) {
                    q.add(new Pair(x, y,
                            p.identity));
                    distance[x][y]
                            = distance[p.x][p.y] + 1;
                    visited[x][y]
                            = p.identity;
                }
                else if (isValid(x, y)
                        && visited[x][y] != 0
                        && visited[x][y]
                        != visited[p.x][p.y]) {
                    return distance[x][y]
                            + distance[p.x][p.y];
                }
            }
        }
        return -1;
    }

    // Function to check if point
    // Is inside grid
    static boolean isValid(int x, int y)
    {
        if (x < 0 || x >= row
                || y < 0 || y >= col)
            return false;
        return true;
    }
}
