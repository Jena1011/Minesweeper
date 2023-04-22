package com.app.minesweeper;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static com.app.minesweeper.TestUtils.childAtPosition;
import static com.app.minesweeper.TestUtils.childOf;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MenuFragmentTest {

    TestNavHostController navController;

    @Rule
    public ActivityScenarioRule<MainActivity> rule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        navController = new TestNavHostController(ApplicationProvider.getApplicationContext());
        FragmentScenario<MenuFragment> menuFragmentScenario = FragmentScenario.launchInContainer(MenuFragment.class,null,R.style.Theme_Minesweeper);
        menuFragmentScenario.onFragment(myFragment -> {
            navController.setGraph(R.navigation.nav_graph);
            Navigation.setViewNavController(myFragment.requireView(), navController);
        });
    }

    @After
    public void tearDown() {
        if(navController!=null) navController = null;
    }


    // UI測試：顯示主選單頁面
    @Test
    public void test_showMenuFragment(){
        if(navController.getCurrentDestination()==null)return;
        Assert.assertEquals(navController.getCurrentDestination().getId(),R.id.menuFragment);
    }

    // UI測試：顯示初始畫面
    @Test
    public void test_showUIText(){
        if(navController.getCurrentDestination()==null)return;
        onView(withId(R.id.tv_player)).check(matches(withText("玩家")));
        onView(withId(R.id.tv_size)).check(matches(withText("地圖")));
        onView(withId(R.id.tv_level)).check(matches(withText("難度")));
        onView(withId(R.id.bt_start)).check(matches(withText("遊戲開始")));
        onView(withId(R.id.sp_players)).check(matches(withSpinnerText("player1")));
        onView(withId(R.id.sp_sizes)).check(matches(withSpinnerText("6X6")));
        onView(withId(R.id.sp_levels)).check(matches(withSpinnerText("easy")));
    }

    // UI測試：player spinner 點擊功能
    @Test
    public void test_clickPlayerSpinner() {
        if(navController.getCurrentDestination()==null)return;
        onView(withId(R.id.sp_players)).perform(click());
        onData(allOf(is(instanceOf(String.class)),is("player2"))).perform(click());
        onView(withId(R.id.sp_players)).check(matches(withSpinnerText(containsString("player2"))));
    }

    // UI測試：size spinner 點擊功能
    @Test
    public void test_clickSizeSpinner() {
        if(navController.getCurrentDestination()==null)return;
        onView(withId(R.id.sp_sizes)).perform(click());
        onData(allOf(is(instanceOf(String.class)),is("9X9"))).perform(click());
        onView(withId(R.id.sp_sizes)).check(matches(withSpinnerText(containsString("9X9"))));
    }

    // UI測試：level spinner 點擊功能
    @Test
    public void test_clickLevelSpinner() {
        if(navController.getCurrentDestination()==null)return;
        onView(withId(R.id.sp_levels)).perform(click());
        onData(allOf(is(instanceOf(String.class)),is("normal"))).perform(click());
        onView(withId(R.id.sp_levels)).check(matches(withSpinnerText(containsString("normal"))));
    }
}
