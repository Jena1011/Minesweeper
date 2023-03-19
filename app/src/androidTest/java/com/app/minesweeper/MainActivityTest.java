package com.app.minesweeper;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.*;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> rule =
            new ActivityScenarioRule<>(MainActivity.class);

    //檢驗：一開始，顯示81個格子
    @Test
    public void loadCellTest() {
        onView((withId(R.id.rv_cells)))
                .check(matches(hasChildCount(81)));
    }

    //檢驗：點擊格子，若周圍有炸彈，顯示炸彈數量
    @Test
    public void clickShowNextMines(){
        clickCellAt(1, 0);
        checkNumber(1,0,1);
    }

    //檢驗：點擊格子，若為周圍炸彈數量為0，自動打開周圍格子
    @Test
    public void clickShowNextNextMines() {
        clickCellAt(8, 8);
        checkNumber(7, 6, 2);
        checkNumber(8, 6, 1);
        checkNumber(7, 7, 1);
        checkNumber(7, 8, 1);
    }

    // 檢查某方格中的數字
    private void checkNumber(int x, int y, int number) {
        int position = y * 9 + x;
        ViewInteraction textView =
            onView(
                allOf(
                        withId(R.id.tv_cell),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rv_cells),
                                        position
                                ),
                                0
                        ),
                        isDisplayed()
                )
            );
        textView.check(matches(withText(containsString(String.valueOf(number)))));
    }

    // 點擊某座標的方格
    private void clickCellAt(int x, int y) {
        int position =  y * 9 + x;
        ViewInteraction frameLayout = onView(
                allOf(
                        childAtPosition(
                                allOf(
                                        withId(R.id.rv_cells),
                                        childAtPosition(
                                                instanceOf(LinearLayout.class),
                                                1
                                        )
                                ),
                                position
                        ),
                        isDisplayed()
                )
        );
        frameLayout.perform(click());
    }

    // 返回一個Matcher，用於查找父元素中指定位置的子元素
    private Matcher<View> childAtPosition(final Matcher<View> parentMatcher, final int position){
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && ((ViewGroup) parent).getChildAt(position).equals(view);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position "+ position + " in parent");
                parentMatcher.describeTo(description);
            }
        };
    }

    // 檢驗：長按格子，顯示旗子圖示
    @Test
    public void longPressShowFlag() {
        longPressCellAt(0, 0);
        checkIfFlagged(0,0);
    }

    // 檢查是否插旗
    private void checkIfFlagged(int x, int y) {
        int position = y * 9 + x;
        ViewInteraction item =
                onView(
                        allOf(
                                withId(R.id.iv_cell),
                                childAtPosition(
                                        childAtPosition(
                                                withId(R.id.rv_cells),
                                                position
                                        ),
                                        1
                                ),
                                isDisplayed()
                        )
                );
        item.check(matches(withDrawable(R.drawable.flag)));
    }

    // 返回DrawableMatcher物件，用於比對 ImageView 是否正確顯示 Drawable 圖片
    private Matcher<? super View> withDrawable(int resourceId) {
        return new DrawableMatcher(resourceId);
    }

    // 長按某座標的方格
    private void longPressCellAt(int x, int y) {
        int position =  y * 9 + x;
        ViewInteraction frameLayout = onView(
                allOf(
                        childAtPosition(
                                allOf(
                                        withId(R.id.rv_cells),
                                        childAtPosition(
                                                instanceOf(LinearLayout.class),
                                                1
                                        )
                                ),
                                position
                        ),
                        isDisplayed()
                )
        );
        frameLayout.perform(longClick());
    }

    // 檢驗：長按已插旗的格子，旗子圖示消失

    //檢驗：點擊格子，若格子有炸彈，顯示 Game Over




}
