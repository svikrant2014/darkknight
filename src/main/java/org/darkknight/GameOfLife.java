package org.darkknight;

public class GameOfLife {

    public static void main(String[] args) {
        gameOfLife(boolMatrix);
    }
    static int[][] boolMatrix = {{ 0, 1, 0 },
            { 0, 0, 1 },
            { 1, 1, 1 },
            {0, 0, 0}};

    static int[] neighbors = {0, 1, -1};

    // idea is to check all neighbors
    // convert all ready to die into -1
    // convert which is coming back to life into 2
    // and in third pass, handle those updates
    // O(mn)
    public static void gameOfLife(int[][] board){
        int rows = board.length;
        int cols = board[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                int liveNeighbors = countLiveNeighbors(board, i, j);

                // rule 1 or rule 3
                if((board[i][j] == 1) && (liveNeighbors < 2 || liveNeighbors > 3)){
                    board[i][j] = -1;
                }

                // rule 4
                if(board[i][j] == 0 && liveNeighbors == 3){
                    board[i][j] = 2;
                }
            }
        }

        // update the final matrix
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(board[i][j] > 0){
                    board[i][j] = 1;
                }
                else{
                    board[i][j] = 0;
                }
            }
        }
        printMatrix(board);
    }
    private static int countLiveNeighbors(int[][] board, int row, int col){
        int rows = board.length;
        int cols = board[0].length;

        int liveNeighbors = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if(!(neighbors[i] == 0 && neighbors[j] == 0)){
                    int r = (row + neighbors[i]);
                    int c = (col + neighbors[j]);

                    if((r < rows && r >= 0) && (c < cols && c >= 0) && Math.abs(board[r][c]) == 1){
                        liveNeighbors++;
                    }
                }
            }
        }

        return liveNeighbors;
    }
    private static void printMatrix(int[][] test){
        StringBuilder sb = new StringBuilder();
        for(int row = 0; row < test.length; row++){
            for(int col = 0; col < test[row].length; col++){
                sb.append(test[row][col]);
                sb.append(", ");
            }
            System.out.println(sb);
            sb = new StringBuilder();
        }
    }
}
