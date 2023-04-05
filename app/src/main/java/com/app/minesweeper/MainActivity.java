package com.app.minesweeper;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import com.app.minesweeper.databinding.ActivityMainBinding;

// TODO:修復畫面未隨手機旋轉問題
public class MainActivity extends AppCompatActivity {

    private final String TAG = "jena_ma";
//    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 隱藏狀態列
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

}