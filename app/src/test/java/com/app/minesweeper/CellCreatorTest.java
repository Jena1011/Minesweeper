package com.app.minesweeper;

import androidx.annotation.NonNull;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class CellCreatorTest {

    // 單元測試：創造符合size的格子數
    @Test
    public void test_createCell(){
        Assert.assertEquals(36,new CellCreator("6X6","hard").create().size());
        Assert.assertEquals(81,new CellCreator("9X9","hard").create().size());
        Assert.assertEquals(117,new CellCreator("9X13","hard").create().size());
    }

    // 單元測試：一開始所有格子必須關閉
    @Test
    public void test_cellShouldClose(){
        CellCreator cellCreator = new CellCreator("9X9","hard");
        ArrayList<Cell> cells = cellCreator.create();
       int closeCount = 0;
        for(Cell cell:cells){
            if(cell.status == CellStatus.CLOSE){
                closeCount++;
            }
        }
        Assert.assertEquals(81,closeCount);
    }

    // 單元測試：難度 hard 15% 的格子埋有地雷
    @Test
    public void test_hard15PercentMine(){
        CellCreator cellCreator = new CellCreator("9X9","hard");
        int mineCount = getMineCount(cellCreator);
        Assert.assertEquals(13,mineCount);
    }

    // 單元測試：難度 normal 12% 的格子埋有地雷
    @Test
    public void test_normal12PercentMine(){
        CellCreator cellCreator = new CellCreator("9X9","normal");
        int mineCount = getMineCount(cellCreator);
        Assert.assertEquals(10,mineCount);
    }

    // 單元測試：難度 easy 9% 的格子埋有地雷
    @Test
    public void test_easy9PercentMine(){
        CellCreator cellCreator = new CellCreator("9X9","easy");
        int mineCount = getMineCount(cellCreator);
        Assert.assertEquals(8,mineCount);
    }

    /**
     * 利用 cellCreator 產生 ArrayList<Cell>，計算並返回列表中的地雷總數
     * @param cellCreator Cell產生器
     * @return 產生的地雷總數
     */
    private int getMineCount(CellCreator cellCreator) {
        ArrayList<Cell> cells = cellCreator.create();
        int mineCount = 0;
        for(Cell cell:cells){
            if(cell.isMine ){
                mineCount++;
            }
        }
        return mineCount;
    }
}
