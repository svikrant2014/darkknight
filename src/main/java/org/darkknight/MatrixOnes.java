package org.darkknight;

import java.util.*;

public class MatrixOnes {
    public static void main(String[] args) {
        MatrixOnes test = new MatrixOnes();
        int[][] mat = {{4,3,5}, {1,2,6}};
        test.firstCompleteIndex(new int[]{1,4,5,2,6,3}, mat);

    }

    // https://leetcode.com/problems/number-of-islands/description/
    public int numIslands(char[][] grid) {
        if(grid.length == 0 || grid[0].length == 0) return 0;
        int count = 0;
        for(int i = 0; i< grid.length; i++){
            for(int j=0; j<grid[i].length; j++){
                if(grid[i][j] == '1'){
                    dfs(i, j, grid);
                    count++;
                }
            }
        }
        return count;
    }

    private void dfs(int i, int j, char[][] grid){
        if(i < 0 || i >= grid.length || j<0 || j >= grid[i].length || grid[i][j] == '0'){
            return;
        }

        grid[i][j] = '0';
        dfs(i + 1, j, grid);
        dfs(i - 1, j, grid);
        dfs(i, j + 1, grid);
        dfs(i, j - 1, grid);
    }

    // https://leetcode.com/problems/max-area-of-island/
    public int maxAreaOfIsland(int[][] grid) {
        if(grid.length == 0 || grid[0].length ==0 ) return 0;
        int maxCount = 0;

        for(int i = 0; i< grid.length; i++){
            for(int j=0; j<grid[i].length; j++){
                if(grid[i][j] == 1){
                    maxCount = Math.max(maxCount, dfsArea(i, j, grid));
                }
            }
        }
        return maxCount;
    }

    private int dfsArea(int i, int j, int[][] grid){
        if(i < 0 || i >= grid.length || j<0 || j >= grid[i].length || grid[i][j] == 0){
            return 0;
        }

        grid[i][j] = 0;
        return 1 + dfsArea(i + 1, j, grid) +
                dfsArea(i - 1, j, grid) +
                dfsArea(i, j + 1, grid) +
                dfsArea(i, j - 1, grid);
    }

