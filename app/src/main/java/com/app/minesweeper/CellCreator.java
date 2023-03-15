package com.app.minesweeper;

import java.util.ArrayList;
import java.util.HashSet;

public class CellCreator implements ICellCreator {
    public int level = 0; // 難度等級

    // 創造格子
    @Override
    public ArrayList<Cell> create() {
        ArrayList<String> init = new ArrayList<>();
        init.add("*|-|-|-|-|*|-|-|-");
        init.add("-|-|-|-|-|-|-|-|-");
        init.add("-|-|-|-|-|*|-|-|-");
        init.add("*|*|-|-|-|-|-|-|*");
        init.add("-|*|-|-|*|-|-|-|*");
        init.add("-|-|-|*|-|-|-|-|*");
        init.add("-|-|-|-|-|-|-|-|-");
        init.add("-|-|-|-|-|-|*|-|-");
        init.add("-|-|*|-|-|-|-|-|-");
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
                cell.status = Cell.STATUS.OPEN;
                if(value.equals("-")||value.equals("*")){
                    cell.status = Cell.STATUS.CLOSE;
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

}
