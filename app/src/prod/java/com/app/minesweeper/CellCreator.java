package com.app.minesweeper;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Cell產生器。按照所需的地圖尺寸、難度，生成相應的 ArrayList<Cell>
 */
public class CellCreator implements ICellCreator {
    protected String size = ""; // 地圖尺寸
    private int numRows; // 排數
    private int numCols; // 列數

    /**
     * 建構子。根據地圖尺寸設定每排及列的格子數量
     * @param size 地圖尺寸
     */
    CellCreator(String size){
        this.size = size;
    }

    /**
     * 空建構子
     */
    CellCreator(){

    }

    /**
     * 根據地圖尺寸，生成相應數量的 Cell 列表，並設定每個 Cell 的初始狀態(座標、開合、地雷)
     * @return 代表地圖上所有格子的 ArrayList<Cell>
     */
    @Override
    public ArrayList<Cell> create() {
        ArrayList<Cell> list = new ArrayList<>();

        // 根據地圖尺寸，設定不同行列的格數
        switch (size){
            case "6X6":
                numRows = 6;
                numCols = 6;
                break;
            case "9X9":
                numRows = 9;
                numCols = 9;
                break;
            case "9X13":
                numRows = 13;
                numCols = 9;
                break;
        }

        // 產生作為地雷位置的隨機索引值
        HashSet<Integer> indexSet = createRandomIndexes(numRows,numCols);

        // 生成 cell 列表
        for (int y = 0; y < numRows; y++) {
            for (int x = 0; x < numCols; x++) {
                Cell cell = new Cell(x,y);
                cell.status = CellStatus.CLOSE;
                if (indexSet.contains( y * numCols + x + 1 )) {
                    cell.isMine = true;
                }
                list.add(cell);
            }
        }
        return list;
    }

    /**
     * 根據地圖尺寸及難度，產生相應數量隨機索引，可用來設定地雷位置
     * @param numRows 地圖行數
     * @param numCols 地圖列數
     * @return 代表地雷位置的 HashSet<Integer>
     */
    public HashSet<Integer> createRandomIndexes(int numRows, int numCols) {
        int numberOfIndex = (int) Math.ceil(numRows * numCols * 0.15);
        HashSet<Integer> indexSet = new HashSet<>();
        while (indexSet.size() < numberOfIndex) {
            int randomIndex = (int) (Math.random() * numRows * numCols + 1);
            indexSet.add(randomIndex);
        }
        return indexSet;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }
}
