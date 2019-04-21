package com.raymond.udacity.bakingapp.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.raymond.udacity.bakingapp.R;
import com.raymond.udacity.bakingapp.models.db.Step;
import com.raymond.udacity.bakingapp.ui.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class RecipeAllDetailFragment extends BaseFragment {
    public static final String KEY_RECIPE_ID = "recipe_id";
    public static final String KEY_STEP_ID = "step_id";

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    private PagerAdapter pagerAdapter;
    private RecipeAllDetailViewModel viewModel;

    public static RecipeAllDetailFragment newInstance(int recipeId) {

        Bundle args = new Bundle();

        args.putInt(KEY_RECIPE_ID, recipeId);

        RecipeAllDetailFragment fragment = new RecipeAllDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RecipeAllDetailViewModel.class);
        viewModel.loadRecipeSteps(getArguments().getInt(KEY_RECIPE_ID));
        viewModel.recipeMutableLiveData.observe(this, recipe -> {
            pagerAdapter.setData(recipe.id, Arrays.asList(recipe.steps));
            viewPager.setCurrentItem(getArguments().getInt(KEY_STEP_ID));
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_step_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pagerAdapter = new PagerAdapter(getFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    static class PagerAdapter extends FragmentStatePagerAdapter {
        private int recipeId;
        private final List<Step> steps = new ArrayList<>();

        PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return RecipeDetailFragment.newInstance(recipeId, steps.get(position).id);
        }

        @Override
        public int getCount() {
            return steps.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return String.valueOf(position);
        }

        void setData(int recipeId, List<Step> steps) {
            this.recipeId = recipeId;
            this.steps.clear();
            this.steps.addAll(steps);
            notifyDataSetChanged();
        }
    }
}
