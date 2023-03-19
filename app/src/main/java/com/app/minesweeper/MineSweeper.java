package com.app.minesweeper;

import java.util.ArrayList;

public class MineSweeper {

    ArrayList<Cell> cells = new ArrayList<>();

    // 使開始遊戲
    public void startGame(ICellCreator cellCreator) {
        cells = cellCreator.create();
        for (Cell cell : cells) {
            setCellNextStatus(cell);
        }
    }

    // 找出周遭地雷數量
    private void setCellNextStatus(Cell cell) {
        for (int x = cell.getX() - 1; x <= cell.getX() + 1; x++) {
            for (int y = cell.getY() - 1; y <= cell.getY() + 1; y++) {
                if (getCell(x, y).status == null) {
                    continue;
                }
                if (cell.getX() == x && cell.getY() == y) {
                    continue;
                }
                if (getCell(x, y).isMine) {
                    cell.nextMines++;
                }
            }
        }
    }

    // 按下方格
    public void tap(int xIndex, int yIndex) {
        Cell cell = getCell(xIndex, yIndex);
        cell.status = Cell.STATUS.OPEN;
        if (getCell(xIndex, yIndex).nextMines == 0) {
            for (int x = xIndex - 1; x <= xIndex + 1; x++) {
                for (int y = yIndex - 1; y <= yIndex + 1; y++) {
                    Cell nextCell = getCell(x, y);
                    if (nextCell.status == null) {
                        continue;
                    }
                    if (x == xIndex && y == yIndex) {
                        continue;
                    }
                    if (!nextCell.isMine && nextCell.status == Cell.STATUS.CLOSE) {
                        if (nextCell.nextMines == 0) {
                            tap(x, y);
                        } else {
                            nextCell.status = Cell.STATUS.OPEN;
                        }
                    }
                }
            }
        }
    }

    // 由x,y座標取得目標方格物件
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

    public void tapFlag(int xIndex, int yIndex) {
        Cell cell = getCell(xIndex, yIndex);
        cell.isFlagged = true;
    }
}
