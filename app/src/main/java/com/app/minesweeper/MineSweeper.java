package com.app.minesweeper;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class MineSweeper implements Parcelable {

    ArrayList<Cell> cells = new ArrayList<>();
    public STATUS status = null;

////////////////// ↓ 實作 Parcelable 必要 ↓ //////////////////
    public MineSweeper(){

    }

    // 讀取
    protected MineSweeper(Parcel in) {
        in.readTypedList(cells, Cell.CREATOR);
        this.status = STATUS.valueOf(in.readString());

        getClass().getClassLoader();
        Thread.currentThread().getContextClassLoader();
        Cell.class.getClassLoader();
    }

    public static final Creator<MineSweeper> CREATOR = new Creator<MineSweeper>() {
        @Override
        public MineSweeper createFromParcel(Parcel in) {
            return new MineSweeper(in);
        }

        @Override
        public MineSweeper[] newArray(int size) {
            return new MineSweeper[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    // 寫入
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeTypedList(cells); // 筆記：
        parcel.writeString(this.status.name());
    }

////////////////// ↑ 實作 Parcelable 必要 ↑ /////////////////

    public enum STATUS {
        PLAYING, DIE, WIN
    }

    // 使開始遊戲
    public void startGame(ICellCreator cellCreator) {
        cells = cellCreator.create();
        for (Cell cell : cells) {
            setCellNextStatus(cell);
        }
        this.status = STATUS.PLAYING;
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
        checkGameResult(cell);
        openCellsAround(xIndex, yIndex);
    }

    // 打開周圍格子
    private void openCellsAround(int xIndex, int yIndex) {
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

    // 檢查遊戲結果
    private void checkGameResult(Cell cell) {
        // 你贏了!
        if (allSafeCellsOpened(cells)) this.status = STATUS.WIN;
        // 你輸了!
        if (cell.isMine) this.status = STATUS.DIE;
    }

    private Boolean allSafeCellsOpened(ArrayList<Cell> cells) {
        for (Cell checkCell : cells) {
            if (!checkCell.isMine && checkCell.status == Cell.STATUS.CLOSE) {
                return false;
            }
        }
        return true;
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

    // 插旗拔旗
    public void tapFlag(int xIndex, int yIndex) {
        Cell cell = getCell(xIndex, yIndex);
        cell.isFlagged = !cell.isFlagged;
    }
}
