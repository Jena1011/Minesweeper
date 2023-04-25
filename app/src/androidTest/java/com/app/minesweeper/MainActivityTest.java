package com.app.minesweeper;

import static org.junit.Assert.assertEquals;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    TestNavHostController navController;
    final static String TAG = "jena_mt";

    @Rule
    public ActivityScenarioRule<MainActivity> rule =
            new ActivityScenarioRule<>(MainActivity.class);

    // UI測試：導航至遊戲畫面
    @Test
    public void test_showGameFragment() {
        navController = new TestNavHostController(ApplicationProvider.getApplicationContext());
        GameFragmentArgs fragmentArgs =
                new com.app.minesweeper.GameFragmentArgs
                        .Builder("player1","9X9","easy")
                        .build();

        FragmentScenario<GameFragment> gameFragmentScenario = FragmentScenario.launchInContainer(GameFragment.class, fragmentArgs.toBundle(), R.style.Theme_Minesweeper);

        gameFragmentScenario.onFragment(fragment -> {
            if (navController.getCurrentDestination() == null) return;
            assertEquals(navController.getCurrentDestination().getId(),R.id.gameFragment);
        });
    }
}
