package com.app.minesweeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ICellTapListener {

    RecyclerView rv_cells;
    MineSweeper mineSweeper;
    MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_cells = findViewById(R.id.rv_cells);
        mineSweeper = new MineSweeper();
        int level = 9;
        CellCreator cellCreator = new CellCreator();
        cellCreator.level = level;
        mineSweeper.startGame(cellCreator);
        mainAdapter =  new MainAdapter(mineSweeper.cells);

        /* 筆記：MainAdapter本身並不知道使用者點擊的是哪個格子，所以需要透過ICellTapListener這個介面將使用者點擊的格子傳遞給MainActivity。
           因此，在MainActivity中，需要實作ICellTapListener這個介面，以便能夠接收來自MainAdapter的使用者點擊事件。
        */
        mainAdapter.setCellListener(this);

        rv_cells.setAdapter(mainAdapter);
        rv_cells.setLayoutManager(new GridLayoutManager(this,9));

    }

    // 監聽點擊格子的事件
    @Override
    public void onCellClick(Cell cell) {
        mineSweeper.tap(cell.getX(),cell.getY());
        mainAdapter.notifyDataSetChanged();
    }

}