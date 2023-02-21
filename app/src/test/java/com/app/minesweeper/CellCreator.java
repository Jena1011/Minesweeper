package com.app.minesweeper;

import java.util.ArrayList;

public class CellCreator {
    public int level = 0;

    public ArrayList<Cell> create() {
        ArrayList<Cell> list = new ArrayList<>();
        for(int i = 0; i < level; i++ ){
            for(int j = 0; j < level; j++){
                Cell cell = new Cell();
                cell.status = STATUS.CLOSE;
                list.add(cell);
            }
        }
        return list;
    }
}
