package com.app.minesweeper;

import java.util.ArrayList;

public class CellCreator implements ICellCreator {
    public String size = ""; // 地圖尺寸
    int numRows = 0; // 排數
    int numCols = 0; // 列數

    public CellCreator(String size) {
        this.size = size;
    }

    public CellCreator() {

    }

    // 創造格子
    @Override
    public ArrayList<Cell> create() {
        ArrayList<String> init = new ArrayList<>();
        switch (size){
            case "6X6":
                init.add("*|-|-|-|-|-");
                init.add("-|-|-|-|-|-");
                init.add("-|-|-|-|-|-");
                init.add("*|*|-|-|-|-");
                init.add("-|*|-|-|*|-");
                init.add("-|-|-|*|-|-");
                numRows = 6;
                numCols = 6;
                break;
            case "9X9":
                init.add("*|-|-|-|-|*|-|-|-");
                init.add("-|-|-|-|-|-|-|-|-");
                init.add("-|-|-|-|-|*|-|-|-");
                init.add("*|*|-|-|-|-|-|-|*");
                init.add("-|*|-|-|*|-|-|-|*");
                init.add("-|-|-|*|-|-|-|-|*");
                init.add("-|-|-|-|-|-|-|-|-");
                init.add("-|-|-|-|-|-|*|-|-");
                init.add("-|-|*|-|-|-|-|-|-");
                numRows = 9;
                numCols = 9;
                break;
            case "9X13":
                init.add("*|-|-|-|-|*|-|-|-");
                init.add("-|-|-|-|-|-|-|-|-");
                init.add("-|-|-|-|-|*|-|-|-");
                init.add("*|*|-|-|-|-|-|-|*");
                init.add("-|*|-|-|*|-|-|-|*");
                init.add("-|-|-|*|-|-|-|-|*");
                init.add("-|-|-|-|-|-|-|-|-");
                init.add("-|-|-|-|-|-|*|-|-");
                init.add("-|-|*|-|-|-|-|-|-");
                init.add("-|*|-|-|*|-|-|-|*");
                init.add("-|-|-|*|-|-|-|-|*");
                init.add("-|-|-|-|-|-|-|-|-");
                init.add("-|-|-|-|-|-|-|-|-");
                numRows = 13;
                numCols = 9;
                break;
        }
        return createCell(init);
    }

    // 將圖示字串轉為 ArrayList<Cell>
    private ArrayList<Cell> createCell(ArrayList<String> initSweeper) {
        ArrayList<Cell> cells = new ArrayList<>();
        for (int y=0; y<initSweeper.size(); y++) {
            String[] ylist = initSweeper.get(y).split("\\|");
            for(int x=0; x<ylist.length; x++){
                Cell cell = new Cell(x,y);
                String value = ylist[x];
                cell.status = CellStatus.OPEN;
                if(value.equals("-")||value.equals("*")){
                    cell.status = CellStatus.CLOSE;
                }
                if(value.equals("*")){
                    cell.isMine = true;
                }
                cells.add(cell);
            }
        }
        System.out.println(cells);
        return cells;
    }

    @Override
    public int getNumCols() {
        return numCols;
    }

    @Override
    public int getNumRows() {
        return numRows;
    }
}
