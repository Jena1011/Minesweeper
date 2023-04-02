package com.app.minesweeper;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.minesweeper.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements ICellTapListener {

    // TODO: 把遊戲內容搬運到 GameFragment
    MineSweeper mineSweeper;
    MainAdapter mainAdapter;
    private final String KEY_MINESWEEPER = "mineSweeper_key";
    private ActivityMainBinding binding;
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);

        // 隱藏狀態列
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(savedInstanceState==null){
            startGame();
        }else {
            mineSweeper = savedInstanceState.getParcelable(KEY_MINESWEEPER);
        }

        setRVAdapter(mineSweeper);

        binding.rvCells.setLayoutManager(new GridLayoutManager(this, 9));

        binding.btRestart.setOnClickListener(view -> resetGame());
        setStatusText();
    }

    // rv 設定 mainAdapter
    private void setRVAdapter(MineSweeper mineSweeper) {
        mainAdapter = new MainAdapter(mineSweeper);
        mainAdapter.setCellListener(this);
        binding.rvCells.setAdapter(mainAdapter);
    }

    // 重新開始
    private void resetGame() {
        if(mineSweeper!=null){
            mineSweeper.cells.clear();
            CellCreator cellCreator = new CellCreator();
            cellCreator.level= 9;
            mineSweeper.startGame(cellCreator);
//            mineSweeper.startGame(new CellCreator(9));
        }
        if(mainAdapter!=null){
            setRVAdapter(mineSweeper);
            mainAdapter.notifyDataSetChanged();
        }
        setStatusText();
    }

    // 開始遊戲
    private void startGame() {
        mineSweeper = new MineSweeper();
        CellCreator cellCreator = new CellCreator();
        cellCreator.level= 9;
        mineSweeper.startGame(cellCreator);
//            mineSweeper.startGame(new CellCreator(9));
        mineSweeper.startGame(cellCreator);
    }

    // 監聽點擊格子的事件 (實作ICellTapListener方法)
    @Override
    public void onCellClick(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        mineSweeper.tap(x, y);
        mainAdapter.notifyItemRangeChanged(0, 81, Cell.class);

        setStatusText();
    }

    // 設定狀態文字
    private void setStatusText() {
        if (mineSweeper.status == MineSweeper.STATUS.DIE) {
            binding.tvGameStatus.setText(R.string.gameOver);
            binding.tvGameStatus.setTextColor(Color.rgb(255, 0, 0));
        } else if (mineSweeper.status == MineSweeper.STATUS.WIN) {
            binding.tvGameStatus.setText(R.string.congratulation);
            binding.tvGameStatus.setTextColor(Color.rgb(0, 150, 0));
        } else {
            binding.tvGameStatus.setText("");
        }
    }

    // 監聽長按格子的事件 (實作ICellTapListener方法)
    @Override
    public void onCellLongClick(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        mineSweeper.tapFlag(x, y);
        mainAdapter.notifyItemChanged(y * 9 + x);
    }

    // 資料暫存
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mineSweeper != null) {
            outState.putParcelable(KEY_MINESWEEPER, mineSweeper); // 傳送物件，該物件類要實作 Parcelable 介面
        }
        String KEY_GAME_STATUS = "gameStatus_key";
        outState.putString(KEY_GAME_STATUS,binding.tvGameStatus.getText().toString());
    }
}