package com.shawn.touchstone.tdd;

import java.util.Arrays;

public class TicTacToe {

    private final char EMPTY = '-';

    private char lastPlayer = 'X';

    private char[][] board = new char[][]{
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY},
    };

    public String play(int x, int y) {
        checkBound(x);
        checkBound(y);
        setVal(x, y);
        if (win(x, y)) {
            return lastPlayer + " is the winner";
        } else if (draw()) {
            return "it's a draw";
        }
        setNext();
        return "No winner";
    }

    private boolean draw() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean win(int x, int y) {
        int playTotal = lastPlayer * 3;
        char horizontal, vertical, diagonal1, diagonal2;
        horizontal = vertical = diagonal1 = diagonal2 = 0;
        for (int i = 0; i < board.length; i++) {
            horizontal += board[i][y - 1];
            vertical += board[x - 1][i];
            diagonal1 += board[i][i];
            diagonal2 += board[i][board.length - i - 1];
        }
        return horizontal == playTotal || vertical == playTotal || diagonal1 == playTotal || diagonal2 == playTotal;
    }

    private boolean isDiagonalLine() {
        return (board[0][0] == lastPlayer && board[1][1] == lastPlayer && board[2][2] == lastPlayer) ||
                (board[2][0] == lastPlayer && board[1][1] == lastPlayer && board[0][2] == lastPlayer);
    }

    private boolean isVertical() {
        for (int i = 0; i < board.length; i++) {
            if (board[i][0] == lastPlayer &&
                    board[i][1] == lastPlayer &&
                    board[i][2] == lastPlayer) {
                return true;
            }
        }
        return false;
    }

    private boolean isHorizontal() {
        for (int i = 0; i < board.length; i++) {
            if (board[0][i] == lastPlayer &&
            board[1][i] == lastPlayer &&
            board[2][i] == lastPlayer) {
                return true;
            }
        }
        return false;
    }

    public char[][] getBoard() {
        char[][] copied = new char[3][3];
        for (int i = 0; i < board.length; i++) {
            copied[i] = Arrays.copyOf(board[i], 3);
        }
        return copied;
    }

    private void checkBound(int val) {
        if (val < 1 || val > 3) {
            throw new RuntimeException();
        }
    }

    private void setVal(int x, int y) {
        if (board[x - 1][y - 1] != '-') {
            throw new RuntimeException();
        }
        board[x - 1][y - 1] = lastPlayer;
    }

    private void setNext() {
        if (lastPlayer == 'X') {
            lastPlayer = 'O';
        } else {
            lastPlayer = 'X';
        }
    }

    public char nextPlayer() {
        return lastPlayer;
    }
}
