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

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeStepListFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private RecipeStepListAdapter adapter;
    private RecipeStepListViewModel viewModel;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new RecipeStepListAdapter();
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RecipeStepListViewModel.class);
        viewModel.recipeLiveData.observe(this, recipe -> adapter.setData(recipe));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_step_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        viewModel.loadRecipeSteps(1);
    }
}
