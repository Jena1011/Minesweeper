package com.app.minesweeper;

import androidx.annotation.Nullable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class MineSweeperTest {

    private MineSweeper mineSweeper;

    @Before
    public void setUp() {
        mineSweeper = new MineSweeper();
    }

    //檢驗：開始遊戲
    @Test
    public void startGame(){
        int level = 9;
        CellCreator cellCreator = new CellCreator();
        cellCreator.level = level;
        mineSweeper.startGame(cellCreator);
        ArrayList<Cell> cells = mineSweeper.cells;
        Assert.assertEquals(81,cells.size());
    }

    //檢驗：點擊格子，變成打開狀態
    @Test
    public void tapCellShouldOpen(){
//        ArrayList<Cell> cells = new ArrayList<>();
//        cells.add(new Cell(0,0));
        ArrayList<String> init = new ArrayList<>();
        init.add("-");
        ArrayList<Cell> cells = createCell(init);

        ICellCreator creator = new FakeCellCreator();
        ((FakeCellCreator) creator).cells = cells;
        mineSweeper.startGame(creator);

        int x = 0;
        int y = 0;

        mineSweeper.tap(x,y);

        ArrayList<String> verify = new ArrayList<>();
        verify.add(" ");
        verifyDisplay(verify);

    }

    // 利用圖示字串做驗證
    private void verifyDisplay(ArrayList<String> verify) {
        for (int x=0; x<verify.size(); x++) {
            String[] ylist = verify.get(x).split("\\|");
            for(int y=0; y<ylist.length; y++){
                String value = ylist[y];
                Cell findCell = mineSweeper.getCell(x,y);
                switch (value){
                    case " ":
                        Assert.assertEquals("$x, $y", Cell.STATUS.OPEN, findCell.status);
                        break;
                    case "-":
                        Assert.assertEquals("$x, $y", Cell.STATUS.CLOSE, findCell.status);
                        break;
                }
            }
        }
    }

    // 將圖示字串轉為 ArrayList<Cell>
    private ArrayList<Cell> createCell(ArrayList<String> initSweeper) {
        ArrayList<Cell> cells = new ArrayList<>();
        for (int x=0; x<initSweeper.size(); x++) {
            String[] ylist = initSweeper.get(x).split("|");
            for(int y=0; y<ylist.length; y++){
                Cell cell = new Cell(x,y);
                String value = ylist[y];
                cell.status = Cell.STATUS.CLOSE;
                if(value==" "){
                    cell.status = Cell.STATUS.OPEN;
                }
                cells.add(cell);
            }
        }
        return cells;
    }


    //檢驗：點擊格子，若為周圍炸彈數量為0，自動打開周圍格子

    //檢驗：點擊格子，若周圍有炸彈，顯示炸彈數量

    //檢驗：點擊格子，若格子有炸彈，顯示 Game Over

    //檢驗：長按格子，顯示旗子圖示

}
