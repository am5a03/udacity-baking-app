package com.raymond.udacity.bakingapp.util;

import android.view.View;

import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.google.android.material.tabs.TabLayout;

import org.hamcrest.Matcher;

import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.allOf;


public final class TabLayoutActions {
    private TabLayoutActions(){}

    public static ViewAction selectTabAt(final int tabIndex) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(TabLayout.class));
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public void perform(UiController uiController, View view) {
                final TabLayout tabLayout = (TabLayout) view;
                final TabLayout.Tab tabAtIndex = tabLayout.getTabAt(tabIndex);
                if (tabAtIndex == null) {
                    throw new PerformException.Builder()
                            .withCause(new Throwable("No tab index " + tabAtIndex))
                            .build();
                }
                tabAtIndex.select();
            }
        };
    }
}
