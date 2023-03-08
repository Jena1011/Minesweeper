package com.app.minesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class CellCreator implements ICellCreator {
    public int level = 0; // 難度等級

    // 創造格子
    @Override
    public ArrayList<Cell> create() {
        ArrayList<Cell> list = new ArrayList<>();

        HashSet<Integer> indexSet = createRandomIndexes(this.level);

        for (int i = 0; i < level; i++) {
            for (int j = 0; j < level; j++) {
                Cell cell = new Cell();
                cell.status = Cell.STATUS.CLOSE;
                if (indexSet.contains( i * 9 + j + 1 )) {
                    cell.isMine = true;
                }
                list.add(cell);
            }
        }
        return list;
    }

    // 設定地雷位置
    @Override
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
