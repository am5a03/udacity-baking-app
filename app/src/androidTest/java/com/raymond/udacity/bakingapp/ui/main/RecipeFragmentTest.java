package com.raymond.udacity.bakingapp.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.raymond.udacity.bakingapp.R;
import com.raymond.udacity.bakingapp.SimpleFragmentHolderActivity;
import com.raymond.udacity.bakingapp.models.db.Recipe;
import com.raymond.udacity.bakingapp.repository.RecipeRepository;
import com.raymond.udacity.bakingapp.testing.SingleFragmentActivity;
import com.raymond.udacity.bakingapp.ui.NavController;
import com.raymond.udacity.bakingapp.ui.detail.RecipeAllDetailFragment;
import com.raymond.udacity.bakingapp.ui.step.RecipeStepListFragment;
import com.raymond.udacity.bakingapp.util.MockDataSource;
import com.raymond.udacity.bakingapp.util.RecyclerViewMatcher;
import com.raymond.udacity.bakingapp.util.Util;
import com.raymond.udacity.bakingapp.util.ViewModelUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Matchers;

import java.util.ArrayList;
import java.util.List;

import dagger.android.AndroidInjector;
import io.reactivex.Single;

import static androidx.test.espresso.Espresso.onView;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import androidx.test.espresso.matcher.ViewMatchers;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class RecipeFragmentTest {

    @Rule
    public ActivityTestRule<SingleFragmentActivity> activityTestRule
            = new ActivityTestRule<SingleFragmentActivity>(SingleFragmentActivity.class, true, true);

    private RecipeFragment recipeFragment = RecipeFragment.newInstance();
    private RecipeViewModel viewModel;
    private AndroidInjector<Fragment> injector = instance -> {};
    private final List<Recipe> mockList = MockDataSource.getInstance().getMockList();

    @Before
    public void init() {
        viewModel = mock(RecipeViewModel.class);
        when(viewModel.getRecipeLiveData()).thenReturn(new MutableLiveData<>());
        MutableLiveData<Pair<Bundle, Bundle>> clickLiveData = new MutableLiveData<>();
        when(viewModel.getRecipeBundleClickLiveData()).thenReturn(clickLiveData);
        when(viewModel.getClickListener()).thenReturn(new RecipeViewModel.ItemClickListener(clickLiveData));

        recipeFragment.viewModelFactory = ViewModelUtil.createFor(viewModel);
        recipeFragment.navController = mock(NavController.class);

        doNothing().when(viewModel).loadRecipe();
        activityTestRule.getActivity().setFragment(recipeFragment, injector);
    }

    @Test
    public void whenRecipe_IsLoaded_RecipeDisplayedWithNameAndServing() {
        viewModel.getRecipeLiveData().postValue(mockList);
        onView(listMatcher().atPosition(0)).check(matches(hasDescendant(withText("Recipe 0"))));
        onView(listMatcher().atPosition(0)).check(matches(hasDescendant(withText("Serving : 999"))));
    }

    @Test
    public void whenRecipe_IsClicked_GoToDetailFragment() {
        viewModel.getRecipeLiveData().postValue(mockList);
        onView(listMatcher().atPosition(0)).perform(click());
        verify(recipeFragment.navController, times(1)).goToMasterDetailFragment(
                any(Activity.class),
                any(Class.class),
                any(Class.class),
                any(Bundle.class), any(Bundle.class)
        );
    }

    private RecyclerViewMatcher listMatcher() {
        return new RecyclerViewMatcher(R.id.recycler_view);
    }
}
