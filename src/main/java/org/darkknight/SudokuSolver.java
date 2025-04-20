package org.darkknight;

public class SudokuSolver {

    // https://leetcode.com/problems/sudoku-solver/description/
    public static void solveSudoku(int[][] arr) {
        if(arr.length == 0 || arr[0].length == 0) return;

        dfs(arr, 0, 0);
    }
    private static void dfs(int[][] arr, int i, int j){
        if(i == arr.length) return;

        int nexti = 0;
        int nextj = 0;

        if(j == arr[0].length-1){
            nexti = i+1;
            nextj=0;
        }
        else{
            nexti = i;
            nextj=j+1;
        }

        if(arr[i][j] != 0){
            dfs(arr, nexti, nextj);
        }
        else{
            for(int val = 1; val <=9; val++){
                if(isValid(arr, i, j, val)){
                    arr[i][j] = val;
                    dfs(arr, nexti, nextj);
                    arr[i][j] = '.';
                }
            }
        }
    }

    private static boolean isValid(int[][] arr, int i, int j, int val){
        // check rows
        for(int a=0; a< arr[0].length; a++){
            if(arr[i][a] == val) return false;
        }
        // check cols
        for(int a=0; a< arr.length; a++){
            if(arr[a][j] == val) return false;
        }
        // check submatrix
        int subi = i/3 *3;
        int subj = j/3 *3;

        for(int k =0; k<3; k++){
            for(int l=0; l<3; l++){
                if(arr[subi+k][subj+l] == val)
                    return false;
            }
        }
        return true;
    }
}

