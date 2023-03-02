package com.app.minesweeper;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> rule =
            new ActivityScenarioRule<>(MainActivity.class);

//    @Test
//    public void showLevelSelectDialog() {
//        //檢驗：一開始，顯示選擇等級的視窗
//        onView(withText(R.string.select_level))
//                .check(matches(isDisplayed()));
//        onView(withText("9X9"))
//                .check(matches(isDisplayed()));
//    }

    @Test
    public void loadCellTest() {
        //檢驗：一開始，顯示81個格子

        onView((withId(R.id.tv_test)))
                .check(matches(isDisplayed()));

    }

}
