package com.app.minesweeper;

import java.util.ArrayList;
import java.util.HashSet;

public interface ICellCreator {
    // 創造格子
    ArrayList<Cell> create();

    // 設定地雷位置
    HashSet<Integer> createRandomIndexes(int cellSizes);
}
