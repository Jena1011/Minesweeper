package com.app.minesweeper;

import java.util.ArrayList;
import java.util.HashSet;

public class FakeCellCreator implements ICellCreator {

    ArrayList<Cell> cells = null;

    @Override
    public ArrayList<Cell> create() {
        return cells;
    }

    @Override
    public int getNumRows() {
        return 0;
    }

    @Override
    public int getNumCols() {
        return 0;
    }

}
