package com.app.minesweeper;

public class Cell {
    public boolean isMine = false;
    private int x;
    private int y;
    STATUS status = null;

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

    enum STATUS {
        OPEN, CLOSE
    }
}
