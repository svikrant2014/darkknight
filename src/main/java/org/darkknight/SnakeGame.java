package org.darkknight;

import java.util.LinkedList;
import java.util.Queue;

public class SnakeGame {

    class Node{
        int x;
        int y;
        Node (int x, int y){
            this.x = x;
            this.y = y;
        }

        public boolean isEqual(Node node){
            return this.x == node.x && this.y == node.y;
        }
    }

    int width, height;
    int[][] food;
    int score;

    LinkedList<Node> snake;
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        this.food = food;

        snake = new LinkedList<>();
        snake.add(new Node(0,0));
    }

    public int move(String direction) {
        Node head = snake.peekFirst();
        Node nxt = new Node(head.x, head.y);

        switch (direction){
            case "U":
                nxt.x--;
                break;
            case "D":
                nxt.x++;
                break;
            case "L":
                nxt.y--;
                break;
            case "R":
                nxt.y++;
        }
        if(!validate(nxt)) return -1;
        growSnakeAndScore(nxt);
        return score;
    }

    private boolean validate(Node node){
        // boundary check
        if(node.x < 0 || node.y < 0 || node.x >= height || node.y>= width){
            return false;
        }

        // body check -> check size()-1 as the last node will be removed if moved to next block
        for(int i = 0; i<snake.size()-1; i++){
            if(node.isEqual(snake.get(i))){
                return false;
            }
        }
        return true;
    }

    private void growSnakeAndScore(Node nxt){
        if(score < food.length && nxt.x == food[score][0] && nxt.y == food[score][1]){
            score++;
            snake.addFirst(nxt);
        }else{
            snake.addFirst(nxt);
            snake.removeLast();
        }
    }
}
