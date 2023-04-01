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

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private final ArrayList<Cell> localDataSet;
    ICellTapListener listener = null;
    MineSweeper mineSweeper;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_cell;
        private final ImageView iv_cell;

        public ViewHolder(View view) {
            super(view);
            tv_cell = view.findViewById(R.id.tv_cell);
            iv_cell = view.findViewById(R.id.iv_cell);
        }
    }

    public MainAdapter(MineSweeper mineSweeper) {
        this.localDataSet = mineSweeper.cells;
        this.mineSweeper = mineSweeper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cell_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // 初始化
        viewHolder.tv_cell.setVisibility(View.GONE);
        viewHolder.iv_cell.setVisibility(View.GONE);
        Cell cell = localDataSet.get(position);
        // 方格未開啟
        if (cell.status == Cell.STATUS.CLOSE) {
            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
            if(cell.isFlagged){
                viewHolder.iv_cell.setImageResource(R.drawable.flag);
                viewHolder.iv_cell.setVisibility(View.VISIBLE);
            }
        }
        // 方格開啟
        if (cell.status == Cell.STATUS.OPEN) {
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
        if(mineSweeper.status== MineSweeper.STATUS.DIE){
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
