package com.app.minesweeper;

import java.util.ArrayList;

public class MineSweeper {

    ArrayList<Cell> cells = new ArrayList<>();

    public void startGame(int level) {
        CellCreator cellCreator = new CellCreator();
        cellCreator.level = level;
        cells = cellCreator.create();
    }
}
