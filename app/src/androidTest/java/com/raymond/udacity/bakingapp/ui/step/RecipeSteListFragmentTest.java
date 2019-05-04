package com.raymond.udacity.bakingapp.ui.step;

import androidx.fragment.app.Fragment;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.raymond.udacity.bakingapp.testing.SingleFragmentActivity;

import org.junit.Rule;
import org.junit.runner.RunWith;

import dagger.android.AndroidInjector;

@RunWith(AndroidJUnit4.class)
public class RecipeSteListFragmentTest {
    @Rule
    public ActivityTestRule<SingleFragmentActivity> activityTestRule
            = new ActivityTestRule<SingleFragmentActivity>(SingleFragmentActivity.class, true, true);

    private AndroidInjector<Fragment> injector = instance -> {};
}
