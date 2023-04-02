package com.app.minesweeper;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.minesweeper.databinding.FragmentGameBinding;
import com.app.minesweeper.databinding.FragmentMenuBinding;

public class GameFragment extends Fragment implements ICellTapListener{

    private FragmentGameBinding binding = null ;

    private MineSweeper mineSweeper;
    private MainAdapter mainAdapter;
    private final String KEY_MINESWEEPER = "mineSweeper_key";

    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGameBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View gameView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(gameView, savedInstanceState);

        binding = FragmentGameBinding.bind(gameView); // 要用靜態方法綁定

        if(savedInstanceState==null){
            startGame();
        }else {
            mineSweeper = savedInstanceState.getParcelable(KEY_MINESWEEPER);
        }

        setRVAdapter(mineSweeper);

        binding.rvCells.setLayoutManager(new GridLayoutManager(this.requireContext(), 9));

        binding.btRestart.setOnClickListener(view -> resetGame());
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

    // 監聽點擊格子的事件 (實作ICellTapListener方法)
    @Override
    public void onCellClick(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        mineSweeper.tap(x, y);
        mainAdapter.notifyItemRangeChanged(0, 81, Cell.class);

        setStatusText();
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mineSweeper != null) {
            outState.putParcelable(KEY_MINESWEEPER, mineSweeper); // 傳送物件，該物件類要實作 Parcelable 介面
        }
        String KEY_GAME_STATUS = "gameStatus_key";
        outState.putString(KEY_GAME_STATUS,binding.tvGameStatus.getText().toString());
    }
}