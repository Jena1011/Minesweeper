package com.app.minesweeper;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;

import android.content.pm.ActivityInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

// TODO：處理click和longClick執行失敗的問題

@RunWith(AndroidJUnit4.class)
public class GameFragmentTest extends TestUtils {

    TestNavHostController navController;

    @Rule
    public ActivityScenarioRule<MainActivity> rule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        navController = new TestNavHostController(ApplicationProvider.getApplicationContext());
        FragmentScenario<GameFragment> gameFragmentScenario = FragmentScenario.launchInContainer(GameFragment.class,null,R.style.Theme_Minesweeper);
        gameFragmentScenario.onFragment(myFragment -> {
            navController.setGraph(R.navigation.nav_graph);
            Navigation.setViewNavController(myFragment.requireView(), navController);
        });
    }

    @After
    public void tearDown() {
        if(navController!=null) navController = null;
    }

    // UI測試：顯示遊戲畫面
    @Test
    public void test_showGameFragment(){
        if(navController.getCurrentDestination()==null)return;
        Assert.assertEquals(navController.getCurrentDestination().getId(),R.id.gameFragment);
    }

    // UI測試：一開始，顯示81個格子
    @Test
    public void loadCellTest() {
        if(navController.getCurrentDestination()==null)return;
        onView(withId(R.id.rv_cells))
                .check(matches(hasChildCount(81)));
    }

    // UI測試：點擊格子，若周圍有炸彈，顯示炸彈數量
    @Test
    public void clickShowNextMines(){
        if(navController.getCurrentDestination()==null)return;
        clickCellAt(1, 0);
        checkNumber(1,0,1);
    }

    // UI測試：點擊格子，若為周圍炸彈數量為0，自動打開周圍格子
    @Test
    public void clickShowNextNextMines() {
        if(navController.getCurrentDestination()==null)return;
        clickCellAt(8, 8);
        checkNumber(7, 6, 2);
        checkNumber(8, 6, 1);
        checkNumber(7, 7, 1);
        checkNumber(7, 8, 1);
    }

    // UI測試：長按格子，顯示旗子圖示
    @Test
    public void longPressShowFlag() {
        if(navController.getCurrentDestination()==null)return;
        longPressCellAt(0, 0);
        checkCellImage(0,0,R.drawable.flag);
    }

    // UI測試：長按已插旗的格子，旗子圖示消失
    @Test
    public void longPressRemoveFlag() {
        if(navController.getCurrentDestination()==null)return;
        longPressCellAt(0, 0); //插旗
        longPressCellAt(0, 0); //拔旗
        checkCellImage(0,0,0);
    }

    // UI測試：點擊格子，若格子有炸彈，顯示 Game Over
    @Test
    public void testClickMineGameOver(){
        if(navController.getCurrentDestination()==null)return;
        clickCellAt(0, 0);
        checkGameStatus("Game Over");
    }

    // UI測試：所有沒地雷的格子都打開，顯示 Congratulation!
    @Test
    public void testAllSafeCellOpenWin(){
        if(navController.getCurrentDestination()==null)return;
        clickCellAt(2, 1);
        clickCellAt(8,0);
        clickCellAt(0,8);
        clickCellAt(8,8);
        clickCellAt(6,4);
        clickCellAt(4,8);
        clickCellAt(6,8);
        clickCellAt(5,1);
        clickCellAt(2,4);
        clickCellAt(3,4);
        clickCellAt(4,5);
        clickCellAt(0,4);
        clickCellAt(0,1);
        clickCellAt(0,2);
        checkGameStatus("Congratulation!");
    }

    // UI測試：按下 Restart 按鈕，重置畫面 ( 全部格子沒圖案 )
    @Test
    public void testRestartButton(){
        if(navController.getCurrentDestination()==null)return;
        longPressCellAt(5, 0);
        clickCellAt(2, 1);
        clickCellAt(8,0);

        onView(withId(R.id.bt_restart))
                .perform(click());

        for(int y=0;y<9;y++){
            for (int x=0; x<9; x++){
                checkCellImage(x,y,0);
            }
        }
    }

    // UI測試：Game Over 後不得點擊方格
    @Test
    public void testGameOverCellsDisabled(){
        if(navController.getCurrentDestination()==null)return;
        clickCellAt(0, 0);
        for(int i = 0;i<81;i++){
            onView(
                    childAtPosition(
                            withId(R.id.rv_cells),
                            i
                    )
            ).check(matches(isNotEnabled()));
        }
    }

    // UI測試：旋轉畫面資料不重置
    @Test
    public void testRotateScreen(){
        if(navController.getCurrentDestination()==null)return;
        clickCellAt(0,1);
        checkNumber(0,1,1);
        rotate();
        checkNumber(0,1,1);
    }



    // 檢查某方格中的數字
    private void checkNumber(int x, int y, int number) {
        if(navController.getCurrentDestination()==null)return;
        int position = y * 9 + x;
        ViewInteraction textView =
                onView(
                        allOf(
                                withId(R.id.tv_cell),
                                childOf(
                                        childAtPosition(
                                                withId(R.id.rv_cells),
                                                position
                                        )
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

    // 檢查方格中圖片是否符合預期
    private void checkCellImage(int x, int y, int expectedDrawableId) {
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
        if (expectedDrawableId != 0){
            item.check(matches(withDrawable(expectedDrawableId)));
        } else {
            item.check(doesNotExist());
        }
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

    // 檢查遊戲狀態TextView文字內容
    private void checkGameStatus(String gameStatus) {
        onView((withId(R.id.tv_gameStatus)))
                .check(matches(withText(containsString(gameStatus))));
    }

    // 螢幕旋轉
    private void rotate() {
        rule.getScenario().onActivity(activity -> activity.setRequestedOrientation(
                activity.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE ?
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT :
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        ));
    }
}