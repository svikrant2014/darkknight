package org.darkknight;

public class TicTacToe {

    public int[] row;
    public int[] col;
    public int d1;
    public int d2;
    public int size ;

    public TicTacToe(int n){
        row = new int[n];
        col = new int[n];
        size = n;
        d1 = d2 = 0;
    }

    public int move(int r, int c, int player){
        if(player == 1){
            row[r]++;
            col[c]++;
            if(r == c) d1++;
            if(r+c == size-1) d2++;
            if(row[r] == size || col[c] == size || d1 == size || d2 == size) return 1;
        }
        else{
            row[r]--;
            col[c]--;
            if(r == c) d1--;
            if(r+c == size-1) d2--;
            if(row[r] == -size || col[c] == -size || d1 == -size || d2 == -size) return 2;
        }

        return 0;
    }
}
