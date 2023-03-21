package com.app.minesweeper;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements ICellTapListener {

    RecyclerView rv_cells;
    TextView tv_gameStatus;
    Button bt_restart;
    MineSweeper mineSweeper;
    MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_cells = findViewById(R.id.rv_cells);
        tv_gameStatus = findViewById(R.id.tv_gameStatus);
        bt_restart = findViewById(R.id.bt_restart);

        mineSweeper = new MineSweeper();
        int level = 9;
        CellCreator cellCreator = new CellCreator();
        cellCreator.level = level;
        mineSweeper.startGame(cellCreator);
        mainAdapter = new MainAdapter(mineSweeper.cells);

        /* 筆記：MainAdapter本身並不知道使用者點的是哪個格子，所以要透過ICellTapListener這個介面將使用者點擊的格子傳遞給MainActivity。
           因此，在MainActivity中，需要實作ICellTapListener這個介面，以便能夠接收來自MainAdapter的使用者點擊事件。
        */
        mainAdapter.setCellListener(this);

        rv_cells.setAdapter(mainAdapter);
        rv_cells.setLayoutManager(new GridLayoutManager(this, 9));

        bt_restart.setOnClickListener(view -> recreate());

    }

    // 監聽點擊格子的事件 (實作ICellTapListener方法)
    @Override
    public void onCellClick(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        mineSweeper.tap(x, y);
        mainAdapter.notifyItemRangeChanged(0,81,Cell.class);

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
}