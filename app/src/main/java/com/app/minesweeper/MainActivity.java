package com.app.minesweeper;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity implements ICellTapListener {

    RecyclerView rv_cells;
    TextView tv_gameStatus;
    Button bt_restart;
    MineSweeper mineSweeper;
    MainAdapter mainAdapter;
    private final String KEY_MINESWEEPER = "mineSweeper_key";
    private final String KEY_ISRECREATE = "isRecreate_key";
    private final String KEY_GAMESTATUS = "gameStatus_key";
    boolean isRecreate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_cells = findViewById(R.id.rv_cells);
        tv_gameStatus = findViewById(R.id.tv_gameStatus);
        bt_restart = findViewById(R.id.bt_restart);

        if(savedInstanceState!=null){
            isRecreate = savedInstanceState.getBoolean(KEY_ISRECREATE, false);
        }
        if(savedInstanceState==null||isRecreate){
            startGame();
        }else {
            mineSweeper = savedInstanceState.getParcelable(KEY_MINESWEEPER);
        }

        mainAdapter = new MainAdapter(mineSweeper);

        mainAdapter.setCellListener(this);

        rv_cells.setAdapter(mainAdapter);
        rv_cells.setLayoutManager(new GridLayoutManager(this, 9));
        setStatusText();

        bt_restart.setOnClickListener(view -> {
            isRecreate = true;
            recreate();
        });
    }

    // 開始遊戲
    private void startGame() {
        mineSweeper = new MineSweeper();
        int level = 9;
        CellCreator cellCreator = new CellCreator();
        cellCreator.level = level;
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
            tv_gameStatus.setText(R.string.gameOver);
            tv_gameStatus.setTextColor(Color.rgb(255, 0, 0));
        } else if (mineSweeper.status == MineSweeper.STATUS.WIN) {
            tv_gameStatus.setText(R.string.congratulation);
            tv_gameStatus.setTextColor(Color.rgb(0, 150, 0));
        } else {
            tv_gameStatus.setText("");
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_ISRECREATE,isRecreate);
        if (mineSweeper != null) {
            outState.putParcelable(KEY_MINESWEEPER, mineSweeper);
        }
        if(tv_gameStatus!=null){
            outState.putString(KEY_GAMESTATUS,tv_gameStatus.getText().toString());
        }
    }
}