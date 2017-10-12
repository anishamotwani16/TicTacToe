package com.tictactoe;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by User on 4/1/2016.
 */
public class Node {
    private int board[][];
    Node prevBoard;

    public int[][] getBoard() {
        return board;
    }

    ArrayList<Node> children = new ArrayList<Node>();
    ;
    boolean complete;
    int depth;
    int scoreX, scoreO;    //+10, -10 , 0
    ArrayList<Node> nodeX = new ArrayList<Node>();
    ArrayList<Node> nodeO = new ArrayList<Node>();
    static int counter = 0;

    Node() {
        depth = 0;
        prevBoard = null;
        board = new int[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = 0;
        complete = false;
    }

    Node(Node p) {
        depth = p.depth + 1;
        prevBoard = p;
        board = new int[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = p.board[i][j];
        complete = false;
    }

    public boolean isComplete() {
        for (int i = 0; i < 3; i++) {
            int sum = 0;
            for (int j = 0; j < 3; j++)
                sum += board[i][j];
            if (sum == 3 || sum == -3)
                return true;
        }
        for (int i = 0; i < 3; i++) {
            int sum = 0;
            for (int j = 0; j < 3; j++)
                sum += board[j][i];
            if (sum == 3 || sum == -3)
                return true;
        }

        int sum = 0;
        sum = board[0][0] + board[1][1] + board[2][2];
        if (sum == 3 || sum == -3)
            return true;
        sum = 0;
        sum = board[0][2] + board[1][1] + board[2][0];
        if (sum == 3 || sum == -3)
            return true;
        return false;
    }

    public void makeTree(int value) {
        counter++;
        complete = isComplete();
        if (complete) {
            scoreX = (10 - depth) * value * (-1);
            scoreO = (10 - depth) * value;
            /*String s = "";
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++)
                    s += board[i][j] + " ";
                s += "\n";
            }
            Log.i("Tag4", "\n" + " Complete : " + s + " " + scoreX + " " + scoreO + " " + depth);*/
            return;
        }
        if (draw()) {
            scoreX = 0;
            scoreO = 0;
            /*String s = "";
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++)]
                    s += board[i][j] + " ";
                s += "\n";
            }
            Log.i("Tag4", "\n" + " Draw : " + s + " " + scoreX + " " + scoreO + " " + depth);*/
            return;
        }
        for (int i = 2; i >= 0; i--) {
            for (int j = 2; j >= 0; j--) {
                if (board[i][j] == 0) {
                    Node child = new Node(this);
                    child.board[i][j] = value;
                    child.prevBoard = this;
                    child.makeTree(value * (-1));
                    children.add(child);
                } //if
            }//inner for
        }//outer for
        if (value == 1) {
            scoreX = max(children, value);
            scoreO = min(children, value * (-1));
        } else {
            scoreX = min(children, value * (-1));
            scoreO = max(children, value);
        }
        /*String s = "";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                s += board[i][j] + " ";
            s += "\n";
        }
        Log.i("Tag4", "\n" + " Intermediate : \n" + s + " " + scoreX + " " + scoreO + " " + depth);*/
    }

    int max(ArrayList<Node> ch, int v) {
        int max = -20;
        if (v == 1) {
            for (int i = 0; i < ch.size(); i++) {
                Node tmp = ch.get(i);
                if (tmp.scoreX > max) {
                    max = tmp.scoreX;
                }
            }
            for (int i = 0; i < ch.size(); i++) {
                Node tmp = ch.get(i);
                if (tmp.scoreX == max) {
                    nodeX.add(tmp);
                }
            }
        } else {
            for (int i = 0; i < ch.size(); i++) {
                Node tmp = ch.get(i);
                if (tmp.scoreO > max) {
                    max = tmp.scoreO;
                }
            }
            for (int i = 0; i < ch.size(); i++) {
                Node tmp = ch.get(i);
                if (tmp.scoreO == max) {
                    nodeO.add(tmp);
                }
            }
        }
        return max;
    }

    int min(ArrayList<Node> ch, int v) {
        int min = 20;
        if (v == 1) {
            for (int i = 0; i < ch.size(); i++) {
                Node tmp = ch.get(i);
                if (tmp.scoreX < min) {
                    min = tmp.scoreX;
                }
            }
            for (int i = 0; i < ch.size(); i++) {
                Node tmp = ch.get(i);
                if (tmp.scoreX == min) {
                    nodeX.add(tmp);
                }
            }
        } else {
            for (int i = 0; i < ch.size(); i++) {
                Node tmp = ch.get(i);
                if (tmp.scoreO < min) {
                    min = tmp.scoreO;
                }
            }
            for (int i = 0; i < ch.size(); i++) {
                Node tmp = ch.get(i);
                if (tmp.scoreO == min) {
                    nodeO.add(tmp);
                }
            }
        }
        return min;
    }

    public boolean draw() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == 0)
                    return false;
        return true;
    }

    public Node find(int current[][]) {
        for (int i = 0; i < children.size(); i++) {
            if (Arrays.deepEquals(current, children.get(i).board))
                return children.get(i);
        }
        return this;
    }

    public Node best(int value) {
        Random random = new Random();
        int r;
        ArrayList<Node> node;
        if (value == 1)
            node = nodeX;
        else
            node = nodeO;
        r = random.nextInt(node.size());
        return node.get(r);
    }
}

