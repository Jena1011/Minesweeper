package com.app.minesweeper;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * 一個 RecyclerView 的 Adapter，用於顯示地雷方格
 * 該 Adapter 負責處理每個方格的狀態和外觀，以及將點擊事件傳遞到 GameFragment。
 * 它還通過檢查遊戲的當前狀態來防止在遊戲結束後點擊方塊。
*/
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private final ArrayList<Cell> localDataSet; // 儲存方格資訊的陣列
    ICellTapListener listener = null; // 方格點擊監聽器
    MineSweeper mineSweeper; // 踩地雷遊戲主體

    /**
     * 該ViewHolder表示RecyclerView中的單個項目，即單個地雷方塊。
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_cell; // 方格內的文字
        private final ImageView iv_cell; // 方格內的圖片

        // 構造函數，初始化視圖元素
        public ViewHolder(View view) {
            super(view);
            tv_cell = view.findViewById(R.id.tv_cell);
            iv_cell = view.findViewById(R.id.iv_cell);
        }
    }

    /**
     * 構造函數，初始化本地數據集和 Minesweeper 物件。
     * @param mineSweeper Minesweeper 物件，它包含地雷方塊的數據。
     */
    public MainAdapter(MineSweeper mineSweeper) {
        this.localDataSet = mineSweeper.cells;
        this.mineSweeper = mineSweeper;
    }

    /**
     * 創建ViewHolder對象，並將cell_item佈局設置為項目的佈局。
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cell_item, viewGroup, false);
        view.setEnabled(viewGroup.isEnabled());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // 初始化
        viewHolder.tv_cell.setVisibility(View.GONE);
        viewHolder.iv_cell.setVisibility(View.GONE);
        Cell cell = localDataSet.get(position);
        // 方格未開啟
        if (cell.status == CellStatus.CLOSE) {
            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
            if(cell.isFlagged){
                viewHolder.iv_cell.setImageResource(R.drawable.flag);
                viewHolder.iv_cell.setVisibility(View.VISIBLE);
            }
        }
        // 方格開啟
        if (cell.status == CellStatus.OPEN) {
            viewHolder.itemView.setBackgroundColor(Color.WHITE);
            // 方格中有地雷
            if (cell.isMine) {
                viewHolder.iv_cell.setVisibility(View.VISIBLE);
                viewHolder.iv_cell.setImageResource(R.drawable.mine);
                // 附近地雷數不為0
            } else if (cell.nextMines != 0) {
                viewHolder.tv_cell.setVisibility(View.VISIBLE);
                viewHolder.tv_cell.setText(String.valueOf(cell.nextMines));
            }
        }

        // 設定方格被點擊要執行的動作
        viewHolder.itemView.setOnClickListener(view -> {
            if(listener!=null){
                listener.onCellClick(cell);
            }
        });

        // 設定方格長按要執行的動作
        viewHolder.itemView.setOnLongClickListener(view -> {
            if(listener!=null){
                listener.onCellLongClick(cell);
                return true;
            }
            return false;
        });

        // game over 後不能點擊方格
        if(mineSweeper.status== GameStatus.DIE){
            viewHolder.itemView.setEnabled(false);
        }
    }

    void setCellListener(ICellTapListener listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

}
