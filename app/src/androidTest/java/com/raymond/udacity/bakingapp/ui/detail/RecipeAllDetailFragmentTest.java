package com.raymond.udacity.bakingapp.ui.detail;

import android.os.Bundle;
import android.os.SystemClock;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.google.android.material.tabs.TabLayout;
import com.raymond.udacity.bakingapp.R;
import com.raymond.udacity.bakingapp.models.db.Recipe;
import com.raymond.udacity.bakingapp.testing.SingleFragmentActivity;
import com.raymond.udacity.bakingapp.ui.NavController;
import com.raymond.udacity.bakingapp.util.MockDataSource;
import com.raymond.udacity.bakingapp.util.TabLayoutActions;
import com.raymond.udacity.bakingapp.util.ViewModelUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import dagger.android.AndroidInjector;

@RunWith(AndroidJUnit4.class)
public class RecipeAllDetailFragmentTest {
    @Rule
    public ActivityTestRule<SingleFragmentActivity> activityTestRule
            = new ActivityTestRule<SingleFragmentActivity>(SingleFragmentActivity.class, true, true);

    private AndroidInjector<Fragment> injector = instance -> {};
    private final List<Recipe> mockList = MockDataSource.getInstance().getMockList();

    private final RecipeAllDetailFragment fragment = new RecipeAllDetailFragment();
    private RecipeAllDetailViewModel allDetailViewModel;

    private final List<RecipeDetailViewModel> recipeDetailViewModels = new ArrayList<>();
    private final List<RecipeDetailFragment> recipeDetailFragments = new ArrayList<>();

    @Before
    public void init() {
        allDetailViewModel = mock(RecipeAllDetailViewModel.class);
        final Bundle bundle = new Bundle();
        fragment.setArguments(bundle);

        when(allDetailViewModel.getRecipeMutableLiveData()).thenReturn(new MutableLiveData<>());
        when(allDetailViewModel.getSelectViewPagerItemLiveData()).thenReturn(new MutableLiveData<>());
        when(allDetailViewModel.getTablayoutOnClickListener()).thenReturn(new RecipeAllDetailViewModel.TabSelectListener(allDetailViewModel) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
            }
        });

        fragment.viewModelFactory = ViewModelUtil.createFor(allDetailViewModel);
        fragment.navController = mock(NavController.class);
        fragment.recipeDetailFragmentFactory = new RecipeAllDetailFragment.RecipeDetailFragmentFactory() {
            @Override
            RecipeDetailFragment createFragment(int recipeId, int stepId) {
                return recipeDetailFragments.get(stepId);
            }
        };

        doNothing().when(allDetailViewModel).loadRecipeSteps(0);
        activityTestRule.getActivity().setFragment(fragment, injector);

        final Recipe recipe = mockList.get(0);
        for (int i = 0; i < recipe.steps.length; i++) {
            final RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.newInstance(0, i);
            final RecipeDetailViewModel viewModel = mock(RecipeDetailViewModel.class);
            recipeDetailFragment.viewModelFactory = ViewModelUtil.createFor(viewModel);
            when(viewModel.getStepLiveData()).thenReturn(new MutableLiveData<>());
            recipeDetailFragments.add(recipeDetailFragment);
            recipeDetailViewModels.add(viewModel);
        }
    }

    @Test
    public void when_StepsAreLoaded_AllPagesWillBeDisplayed() {
        allDetailViewModel.getRecipeMutableLiveData().postValue(mockList.get(0));
        for (int i = 0; i < mockList.get(0).steps.length; i++) {
            onView(withId(R.id.tab_layout)).perform(TabLayoutActions.selectTabAt(i));
            recipeDetailViewModels.get(0).getStepLiveData().postValue(new RecipeDetailViewModel.StepWrapper(mockList.get(0).steps[i]));
            onView(withId(R.id.tab_layout)).check(matches(hasDescendant(withText(String.valueOf(i)))));
        }
    }
}
