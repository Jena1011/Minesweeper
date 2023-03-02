package com.app.minesweeper;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class MineSweeperTest {

    //檢驗：開始遊戲
    @Test
    public void startGame(){
        int level = 9;
        MineSweeper mineSweeper = new MineSweeper();
        mineSweeper.startGame(level);
        ArrayList<Cell> cells = mineSweeper.cells;
        Assert.assertEquals(81,cells.size());
    }

    //檢驗：點擊格子，變成打開狀態

    //檢驗：點擊格子，若為周圍炸彈數量為0，自動打開周圍格子

    //檢驗：點擊格子，若周圍有炸彈，顯示炸彈數量

    //檢驗：點擊格子，若格子有炸彈，顯示 Game Over

    //檢驗：長按格子，顯示旗子圖示

}
