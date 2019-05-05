package com.raymond.udacity.bakingapp.ui.step;

import android.os.Bundle;
import android.os.SystemClock;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.raymond.udacity.bakingapp.R;
import com.raymond.udacity.bakingapp.testing.SingleFragmentActivity;
import com.raymond.udacity.bakingapp.ui.NavController;
import com.raymond.udacity.bakingapp.util.MockDataSource;
import com.raymond.udacity.bakingapp.util.RecyclerViewMatcher;
import com.raymond.udacity.bakingapp.util.ViewModelUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import dagger.android.AndroidInjector;

@RunWith(AndroidJUnit4.class)
public class RecipeSteListFragmentTest {
    @Rule
    public ActivityTestRule<SingleFragmentActivity> activityTestRule
            = new ActivityTestRule<SingleFragmentActivity>(SingleFragmentActivity.class, true, true);

    private AndroidInjector<Fragment> injector = instance -> {};
    private RecipeStepListFragment fragment = new RecipeStepListFragment();
    private RecipeStepListViewModel viewModel;

    @Before
    public void init() {
        final Bundle args = new Bundle();
        args.putInt(RecipeStepListFragment.KEY_RECIPE_ID, 0);
        fragment.setArguments(args);
        viewModel = mock(RecipeStepListViewModel.class);
        final MutableLiveData<Bundle> liveData = new MutableLiveData<>();
        final RecipeStepListViewModel.ItemClickListener listener = new RecipeStepListViewModel.ItemClickListener(liveData);
        listener.setRecipe(MockDataSource.getInstance().getMockList().get(0));

        when(viewModel.getRecipeLiveData()).thenReturn(new MutableLiveData<>());
        when(viewModel.getStepClickLiveData()).thenReturn(liveData);
        when(viewModel.getStepClickListener()).thenReturn(listener);

        fragment.viewModelFactory = ViewModelUtil.createFor(viewModel);
        fragment.navController = mock(NavController.class);

        doNothing().when(viewModel).loadRecipeSteps(0);
        activityTestRule.getActivity().setFragment(fragment, injector);
    }

    @Test
    public void when_stepsLoaded_displayIngredientsAndShortDescriptions() {
        viewModel.getRecipeLiveData().postValue(MockDataSource.getInstance().getMockList().get(0));
        onView(listMatcher().atPosition(0)).check(matches(hasDescendant(withText("Ingredients"))));

        final ViewAction action = RecyclerViewActions.scrollToPosition(10);
        onView(withId(R.id.recycler_view)).perform(action);
        onView(listMatcher().atPosition(10)).check(matches(hasDescendant(withText("Steps"))));
    }

    private RecyclerViewMatcher listMatcher() {
        return new RecyclerViewMatcher(R.id.recycler_view);
    }
}
