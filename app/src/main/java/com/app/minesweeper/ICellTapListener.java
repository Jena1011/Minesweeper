package com.app.minesweeper;
/**
 用於點擊地雷區單元格的 listener interface
 */
public interface ICellTapListener {

    /**
     當地雷區單元格被點擊時調用。
     @param cell 被點擊的單元格。
     */
    void onCellClick(Cell cell);

    /**
     當地雷區單元格被長按時調用。
     @param cell 被長按的單元格。
     */
    void onCellLongClick(Cell cell);

}
