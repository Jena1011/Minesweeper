package com.app.minesweeper;

import androidx.annotation.NonNull;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class CellCreatorTest {

    //檢驗：創造符合level的格子數
    @Test
    public void testCreateCell(){
        CellCreator cellCreator = createLevelCell(9);
        ArrayList<Cell> cells = cellCreator.create();
        Assert.assertEquals(81,cells.size());
    }

    //檢驗：一開始所有格子必須關閉
    @Test
    public void cell_should_close(){
        CellCreator cellCreator = new CellCreator();
        cellCreator.level = 9;
        ArrayList<Cell> cells = cellCreator.create();
       int closeCount = 0;
        for(Cell cell:cells){
            if(cell.status == Cell.STATUS.CLOSE){
                closeCount++;
            }
        }
        Assert.assertEquals(81,closeCount);
    }

    //檢驗：15%的格子埋有地雷
    @Test
    public void cell_should_have_15_percent_mine(){
        CellCreator cellCreator = new CellCreator();
        cellCreator.level = 9;
        ArrayList<Cell> cells = cellCreator.create();
        int mineCount = 0;
        for(Cell cell:cells){
            if(cell.isMine ){
                mineCount++;
            }
        }
        Assert.assertEquals(13,mineCount);
    }

    @NonNull
    private CellCreator createLevelCell(int level) {
        CellCreator cellCreator = new CellCreator();
        cellCreator.level = level;
        return cellCreator;
    }
}
