package com.app.minesweeper;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;


/**
 * 包含以下整頁式 Fragment:
 * MenuFragment
 * GameFragment
 */
public class MainActivity extends AppCompatActivity {

    private final String TAG = "jena_ma";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 隱藏狀態列
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

}