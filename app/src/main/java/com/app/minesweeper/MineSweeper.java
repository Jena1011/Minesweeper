package com.app.minesweeper;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * MineSweeper 是一個踩地雷遊戲的邏輯程式
 * 這個程式碼定義了 MineSweeper 類別，該類別代表整個地雷遊戲的邏輯，包括地圖的生成、遊戲結果判斷等等。
 * MineSweeper 實現了 Parcelable 介面，以便在 Activity 和 Fragment 等元件之間傳遞
 */
public class MineSweeper implements Parcelable {

    ArrayList<Cell> cells = new ArrayList<>(); // 儲存方格資訊的陣列
    public GameStatus status = null; // 當前遊戲狀態

    public MineSweeper(){

    }
    protected MineSweeper(Parcel in) {
        in.readTypedList(cells, Cell.CREATOR);
        this.status = GameStatus.valueOf(in.readString());
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

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeTypedList(cells);
        parcel.writeString(this.status.name());
    }


    /**
     * 開始遊戲，使用傳入的 ICellCreator 實例建立方格(Cell)物件。
     * @param cellCreator 方格產生器，負責建立方格(Cell)物件。
     */
    public void startGame(ICellCreator cellCreator) {
        cells = cellCreator.create();
        for (Cell cell : cells) {
            setCellNextStatus(cell);
        }
        this.status = GameStatus.PLAYING;
    }

    /**
     * 找出指定方格周圍地雷的數量
     * @param cell 要處理的方格
     */
    private void setCellNextStatus(Cell cell) {
        for (int x = cell.x - 1; x <= cell.x + 1; x++) {
            for (int y = cell.y - 1; y <= cell.y + 1; y++) {
                if (getCell(x, y).status == null) {
                    continue;
                }
                if (cell.x == x && cell.y == y) {
                    continue;
                }
                if (getCell(x, y).isMine) {
                    cell.nextMines++;
                }
            }
        }
    }

    /**
     * 按下指定方格
     * @param xIndex 指定方格的 x 座標
     * @param yIndex 指定方格的 y 座標
     */
    public void tap(int xIndex, int yIndex) {
        Cell cell = getCell(xIndex, yIndex);
        if(cell.status == CellStatus.OPEN) return;
        cell.status = CellStatus.OPEN;
        checkGameResult(cell);
        openCellsAround(xIndex, yIndex);
    }

    /**
     * 打開指定方格周圍的方格
     * @param xIndex 指定方格的 x 座標
     * @param yIndex 指定方格的 y 座標
     */
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
                    if (!nextCell.isMine && nextCell.status == CellStatus.CLOSE) {
                        if (nextCell.nextMines == 0) {
                            tap(x, y);
                        } else {
                            nextCell.status = CellStatus.OPEN;
                        }
                    }
                }
            }
        }
    }

    /**
     * 檢查遊戲結果，更新遊戲狀態
     * @param cell 目前被操作的方格
     */
    private void checkGameResult(Cell cell) {
        // 你贏了!
        if (allSafeCellsOpened(cells)) this.status = GameStatus.WIN;
        // 你輸了!
        if (cell.isMine) this.status = GameStatus.DIE;
    }

    /**
     * 檢查是否所有無地雷的方格都已經打開
     * @param cells 目前所有方格
     */
    private Boolean allSafeCellsOpened(ArrayList<Cell> cells) {
        for (Cell checkCell : cells) {
            if (!checkCell.isMine && checkCell.status == CellStatus.CLOSE) {
                return false;
            }
        }
        return true;
    }

    /**
     * 由 x,y 座標取得指定方格的 Cell 物件
     * @param x 指定方格的 x 座標
     * @param y 指定方格的 y 座標
     */
    public Cell getCell(int x, int y) {
        Cell cell = new Cell();
        for (Cell c : cells) {
            if (c.x == x && c.y == y) {
                cell = c;
                break;
            }
        }
        return cell;
    }

    /**
     * 對指定方格修改插旗狀態。若原本有旗子，則拔旗；若無，則插旗。
     * @param xIndex 指定方格的 x 座標
     * @param yIndex 指定方格的 y 座標
     */
    public void tapFlag(int xIndex, int yIndex) {
        Cell cell = getCell(xIndex, yIndex);
        cell.isFlagged = !cell.isFlagged;
    }
}
