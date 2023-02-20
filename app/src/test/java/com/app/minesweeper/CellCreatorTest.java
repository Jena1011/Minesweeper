package com.app.minesweeper;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class CellCreatorTest {
    @Test
    public void createCell(){
        CellCreator cellCreator = new CellCreator();
        cellCreator.level = 9;
        ArrayList<Cell> cells = cellCreator.create();
        Assert.assertEquals(81,cells.size());
    }
}
