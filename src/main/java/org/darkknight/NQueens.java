package org.darkknight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NQueens {
    // https://leetcode.com/problems/n-queens/
    // prob is to place N queens on a n x n board and queens can move up and down and diagonally
    // idea is that you can put max 1 in one row
    // then go through rows and explore all cols in next row
    // also you need to check diagonals otherwise they will kill each other
    public static List<List<String>> solveNQueens(int n){
        List<List<String>> res = new ArrayList<>();
        if (n <= 0) return res;

        int[][] board = new int[n][n];

//    backtrackNQueens(board, res, "", 0);
        boolean[] cols = new boolean[n];
        boolean[] ndiag = new boolean[2 * n -1];
        boolean[] rdiag = new boolean[2 * n -1];
        backtrackNQueensMemoized(board, 0, cols, ndiag, rdiag, "");
        return res;
    }

    private static void backtrackNQueens(int[][] board, List<List<String>> res, String qsf, int row){
        if(row == board.length){
            // add ans to res
            System.out.println(qsf);
            return;
        }

        for (int col = 0; col < board[0].length; col++) {
            if (isSafeToPlace(board, row, col)) {
                board[row][col] = 1;
                backtrackNQueens(board, res, qsf + row + '-' + col + ", ", row + 1);
                board[row][col] = 0;
            }
        }
    }

    // memoized version is blocking cols where you already sitting
    // and diagonal normal and reverse so that you don't need to check for them
    // normal diag - r+c, reverse-diag - r-c+board.length-1
    private static void backtrackNQueensMemoized(int[][] board, int row, boolean[] cols, boolean[] ndiag, boolean[] rdiag, String qsf){
        if(row == board.length){
            // add ans to res
            System.out.println(qsf);
            return;
        }

        for (int col = 0; col < board[0].length; col++) {
            if (cols[col] == false && ndiag[row + col] == false && rdiag[row -col + board.length -1] == false) {
                board[row][col] = 1;
                cols[col] = true;
                ndiag[row + col] = true;
                rdiag[row -col + board.length -1] = true;
                backtrackNQueensMemoized(board, row+1, cols, ndiag, rdiag, qsf + row + '-' + col + ", ");
                cols[col] = false;
                ndiag[row + col] = false;
                rdiag[row -col + board.length -1] = false;
                board[row][col] = 0;
            }
        }
    }

    // just need to check above you as we are going row by row
    private static boolean isSafeToPlace(int[][] board, int row, int col){
        // check every row above for same col
        for (int i = row-1, j = col; i >= 0; i--) {
            if(board[i][j] == 1){
                return false;
            }
        }

        // check first diagonal
        for (int i = row-1, j = col-1; i >= 0 && j >= 0; i--, j--) {
            if(board[i][j] == 1){
                return false;
            }
        }

        // check second diagonal
        for (int i = row-1, j = col+1; i >= 0 && j < board[row].length; i--, j++) {
            if(board[i][j] == 1){
                return false;
            }
        }

        return true;
    }

    // https://leetcode.com/problems/n-queens/
    // just a little deviation coz of output
    public List<List<String>> solveNQueensLeetcodeVersion(int n) {

        List<List<String>> res = new ArrayList<>();

        boolean[] cols = new boolean[n];
        boolean[] ndiag = new boolean[2 * n - 1];
        boolean[] rdiag = new boolean[2 * n - 1];
        int[] h = new int[n];
        dfs(res, h, cols, ndiag, rdiag, n, 0);

        return res;

    }

    private void dfs(List<List<String>> res, int[] h, boolean[] cols, boolean[] ndiag, boolean[] rdiag, int n, int row){
        // only this was the extra work
        if(row == n){
            List<String> list = new ArrayList();

            for(int i=0; i< n; i++){
                char[] cs = new char[n];
                Arrays.fill(cs, '.');
                cs[h[i]] = 'Q';
                list.add(new String(cs));
            }
            res.add(list);
            return;
        }

        for(int col = 0; col < n; col++){
            if(cols[col] || ndiag[col + row] || rdiag[row - col + n - 1]){
                continue;
            }
            cols[col] = true;
            ndiag[col + row] = true;
            rdiag[row - col + n - 1] = true;
            h[row] = col;
            dfs(res, h, cols, ndiag, rdiag, n, row + 1);
            cols[col] = false;
            ndiag[col + row] = false;
            rdiag[row - col + n - 1] = false;
        }
    }
}
