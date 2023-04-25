package com.app.minesweeper;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * ICellCreator 介面表示一個用於創建 Cell 物件的類。
 * 類必須實現 create() 方法，以返回一個 ArrayList<Cell> 對象，
 * 此對象包含表示遊戲版面上的所有單元格的 Cell 對象。
 * 這個介面用於在遊戲開始時創建 Cell 物件，以便 MineSweeper 對象能夠將它們添加到遊戲版面中。
 */
public interface ICellCreator {

    int numRows = 0; // 排數
    int numCols = 0; // 列數

    ArrayList<Cell> create();

    default int getNumRows() { return numRows; }
    default int getNumCols() {
        return numCols;
    }

}
