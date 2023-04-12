package com.app.minesweeper;

import java.util.ArrayList;
import java.util.HashSet;

public class CellCreator implements ICellCreator {
    public int level = 9; // 難度等級

    CellCreator(int level){
        this.level = level;
    }

    CellCreator(){

    }

    // 創造格子
    @Override
    public ArrayList<Cell> create() {
        ArrayList<Cell> list = new ArrayList<>();

        HashSet<Integer> indexSet = createRandomIndexes(this.level);

        for (int y = 0; y < level; y++) {
            for (int x = 0; x < level; x++) {
                Cell cell = new Cell(x,y);
                cell.status = CellStatus.CLOSE;
                if (indexSet.contains( y * 9 + x + 1 )) {
                    cell.isMine = true;
                }
                list.add(cell);
            }
        }
        return list;
    }

    // 設定地雷位置
    public HashSet<Integer> createRandomIndexes(int cellSizes) {
        int numberOfIndex = (int) Math.ceil(cellSizes * cellSizes * 0.15);
        HashSet<Integer> indexSet = new HashSet<>();
        while (indexSet.size() < numberOfIndex) {
            int randomIndex = (int) (Math.random() * cellSizes * cellSizes + 1);
            indexSet.add(randomIndex);
        }
        return indexSet;
    }
}
