package com.app.minesweeper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MineSweeper {

    ArrayList<Cell> cells = new ArrayList<>();

    public void startGame(ICellCreator cellCreator) {
        cells = cellCreator.create();
    }

    public void tap(int x, int y) {
        getCell(x,y).status = Cell.STATUS.OPEN;
    }


    @Nullable
    public Cell getCell(int x, int y) {
        Cell cell = null;
        for (Cell c : cells) {
            if (c.getX() == x && c.getY() == y) {
                cell = c;
                break;
            }
        }
        return cell;
    }
}
