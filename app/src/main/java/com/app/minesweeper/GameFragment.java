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

/**
 * 遊戲主畫面 Fragment
 */
public class GameFragment extends Fragment implements ICellTapListener{

    private FragmentGameBinding binding = null ;
    private MineSweeper mineSweeper; // MineSweeper 物件，表示踩地雷遊戲
    private MainAdapter mainAdapter; // 用於創建雷區
    private RecyclerView rvCells; // 用於創建雷區
    private final static String KEY_MINESWEEPER = "mineSweeper_key"; // 用於保存遊戲狀態

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentGameBinding.inflate(inflater, container, false);

        // 判斷是否保有之前遊戲狀態
       if(savedInstanceState==null){
            startGame(); // 無 → 直接開始遊戲
        }else {
            mineSweeper = savedInstanceState.getParcelable(KEY_MINESWEEPER); // 有 → 還原之前狀態
        }

       // 取得根視圖
        View view = binding.getRoot();

       // 生成雷區畫面
        rvCells = binding.rvCells;
        setMainAdapter(mineSweeper);
        rvCells.setLayoutManager(new GridLayoutManager(view.getContext(), 9));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View gameView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(gameView, savedInstanceState);
        binding.btRestart.setOnClickListener(view -> resetGame()); // 設定重新開始bt監聽器
        setStatusText(); // 設定遊戲狀態文字
    }

    // 開始新遊戲
    private void startGame() {
        mineSweeper = new MineSweeper();
        //mock測試使用
        CellCreator cellCreator = new CellCreator();
        cellCreator.level= 9;
        mineSweeper.startGame(cellCreator);
//        //prod測試使用
//        mineSweeper.startGame(new CellCreator(9));
    }

    // rv 設定 mainAdapter
    private void setMainAdapter(MineSweeper mineSweeper) {
        mainAdapter = new MainAdapter(mineSweeper);
        mainAdapter.setCellListener(this);
        rvCells.setAdapter(mainAdapter);
    }

    // 重新開始
    private void resetGame() {
        if(mineSweeper!=null){
            mineSweeper.cells.clear();
            //mock測試使用
            CellCreator cellCreator = new CellCreator();
            cellCreator.level= 9;
            mineSweeper.startGame(cellCreator);
//            //prod測試使用
//            mineSweeper.startGame(new CellCreator(9));
        }
        if(mainAdapter!=null){
            setMainAdapter(mineSweeper);
            mainAdapter.notifyItemRangeChanged(0, mainAdapter.getItemCount());
        }
        setStatusText();
    }

    // 設定狀態文字
    private void setStatusText() {
        if (mineSweeper.status == GameStatus.DIE) {
            binding.tvGameStatus.setText(R.string.gameOver);
            binding.tvGameStatus.setTextColor(Color.rgb(255, 0, 0));
        } else if (mineSweeper.status == GameStatus.WIN) {
            binding.tvGameStatus.setText(R.string.congratulation);
            binding.tvGameStatus.setTextColor(Color.rgb(0, 150, 0));
        } else {
            binding.tvGameStatus.setText("");
        }
    }

    // 監聽點擊格子的事件 (實作ICellTapListener方法)
    @Override
    public void onCellClick(Cell cell) {
        mineSweeper.tap(cell.x, cell.y);
        mainAdapter.notifyItemRangeChanged(0, mainAdapter.getItemCount());
        setStatusText();
    }

    // 監聽長按格子的事件 (實作ICellTapListener方法)
    @Override
    public void onCellLongClick(Cell cell) {
        int x = cell.x;
        int y = cell.y;
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