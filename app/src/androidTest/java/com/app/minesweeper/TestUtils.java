package com.app.minesweeper;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

abstract public class TestUtils {
    // 返回一個Matcher，用於查找父元素中指定位置的子元素
    public static Matcher<View> childAtPosition(final Matcher<View> parentMatcher, final int position) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && ((ViewGroup) parent).getChildAt(position).equals(view);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent");
                parentMatcher.describeTo(description);
            }
        };
    }

    // 返回一個Matcher，用於查找父元素中的子元素
    public static Matcher<View> childOf(final Matcher<View> parentMatcher) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Child in parent");
                parentMatcher.describeTo(description);
            }
        };
    }
}
