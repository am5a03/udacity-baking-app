package com.raymond.udacity.bakingapp.ui.detail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.raymond.udacity.bakingapp.R;
import com.raymond.udacity.bakingapp.models.db.Step;
import com.raymond.udacity.bakingapp.ui.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

import static com.raymond.udacity.bakingapp.ui.step.RecipeStepListFragment.ACTION_RECIPE_STEP_SELECTION;

public class RecipeAllDetailFragment extends BaseFragment {
    public static final String KEY_RECIPE_ID = "recipe_id";
    public static final String KEY_STEP_ID = "step_id";

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    RecipeDetailFragmentFactory recipeDetailFragmentFactory = new RecipeDetailFragmentFactory();
    private PagerAdapter pagerAdapter;
    private RecipeAllDetailViewModel viewModel;
    private boolean isTwoPane;
    private LocalBroadcastManager broadcastManager;

    private BroadcastReceiver actionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (viewModel != null) viewModel.handleBroadcast(intent);
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        broadcastManager = LocalBroadcastManager.getInstance(context);
        broadcastManager.registerReceiver(actionReceiver, new IntentFilter(ACTION_RECIPE_STEP_SELECTION));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        broadcastManager.unregisterReceiver(actionReceiver);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isTwoPane = getResources().getBoolean(R.bool.is_twopane);
        pagerAdapter = new PagerAdapter(getFragmentManager(), recipeDetailFragmentFactory);
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RecipeAllDetailViewModel.class);
        viewModel.loadRecipeSteps(getArguments().getInt(KEY_RECIPE_ID));
        viewModel.getRecipeMutableLiveData().observe(this, recipe -> {
            pagerAdapter.setData(recipe.id, Arrays.asList(recipe.steps));
            viewPager.setCurrentItem(pagerAdapter.getActualPosition(getArguments().getInt(KEY_STEP_ID)));
        });
        viewModel.getSelectViewPagerItemLiveData().observe(this, stepId -> {
            viewPager.setCurrentItem(pagerAdapter.getActualPosition(stepId));
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
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(viewModel.getTablayoutOnClickListener());

        if (!isTwoPane) {
            final int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                tabLayout.setVisibility(View.GONE);
                getActivity().findViewById(R.id.toolbar).setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        viewModel.saveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        viewModel.restoreInstanecState(savedInstanceState);
    }

    static class RecipeDetailFragmentFactory {
        RecipeDetailFragment createFragment(int recipeId, int stepId) {
            return RecipeDetailFragment.newInstance(recipeId, stepId);
        }
    }


    static class PagerAdapter extends FragmentStatePagerAdapter {
        private int recipeId;
        private final List<Step> steps = new ArrayList<>();

        // Due to step id may not match position
        private final SparseIntArray stepIdPosMapping = new SparseIntArray();
        private final RecipeDetailFragmentFactory recipeDetailFragmentFactory;

        PagerAdapter(FragmentManager fm, RecipeDetailFragmentFactory recipeDetailFragmentFactory) {
            super(fm);
            this.recipeDetailFragmentFactory = recipeDetailFragmentFactory;
        }

        @Override
        public Fragment getItem(int position) {
            return recipeDetailFragmentFactory.createFragment(recipeId, steps.get(position).id);
        }

        @Override
        public int getCount() {
            return steps.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return String.valueOf(steps.get(position).id);
        }

        int getActualPosition(int stepId) {
            return stepIdPosMapping.get(stepId);
        }

        void setData(int recipeId, List<Step> steps) {
            this.recipeId = recipeId;
            this.steps.clear();
            this.steps.addAll(steps);
            for (int i = 0; i < steps.size(); i++) {
                stepIdPosMapping.put(steps.get(i).id, i);
            }
            notifyDataSetChanged();
        }
    }
}
