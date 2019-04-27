package com.raymond.udacity.bakingapp.ui.step;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.raymond.udacity.bakingapp.R;
import com.raymond.udacity.bakingapp.SimpleFragmentHolderActivity;
import com.raymond.udacity.bakingapp.ui.BaseFragment;
import com.raymond.udacity.bakingapp.ui.detail.RecipeAllDetailFragment;

import butterknife.BindView;


public class RecipeStepListFragment extends BaseFragment {

    public static final String KEY_RECIPE_ID = "recipe_id";
    public static final String ACTION_RECIPE_STEP_SELECTION = RecipeStepListFragment.class.getName() + ".STEP_SELECTION";
    public static final String KEY_ACTION_SELECT_STEP = "select_step";
    public static final String KEY_STEP_LIST_INDEX = "step_index";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private RecipeStepListAdapter adapter;
    private RecipeStepListViewModel viewModel;
    private RecyclerView.LayoutManager layoutManager;
    private boolean isTwoPane;

    private LocalBroadcastManager broadcastManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        broadcastManager = LocalBroadcastManager.getInstance(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isTwoPane = getResources().getBoolean(R.bool.is_twopane);
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RecipeStepListViewModel.class);
        viewModel.recipeLiveData.observe(this, recipe -> adapter.setData(recipe));
        viewModel.stepClickLiveData.observe(this, stepBundle -> {
            stepBundle.putBoolean(
                    SimpleFragmentHolderActivity.KEY_SUPPORT_LANDSCAPE_FULL_SCREEN_MODE,
                    !isTwoPane);
            if (isTwoPane) {
                final Intent intent = new Intent(ACTION_RECIPE_STEP_SELECTION);
                intent.putExtra(KEY_ACTION_SELECT_STEP, stepBundle);
                broadcastManager.sendBroadcast(intent);
                adapter.setSelectedIndex(stepBundle.getInt(KEY_STEP_LIST_INDEX));
            } else {
                goToFragment(RecipeAllDetailFragment.class, stepBundle);
            }
        });
        adapter = new RecipeStepListAdapter(viewModel.stepClickListener);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_step_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        viewModel.loadRecipeSteps(getArguments().getInt(KEY_RECIPE_ID, 1));
    }
}