    // https://leetcode.com/problems/surrounded-regions/description/
    public void surroundedRegions(char[][] grid){
        if(grid.length == 0 || grid[0].length == 0) return;
        int m = grid.length;
        int n = grid[0].length;

        // checkout first & last col
        for(int i = 0; i<m; i++){
            dfsRegions(i, 0, grid);
            dfsRegions(i, n-1, grid);
        }

        // checkout first & last row
        for(int j = 1; j<n-1; j++){
            dfsRegions(0, j, grid);
            dfsRegions(m-1, j, grid);
        }

        // make all the rem 0 to X{
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'O') grid[i][j] = 'X';
                if (grid[i][j] == '*') grid[i][j] = 'O';
            }
        }
    }

    private void dfsRegions(int i, int j, char[][] grid){
        if(i < 0 || i >= grid.length || j < 0 || j >= grid[i].length) return;
        if(grid[i][j] == 'X' || grid[i][j] == '*') return;

        grid[i][j] = '*';
        dfsRegions(i+1, j, grid);
        dfsRegions(i-1, j, grid);
        dfsRegions(i, j+1, grid);
        dfsRegions(i, j-1, grid);
    }

    // https://leetcode.com/problems/rotting-oranges/
    public int orangesRotting(int[][] grid){
        if(grid.length == 0 ||grid[0].length == 0 ) return 0;
        int countFresh = 0;
        Queue<int[]> queue = new LinkedList<>();

        for(int i=0; i<grid.length; i++){
            for (int j=0; j<grid[0].length; j++){
                if(grid[i][j] == 2){
                    queue.offer(new int[]{i, j});
                }
                else if (grid[i][j] == 1){
                    countFresh++;
                }
            }
        }

        if(countFresh == 0) return 0;
        int count = 0;
        int[][] dirs = {{1,0}, {0,1}, {-1,0}, {0, -1}};

        while(!queue.isEmpty()){
            int size = queue.size();
            count++;

            for(int i = 0; i<size; i++){
                int[] curr = queue.poll();

                for(int dir[] : dirs){
                    int x  = curr[0] + dir[0];
                    int y = curr[1] + dir[1];

                    if(x <0 || x >= grid.length || y<0 || y>= grid[0].length || grid[x][y] == 0 || grid[x][y] == 2) continue;

                    grid[x][y] = 2;
                    queue.offer(new int[] {x,y});
                    countFresh--;
                }
            }
        }

        return countFresh ==0 ? count-1 : -1;
    }

    StringBuilder sb;
    // https://leetcode.com/problems/number-of-distinct-islands
    public int numDistinctIslands(int[][] grid) {
        if(grid.length == 0 || grid[0].length == 0) return 0;
        Set<String> islandCount = new HashSet<>();

        for(int i = 0; i< grid.length; i++){
            for(int j =0; j<grid[0].length; j++){
                if(grid[i][j] == 1){
                    sb = new StringBuilder();
                    dfsDistinct(grid, i, j, '0');

                    if(sb.length() == 0) continue;
                    islandCount.add(sb.toString());
                }
            }
        }
        return islandCount.size();
    }

    private void dfsDistinct(int[][] grid, int i, int j, char dir){
        if(i < 0 || i >= grid.length || j< 0 || j >= grid[0].length || grid[i][j] == 0){
            return ;
        }

        grid[i][j] = 0;
        sb.append(dir);

        grid[i][j] = 0;
        dfsDistinct(grid, i+1, j, 'D');
        dfsDistinct(grid, i, j+1, 'R');
        dfsDistinct(grid, i-1, j, 'U');
        dfsDistinct(grid, i, j-1, 'L');
        sb.append('0');
    }

    // can be solved as numOfIslands via DFS but check here for benefits - https://leetcode.com/problems/walls-and-gates/solutions/72748/benchmarks-of-dfs-and-bfs/
    // https://leetcode.com/problems/walls-and-gates/
    public void wallsAndGates(int[][] rooms) {
        if(rooms.length == 0) return;
        int m = rooms.length; int n = rooms[0].length;

        Queue<int[]> queue = new LinkedList<>();
        for(int i = 0; i< m; i++){
            for(int j=0; j<n; j++){
                if(rooms[i][j] == 0) queue.offer(new int[] {i,j});
            }
        }
        List<int[]> DIRECTIONS = Arrays.asList(
                new int[] { 1,  0},
                new int[] {-1,  0},
                new int[] { 0,  1},
                new int[] { 0, -1}
        );

        while(!queue.isEmpty()){
            int[] point = queue.poll();
            int row = point[0];
            int col = point[1];

            for(int[] dir : DIRECTIONS){
                int r = row + dir[0];
                int c = col + dir[1];

                if(r < 0 || c < 0 || r >=m || c >=n || rooms[r][c] != Integer.MAX_VALUE){
                    continue;
                }

                rooms[r][c] = rooms[row][col]+1;
                queue.offer(new int[] {r,c});
            }
        }
    }

    // https://leetcode.com/problems/flood-fill/
    // O(mn) decided by tree height
    public int[][] floodFill(int[][] grid, int sr, int sc, int newColor) {
        if(grid.length == 0) return grid;

        int oldColor = grid[sr][sc];
        callDFSFloodFill(grid, sr, sc, newColor, oldColor);

        return grid;
    }

    // make the island's 1s to 0s
    private static void callDFSFloodFill(int[][] grid, int i, int j, int newColor, int oldColor){
        if(i < 0 || i >=grid.length || j <0 || j >= grid[i].length || grid[i][j] != oldColor || grid[i][j] == newColor){
            return;
        }

        if(grid[i][j] == oldColor){
            grid[i][j] = newColor;
        }

        callDFSFloodFill(grid, i+1, j, newColor, oldColor);
        callDFSFloodFill(grid, i, j+1, newColor, oldColor);
        callDFSFloodFill(grid, i-1, j, newColor, oldColor);
        callDFSFloodFill(grid, i, j-1, newColor, oldColor);
    }

    // we do not need to scan the entire matrix to find the "start point" of DFS, we just need to look at the first and last rows
    // and columns. Because essentially we can find all points from where water can get to the ocean by starting DFS at the border.
    // https://leetcode.com/problems/pacific-atlantic-water-flow/
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        List<List<Integer>> res = new LinkedList<>();
        if(heights == null || heights.length == 0 || heights[0].length == 0) return res;

        int n = heights.length; int m = heights[0].length;

        boolean[][] pacific = new boolean[n][m];
        boolean[][] atlantic = new boolean[n][m];

        for(int i = 0; i<n; i++){
            dfsOcean(heights, pacific, Integer.MIN_VALUE, i, 0);
            dfsOcean(heights, atlantic, Integer.MIN_VALUE, i, m-1);
        }

        for(int i = 0; i<m; i++){
            dfsOcean(heights, pacific, Integer.MIN_VALUE, 0, i);
            dfsOcean(heights, atlantic, Integer.MIN_VALUE, n-1, i);
        }

        for(int i = 0; i<n; i++){
            for(int j=0; j<m; j++){
                if(pacific[i][j] && atlantic[i][j]){
                    res.add(List.of(i, j));
                }
            }
        }
        return res;
    }

    int[][]dir = new int[][]{{0,1},{0,-1},{1,0},{-1,0}};
    public void dfsOcean(int[][]matrix, boolean[][]visited, int height, int x, int y){
        int n = matrix.length, m = matrix[0].length;
        if(x<0 || x>=n || y<0 || y>=m || visited[x][y] || matrix[x][y] < height)
            return;
        visited[x][y] = true;
        for(int[]d:dir){
            dfsOcean(matrix, visited, matrix[x][y], x+d[0], y+d[1]);
        }
    }

    public int firstCompleteIndex(int[] arr, int[][] mat) {
        Map<Integer, int[]> map = new HashMap<>();

        for(int i =0; i<mat.length; i++){
            for(int j = 0; j<mat[0].length; j++){
                map.put(mat[i][j], new int[]{i, j});
            }
        }

        int[] row = new int[mat.length];
        int[] col = new int[mat[0].length];

        for(int i=0; i<arr.length; i++){
            int[] curr = map.get(arr[i]);

            row[curr[0]]++;
            col[curr[1]]++;

            // a row is considered filled when all cols are filled & vice-versa
            // that's why row == mat[0].length, col == mat.length
            if(row[curr[0]] == mat[0].length || col[curr[1]] == mat.length){
                return i;
            }
        }

        return -1;
    }

    // https://leetcode.com/problems/transpose-matrix
    public int[][] transpose(int[][] A) {
        int M = A.length, N = A[0].length;
        int[][] B = new int[N][M];
        for (int j = 0; j < N; j++)
            for (int i = 0; i < M; i++)
                B[j][i] = A[i][j];
        return B;
    }

    // https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/
    // kth smallest matrix
    // space - O(k), time - O(k * log(K))
    // check for binary-search too
    public int kthSmallest(int[][] matrix, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

        int n = matrix.length;
        for(int i = 0; i<n; i++){
            for(int j=0; j<n; j++){
                if(pq.size() < k){
                    pq.add(matrix[i][j]);
                }else{
                    if(matrix[i][j] < pq.peek()){
                        pq.poll();
                        pq.add(matrix[i][j]);
                    }
                }
            }
        }
        return pq.peek();
    }


    // https://leetcode.com/problems/rotate-image/description/
    private void rotate(int[][] arr){
        if(arr == null || arr.length == 0 || arr[0].length ==0) return;

        int row = arr.length;
        int col = arr[0].length;

        for(int i = 0; i<row; i++){
            for(int j = i; j<col; j++){
                int tmp = arr[i][j];
                arr[i][j] = arr[j][i];
                arr[j][i] = tmp;
            }
        }

        for(int i = 0; i<row; i++){
            for(int j = 0; j<arr[i].length/2; j++){
                int tmp = arr[i][j];
                arr[i][j] = arr[i][col-1-j];
                arr[i][col-1-j] = tmp;
            }
        }
    }

    // https://leetcode.com/problems/spiral-matrix
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        int m = 0;
        int n=0;

        int maxrows = matrix.length;
        int maxcols = matrix[0].length;
        int size = maxrows * maxcols;
        int count = 0;

        while(count < size){
            for(int i =m, j=n; j<maxcols && count <size; j++){
                res.add(matrix[i][j]);
                count++;
            }
            m++;

            for(int i =m, j=maxcols-1; i<maxrows && count <size; i++){
                res.add(matrix[i][j]);
                count++;
            }
            maxcols--;

            for(int i =maxrows-1, j=maxcols-1; j>=n && count <size; j--){
                res.add(matrix[i][j]);
                count++;
            }
            maxrows--;

            for(int i =maxrows-1, j=n; i>=m && count <size; i--){
                res.add(matrix[i][j]);
                count++;
            }
            n++;
        }
        return res;
    }

    // https://leetcode.com/problems/spiral-matrix-ii
    public int[][] generateMatrix(int n) {
        int[][] mat = new int[n][n];

        int minr = 0;
        int minc = 0;
        int maxr = n-1;
        int maxc = n-1;

        int totalCount = n * n +1 ;
        int ctr = 1;

        while(ctr < totalCount){
            // top most row
            for (int i = minr, j=minc; j <= maxc && ctr < totalCount; j++) {
                mat[i][j] = ctr++;
            }
            minr++;
            // right most col
            for (int i = minr, j=maxc; i <= maxr && ctr < totalCount; i++) {
                mat[i][j] = ctr++;
            }
            maxc--;
            // last row
            for (int i = maxr, j=maxc; j >= minc && ctr < totalCount; j--) {
                mat[i][j] = ctr++;
            }
            maxr--;
            // left most col
            for (int i = maxr, j=minc; i >= minr && ctr < totalCount; i--) {
                mat[i][j] = ctr++;
            }
            minc++;
        }
        return mat;
    }

    // https://leetcode.com/problems/set-matrix-zeroes
    public void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        boolean isRow0 = false;
        boolean isCol0 = false;

        for(int j = 0; j<n; j++){
            if(matrix[0][j] == 0){
                isRow0 = true;
                break;
            }

        }

        for(int i = 0; i<m; i++){
            if(matrix[i][0] == 0) {
                isCol0 = true;
                break;
            }
        }

        for(int i =1; i<m; i++){
            for(int j=1; j<n; j++){
                if(matrix[i][j] == 0){
                    matrix[i][0] =0;
                    matrix[0][j] = 0;
                }}}

        for(int i =1; i<m; i++){
            for(int j=1; j<n; j++){
                if(matrix[0][j] == 0 || matrix[i][0] == 0){
                    matrix[i][j] =0;
                }}}

        if(isRow0){
            for(int j=0;j<n;j++){
                matrix[0][j]=0;
            }}

        if(isCol0){
            for(int i=0;i<m;i++){
                matrix[i][0]=0;
            }}
    }

    // another vImp based on numberOfIsland problem
    // https://leetcode.com/problems/longest-increasing-path-in-a-matrix/
    public static int longestIncreasingPathMatrix(int[][] matrix) {
        if(matrix.length == 0 || matrix[0].length==0) return 0;

        int max =0;
        int[][] dp = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(dp[i][j] == 0){
                    callDFSLIP(matrix, i, j, Integer.MIN_VALUE, dp);
                    max = Math.max(max, dp[i][j]);
                }
            }
        }
        return max;
    }

    // keep the path max in dp table
    private static int callDFSLIP(int[][] grid, int i, int j, int prev, int[][] dp){
        if(i < 0 || i >=grid.length || j <0 || j >= grid[i].length || prev >= grid[i][j]){
            return 0;
        }
        if(dp[i][j] != 0) return dp[i][j];

        int path1 = callDFSLIP(grid, i+1, j, grid[i][j], dp);
        int path2 = callDFSLIP(grid, i, j+1, grid[i][j], dp);
        int path3 = callDFSLIP(grid, i-1, j, grid[i][j], dp);
        int path4 = callDFSLIP(grid, i, j-1, grid[i][j], dp);

        int max1 = Math.max(path1, path2);
        int max2 = Math.max(path3, path4);
        dp[i][j] = Math.max(max1, max2) + 1;

        return dp[i][j];
    }

    // https://leetcode.com/problems/unique-paths/
    // O(mXn)
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];

        // for each row, there is only 1 way to reach it (first col)
        for(int i =0; i<dp.length; i++){
            dp[i][0] = 1;
        }

        // for each col, there is only 1 way to reach it (first row)
        for(int j =0; j<dp[0].length; j++){
            dp[0][j] = 1;
        }

        // now for every other row/col, we got 2 options, just sum them up
        for(int i =1; i< m; i++){
            for(int j=1; j<n; j++){
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }

        return dp[m-1][n-1];
    }

    // this alternate to print all paths was asked in meta
    // O(2^2(n−1))  (Exponential, as we make two recursive calls per step)
    // just to find the paths is simple DP, solved above
    // O(n) space as  (Recursive depth at most 2n-2 in the worst case)
    // DDRR   (Down, Down, Right, Right)
    //DRDR   (Down, Right, Down, Right)
    //DRRD   (Down, Right, Right, Down)
    //RDDR   (Right, Down, Down, Right)
    //RDRD   (Right, Down, Right, Down)
    //RRDD   (Right, Right, Down, Down)
    List<String> res;
    public List<String> uniquePathsMeta(int n) {
        res = new ArrayList<>();
        backtrack(0,0, n, new StringBuilder());
        return res;
    }

    private void backtrack(int row, int col, int n, StringBuilder path){
        if(row == n-1 && col == n-1){
            res.add(path.toString());
            return;
        }

        if(row +1 < n){
            path.append("D");
            backtrack(row+1, col, n, path);
            path.deleteCharAt(path.length()-1);
        }

        if(col +1 < n){
            path.append("R");
            backtrack(row, col+1, n, path);
            path.deleteCharAt(path.length()-1);
        }
    }

    // https://leetcode.com/problems/unique-paths-ii/
    public int uniquePathsII(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;

        // If the starting cell has an obstacle, then simply return as there would be
        // no paths to the destination.
        if (obstacleGrid[0][0] == 1) {
            return 0;
        }

        // Number of ways of reaching the starting cell = 1.
        obstacleGrid[0][0] = 1;

        // Filling the values for the first column
        for (int i = 1; i < m; i++) {
            obstacleGrid[i][0] = (obstacleGrid[i][0] == 0 && obstacleGrid[i - 1][0] == 1) ? 1 : 0;
        }

        // for each row, there is only 1 way to reach it (first col)
        for(int i =1; i<n; i++){
            obstacleGrid[0][i] = (obstacleGrid[0][i] == 0 && obstacleGrid[0][i - 1] == 1) ? 1 : 0;
        }

        // now for every other row/col, we got 2 options, just sum them up
        for(int i =1; i< m; i++){
            for(int j=1; j<n; j++){
                if (obstacleGrid[i][j] == 0) {
                    obstacleGrid[i][j] = obstacleGrid[i - 1][j] + obstacleGrid[i][j - 1];
                } else {
                    obstacleGrid[i][j] = 0;
                }
            }
        }

        return obstacleGrid[m-1][n-1];
    }

    // https://leetcode.com/problems/the-maze
    public boolean maze(int[][] maze, int[] start, int[] destination) {
        int m = maze.length;
        int n = maze[0].length;
        boolean[][] visit = new boolean[m][n];
        return dfs(m, n, maze, start, destination, visit);
    }

    private boolean dfs(int m, int n, int[][] maze, int[] curr, int[] dest, boolean[][] visit){
        if(visit[curr[0]][curr[1]]){
            return false;
        }

        if(curr[0] == dest[0] && curr[1] == dest[1]){
            return true;
        }

        visit[curr[0]][curr[1]] = true;
        int[] dirX = {0,1,0,-1};
        int[] dirY = {-1,0,1,0};

        for(int i =0; i<4; i++){
            int r = curr[0], c = curr[1];

            while(r >=0 && r < m && c >=0 && c < n && maze[r][c] == 0){
                r += dirX[i];
                c += dirY[i];
            }

            if(dfs(m, n, maze, new int[] {r-dirX[i], c - dirY[i]}, dest, visit)){
                return true;
            }
        }
        return false;
    }

    int[][] dirs = new int[][]{{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

    // O{N}
    // https://leetcode.com/problems/shortest-path-in-binary-matrix/
    public int shortestPathBinaryMatrix(int[][] grid) {
        // check start and end are 0
        if(grid[0][0] != 0 || grid[grid.length-1][grid[0].length-1] != 0) return -1;

        int pathLen = 1;

        Queue<int[]> queue = new LinkedList<>();
        grid[0][0]  = 1;
        queue.add(new int[]{0, 0});

        while(!queue.isEmpty()){
            int[] curr = queue.remove();
            int row = curr[0];
            int col = curr[1];
            int distance = grid[row][col];

            if(row == grid.length-1 && col == grid[0].length-1){
                return distance;
            }

            for(int[] neighbor : getNeighbours(row, col, grid)){
                int neighborRow = neighbor[0];
                int neighborCol = neighbor[1];

                queue.add(new int[] { neighborRow, neighborCol});
                grid[neighborRow][neighborCol] = distance+1;
            }
        }

        return -1;
    }

    private List<int[]> getNeighbours(int row, int col, int[][] grid) {
        List<int[]> neighbours = new ArrayList<>();
        for (int i = 0; i < dirs.length; i++) {
            int newRow = row + dirs[i][0];
            int newCol = col + dirs[i][1];
            if (newRow < 0 || newCol < 0 || newRow >= grid.length
                    || newCol >= grid[0].length
                    || grid[newRow][newCol] != 0) {
                continue;
            }
            neighbours.add(new int[]{newRow, newCol});
        }
        return neighbours;
    }

    // https://leetcode.com/problems/diagonal-traverse
    // good soln - https://leetcode.com/problems/diagonal-traverse/solutions/581868/Easy-Python-NO-DIRECTION-CHECKING/
    // idea is - sum of indices at every diag is same
    // zig-zag
    // space- O(square-root-N) Time - O(n)
    public int[] findDiagonalOrder(int[][] matrix) {
        if(matrix==null|| matrix.length==0) return new int[0];
        int N=matrix.length;
        int M=matrix[0].length;
        int[] res=new int[N*M];
        Map<Integer,List<Integer>>dict=new HashMap<>();
        for(int i=0;i<N;i++)
        {
            for(int j=0;j<M;j++)
            {
                int sum=i+j;
                if(!dict.containsKey(sum))
                {
                    dict.put(sum, new ArrayList<>());
                }
                dict.get(sum).add(matrix[i][j]);
            }
        }
        int ctr=0;
        for(Map.Entry<Integer,List<Integer>> entry:dict.entrySet())
        {
            List<Integer> temp_list=new ArrayList<>();
            if(entry.getKey()%2==0)
            {
                temp_list=entry.getValue();
                Collections.reverse(temp_list);
            }
            else
            {
                temp_list=entry.getValue();
            }
            for(int i=0;i<temp_list.size();i++)
            {
                res[ctr++]=temp_list.get(i);
            }
        }
        return res;
    }

    // like level order traversal
    //
    public static List<List<Integer>> findAntiDiagonalOrder2(List<List<Integer>> nums) {
        List<List<Integer>> result = new ArrayList<>();
        Queue<int[]> queue = new LinkedList<>();

        queue.offer(new int[]{0, 0});

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> currLevel = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                int[] position = queue.poll();
                int row = position[0], col = position[1];

                currLevel.add(nums.get(row).get(col));

                if (col + 1 < nums.get(row).size()) {
                    queue.offer(new int[]{row, col + 1});
                }

                if (col == 0 && row + 1 < nums.size()) {
                    queue.offer(new int[]{row + 1, col});
                }
            }

            result.add(currLevel);
        }

        return result;
    }

    // #meta
    // little diff approach to solve diagonalII ... like level order traversal
    //    // space- O(square-root-N) Time - O(n)
    public int[] findDiagonalOrderII(List<List<Integer>> nums) {
        List<Integer> resultList = new ArrayList<>();
        Queue<int[]> queue = new LinkedList<>();

        queue.offer(new int[]{0, 0}); // Start from (0,0)

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0], col = current[1];

            resultList.add(nums.get(row).get(col));

            // Add the next row element if we're at column 0 (ensures diagonal traversal)
            if (col == 0 && row + 1 < nums.size()) {
                queue.offer(new int[]{row + 1, col});
            }

            // Add the next column element if it exists
            if (col + 1 < nums.get(row).size()) {
                queue.offer(new int[]{row, col + 1});
            }
        }

        // Convert List<Integer> to Integer[] array
        int[] res = new int[resultList.size()];
        int idx = 0;
        for(int i : resultList){
            res[idx] = i;
            idx++;
        }
        return res;
    }

    // https://leetcode.com/problems/toeplitz-matrix/
    public boolean isToeplitzMatrix(int[][] matrix) {
        for (int i = 1; i < matrix.length; i++){
            for(int j = 1; j< matrix[0].length; j++){
                if(matrix[i][j] != matrix[i-1][j-1]){
                    return false;
                }
            }
        }
        return true;
    }

    // https://leetcode.com/problems/valid-word-square/
    public boolean validWordSquare(List<String> words) {
        for (int wordNum = 0; wordNum < words.size(); ++wordNum) {
            for (int charPos = 0; charPos < words.get(wordNum).length(); ++charPos) {
                // charPos (curr 'row' word) is bigger than column word, or
                // wordNum (curr 'column' word) is bigger than row word, or
                // characters at index (wordNum,charPos) and (charPos,wordNum) are not equal.
                if (charPos >= words.size() ||
                        wordNum >= words.get(charPos).length() ||
                        words.get(wordNum).charAt(charPos) != words.get(charPos).charAt(wordNum)){
                    return false;
                }
            }
        }
        return true;
    }
}
