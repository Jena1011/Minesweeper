package com.app.minesweeper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Objects;

public class MineSweeper {

    ArrayList<Cell> cells = new ArrayList<>();

    public void startGame(ICellCreator cellCreator) {
        cells = cellCreator.create();
        for(Cell cell:cells){
            setCellNextStatus(cell);
        }
    }

    private void setCellNextStatus(Cell cell) {
        for(int x=cell.getX()-1; x<=cell.getX()+1; x++){
            for (int y= cell.getY()-1; y<= cell.getY()+1; y++){
                if (getCell(x,y).isMine){
                    cell.nextMines ++;
                }
            }
        }
    }

    public void tap(int x, int y) {
        Objects.requireNonNull(getCell(x, y)).status = Cell.STATUS.OPEN;
    }


    public Cell getCell(int x, int y) {
        Cell cell = new Cell();
        for (Cell c : cells) {
            if (c.getX() == x && c.getY() == y) {
                cell = c;
                break;
            }
        }
        return cell;
    }
}
