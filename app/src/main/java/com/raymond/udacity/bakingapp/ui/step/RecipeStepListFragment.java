package com.raymond.udacity.bakingapp.ui.step;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.raymond.udacity.bakingapp.R;
import com.raymond.udacity.bakingapp.ui.BaseFragment;
import com.raymond.udacity.bakingapp.ui.detail.RecipeAllDetailFragment;

import butterknife.BindView;


public class RecipeStepListFragment extends BaseFragment {

    public static final String KEY_RECIPE_ID = "recipe_id";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private RecipeStepListAdapter adapter;
    private RecipeStepListViewModel viewModel;
    private RecyclerView.LayoutManager layoutManager;
    private boolean isTwoPane;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isTwoPane = getResources().getBoolean(R.bool.is_twopane);
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RecipeStepListViewModel.class);
        viewModel.recipeLiveData.observe(this, recipe -> adapter.setData(recipe));
        viewModel.stepClickLiveData.observe(this, stepBundle -> {
            goToFragment(RecipeAllDetailFragment.class, stepBundle);
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
