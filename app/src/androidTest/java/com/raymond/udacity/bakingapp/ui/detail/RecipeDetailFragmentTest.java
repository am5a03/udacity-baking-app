package com.raymond.udacity.bakingapp.ui.detail;

import android.os.SystemClock;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.raymond.udacity.bakingapp.R;
import com.raymond.udacity.bakingapp.models.db.Recipe;
import com.raymond.udacity.bakingapp.testing.SingleFragmentActivity;
import com.raymond.udacity.bakingapp.ui.NavController;
import com.raymond.udacity.bakingapp.util.MockDataSource;
import com.raymond.udacity.bakingapp.util.ViewModelUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import dagger.android.AndroidInjector;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailFragmentTest {
    @Rule
    public ActivityTestRule<SingleFragmentActivity> activityTestRule
            = new ActivityTestRule<SingleFragmentActivity>(SingleFragmentActivity.class, true, true);

    private AndroidInjector<Fragment> injector = instance -> {};
    private final List<Recipe> mockList = MockDataSource.getInstance().getMockList();

    private RecipeDetailFragment fragment;
    private RecipeDetailViewModel viewModel;

    @Before
    public void init() {
        fragment = RecipeDetailFragment.newInstance(0, 0);
        viewModel = mock(RecipeDetailViewModel.class);
        fragment.viewModelFactory = ViewModelUtil.createFor(viewModel);
        fragment.navController = mock(NavController.class);
        when(viewModel.getStepLiveData()).thenReturn(new MutableLiveData<>());
        activityTestRule.getActivity().setFragment(fragment, injector);
    }

    @Test
    public void when_RecipeWithNoVideoLoaded_NoPlayerIsDisplayed() {
        viewModel.getStepLiveData().postValue(new RecipeDetailViewModel.StepWrapper(mockList.get(0).steps[0]));
        onView(withId(R.id.player_view)).check(matches(not(isDisplayed())));
    }

    @Test
    public void when_RecipeWithNoVideoLoaded_PlayerIsDisplayed() {
        viewModel.getStepLiveData().postValue(new RecipeDetailViewModel.StepWrapper(mockList.get(0).steps[1]));
        onView(withId(R.id.player_view)).check(matches(isDisplayed()));
    }
}
