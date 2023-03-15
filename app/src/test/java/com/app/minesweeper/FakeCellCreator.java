package com.app.minesweeper;

import java.util.ArrayList;
import java.util.HashSet;

public class FakeCellCreator implements ICellCreator {

    ArrayList<Cell> cells = null;

    @Override
    public ArrayList<Cell> create() {
        return cells;
    }

}
