package com.raymond.udacity.bakingapp.ui.detail;

import android.os.Bundle;
import android.os.SystemClock;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.google.android.material.tabs.TabLayout;
import com.raymond.udacity.bakingapp.models.db.Recipe;
import com.raymond.udacity.bakingapp.testing.SingleFragmentActivity;
import com.raymond.udacity.bakingapp.ui.NavController;
import com.raymond.udacity.bakingapp.util.MockDataSource;
import com.raymond.udacity.bakingapp.util.ViewModelUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dagger.android.AndroidInjector;

@RunWith(AndroidJUnit4.class)
public class RecipeAllDetailFragmentTest {
    @Rule
    public ActivityTestRule<SingleFragmentActivity> activityTestRule
            = new ActivityTestRule<SingleFragmentActivity>(SingleFragmentActivity.class, true, true);

    private AndroidInjector<Fragment> injector = instance -> {};
    private final List<Recipe> mockList = MockDataSource.getInstance().getMockList();

    private final RecipeAllDetailFragment fragment = new RecipeAllDetailFragment();;
    private RecipeAllDetailViewModel allDetailViewModel;
    private RecipeDetailViewModel recipeDetailViewModel;

    @Before
    public void init() {
        allDetailViewModel = mock(RecipeAllDetailViewModel.class);
        final Bundle bundle = new Bundle();
        fragment.setArguments(bundle);

        when(allDetailViewModel.getRecipeMutableLiveData()).thenReturn(new MutableLiveData<>());
        when(allDetailViewModel.getSelectViewPagerItemLiveData()).thenReturn(new MutableLiveData<>());
        when(allDetailViewModel.getTablayoutOnClickListener()).thenReturn(new RecipeAllDetailViewModel.TabSelectListener(allDetailViewModel));

        fragment.viewModelFactory = ViewModelUtil.createFor(allDetailViewModel);
        fragment.navController = mock(NavController.class);

        doNothing().when(allDetailViewModel).loadRecipeSteps(0);
        activityTestRule.getActivity().setFragment(fragment, injector);
    }

    @Test
    public void test() {
        allDetailViewModel.getRecipeMutableLiveData().postValue(mockList.get(0));
    }
}
