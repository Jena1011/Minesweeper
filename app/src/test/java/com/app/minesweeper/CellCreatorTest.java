package com.app.minesweeper;

import androidx.annotation.NonNull;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class CellCreatorTest {

    // 單元測試：創造符合size的格子數
    @Test
    public void test_createCell(){
        Assert.assertEquals(36,new CellCreator("6X6").create().size());
        Assert.assertEquals(81,new CellCreator("9X9").create().size());
        Assert.assertEquals(117,new CellCreator("9X13").create().size());
    }

    // 單元測試：一開始所有格子必須關閉
    @Test
    public void test_cellShouldClose(){
        CellCreator cellCreator = new CellCreator("9X9");
        ArrayList<Cell> cells = cellCreator.create();
       int closeCount = 0;
        for(Cell cell:cells){
            if(cell.status == CellStatus.CLOSE){
                closeCount++;
            }
        }
        Assert.assertEquals(81,closeCount);
    }

    // 單元測試：15%的格子埋有地雷
    @Test
    public void test_cellShouldHave15PercentMine(){
        CellCreator cellCreator = new CellCreator("9X9");
        ArrayList<Cell> cells = cellCreator.create();
        int mineCount = 0;
        for(Cell cell:cells){
            if(cell.isMine ){
                mineCount++;
            }
        }
        Assert.assertEquals(13,mineCount);
    }
}
