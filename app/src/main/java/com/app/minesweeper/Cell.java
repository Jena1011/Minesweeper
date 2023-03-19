package com.app.minesweeper;

public class Cell {
    public boolean isMine = false;
    public int nextMines = 0;
    public boolean isFlagged = false;
    private int x;
    private int y;
    public STATUS status = null;

    public Cell() {

    }

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public enum STATUS {
        OPEN, CLOSE
    }
}
