package com.app.minesweeper;

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

    //單元測試：開始遊戲
    @Test
    public void startGame(){
        int level = 9;
        CellCreator cellCreator = new CellCreator();
        cellCreator.level = level;
        mineSweeper.startGame(cellCreator);
        ArrayList<Cell> cells = mineSweeper.cells;
        Assert.assertEquals(81,cells.size());
    }

    //單元測試：點擊格子，變成打開狀態
    @Test
    public void tapCellShouldOpen(){
        ArrayList<String> init = new ArrayList<>();
        init.add("-");
        ArrayList<Cell> cells = createCell(init);

        ICellCreator creator = new FakeCellCreator();
        ((FakeCellCreator) creator).cells = cells;
        mineSweeper.startGame(creator);

        mineSweeper.tap(0,0);

        ArrayList<String> verify = new ArrayList<>();
        verify.add(" ");
        verifyDisplay(verify);
    }

    // 利用圖示字串做驗證
    private void verifyDisplay(ArrayList<String> verify) {
        for (int y=0; y<verify.size(); y++) {
            String[] ylist = verify.get(y).split("\\|");
            for(int x=0; x<ylist.length; x++){
                String value = ylist[x];
                Cell findCell = mineSweeper.getCell(x,y);
                assert findCell != null;
                switch (value){
                    case "-":
                        Assert.assertEquals("$x, $y", CellStatus.CLOSE, findCell.status);
                        break;
                    case " ":
                        Assert.assertEquals("$x, $y", CellStatus.OPEN, findCell.status);
                        break;
                    case  "*":
                        Assert.assertTrue("$x, $y", findCell.isMine);
                        break;
                    case  "f":
                        Assert.assertTrue("$x, $y", findCell.isFlagged);
                        break;
                    default:
                        Assert.assertEquals("$x, $y", CellStatus.OPEN, findCell.status);
                        Assert.assertEquals("$x, $y", value, Integer.toString(findCell.nextMines));

                }
            }
        }
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
                if(value.equals("f")){
                    cell.isFlagged = true;
                }
                cells.add(cell);
            }
        }
        System.out.println(cells);
        return cells;
    }

    //單元測試：點擊格子，若周圍有炸彈，顯示炸彈數量
    @Test
    public void tapNumberShouldDisplay(){
        ArrayList<String> init = new ArrayList<>();
        init.add("*|-|-|-|-");
        init.add("-|-|-|-|-");
        init.add("-|*|-|-|-");
        init.add("-|-|-|-|-");
        init.add("-|-|*|-|-");
        ArrayList<Cell> cells = createCell(init);

        ICellCreator creator = new FakeCellCreator();
        ((FakeCellCreator) creator).cells = cells;
        mineSweeper.startGame(creator);

        mineSweeper.tap(0,1);

        ArrayList<String> verify = new ArrayList<>();
        verify.add("*|-|-|-|-");
        verify.add("2|-|-|-|-");
        verify.add("-|*|-|-|-");
        verify.add("-|-|-|-|-");
        verify.add("-|-|*|-|-");
        verifyDisplay(verify);
    }

    //單元測試：點擊格子，若為周圍炸彈數量為0，自動打開周圍格子
    @Test
    public void tapCellShouldDisplayNextMines(){
        ArrayList<String> init = new ArrayList<>();
        init.add("*|-|-|-|-");
        init.add("-|-|-|-|-");
        init.add("-|*|-|-|-");
        init.add("-|-|-|-|-");
        init.add("-|-|*|-|-");
        ArrayList<Cell> cells = createCell(init);

        ICellCreator creator = new FakeCellCreator();
        ((FakeCellCreator) creator).cells = cells;
        mineSweeper.startGame(creator);

        mineSweeper.tap(0,4);

        ArrayList<String> verify = new ArrayList<>();
        verify.add("*|-|-|-|-");
        verify.add("-|-|-|-|-");
        verify.add("-|*|-|-|-");
        verify.add("1|2|-|-|-");
        verify.add(" |1|*|-|-");
        verifyDisplay(verify);
    }

    //單元測試：點擊格子，若為周圍炸彈數量為0，自動打開周圍格子；若打開的格子周圍炸彈數量亦為0，再繼續打開周圍格子
    @Test
    public void tapIfNextIs0ThenOpen(){
        ArrayList<String> init = new ArrayList<>();
        init.add("*|-|-|-|-");
        init.add("-|-|-|-|-");
        init.add("-|*|-|-|-");
        init.add("-|-|-|-|-");
        init.add("-|-|*|-|-");
        ArrayList<Cell> cells = createCell(init);

        ICellCreator creator = new FakeCellCreator();
        ((FakeCellCreator) creator).cells = cells;
        mineSweeper.startGame(creator);

        mineSweeper.tap(3,2);

        ArrayList<String> verify = new ArrayList<>();
        verify.add("*|1| | | ");
        verify.add("-|2|1| | ");
        verify.add("-|*|1| | ");
        verify.add("-|-|2|1| ");
        verify.add("-|-|*|1| ");
        verifyDisplay(verify);
    }

    //單元測試：插旗子
    @Test
    public void tagFlagTest(){
        ArrayList<String> init = new ArrayList<>();
        init.add("*");
        ArrayList<Cell> cells = createCell(init);

        ICellCreator creator = new FakeCellCreator();
        ((FakeCellCreator) creator).cells = cells;
        mineSweeper.startGame(creator);

        mineSweeper.tapFlag(0,0);

        ArrayList<String> verify = new ArrayList<>();
        verify.add("f");
        verifyDisplay(verify);
    }

    //單元測試：取消旗子
    @Test
    public void removeFlagTest(){
        ArrayList<String> init = new ArrayList<>();
        init.add("*");
        ArrayList<Cell> cells = createCell(init);

        ICellCreator creator = new FakeCellCreator();
        ((FakeCellCreator) creator).cells = cells;
        mineSweeper.startGame(creator);

        mineSweeper.tapFlag(0,0); // 插旗
        mineSweeper.tapFlag(0,0); // 拔旗

        ArrayList<String> verify = new ArrayList<>();
        verify.add("*");
        verifyDisplay(verify);
    }

    //單元測試：點擊格子，若格子有炸彈，YOU LOSE
    @Test
    public void loseGameTest(){
        ArrayList<String> init = new ArrayList<>();
        init.add("*|-");
        init.add("-|-");
        ArrayList<Cell> cells = createCell(init);

        ICellCreator creator = new FakeCellCreator();
        ((FakeCellCreator) creator).cells = cells;
        mineSweeper.startGame(creator);

        mineSweeper.tap(0,0); // 踩到地雷

        Assert.assertEquals(MineSweeper.STATUS.DIE, mineSweeper.status);
    }

    //單元測試：所有沒地雷的格子都打開，YOU WIN
    @Test
    public void winGameTest(){
        ArrayList<String> init = new ArrayList<>();
        init.add("*|-");
        init.add("-|-");
        ArrayList<Cell> cells = createCell(init);

        ICellCreator creator = new FakeCellCreator();
        ((FakeCellCreator) creator).cells = cells;
        mineSweeper.startGame(creator);

        mineSweeper.tap(1,0);
        mineSweeper.tap(0,1);
        mineSweeper.tap(1,1);

        Assert.assertEquals(MineSweeper.STATUS.WIN,mineSweeper.status);
    }

    //單元測試：沒地雷的格子尚未全部打開，遊戲繼續
    @Test
    public void testGameContinues(){
        ArrayList<String> init = new ArrayList<>();
        init.add("*|-");
        init.add("-|-");
        ArrayList<Cell> cells = createCell(init);

        ICellCreator creator = new FakeCellCreator();
        ((FakeCellCreator) creator).cells = cells;
        mineSweeper.startGame(creator);

        mineSweeper.tap(1,0);
        Assert.assertEquals(MineSweeper.STATUS.PLAYING,mineSweeper.status);
    }
}
