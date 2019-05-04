package com.raymond.udacity.bakingapp.ui.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.raymond.udacity.bakingapp.R;
import com.raymond.udacity.bakingapp.ui.BaseFragment;
import com.raymond.udacity.bakingapp.ui.detail.RecipeAllDetailFragment;
import com.raymond.udacity.bakingapp.ui.step.RecipeStepListFragment;

import butterknife.BindView;
import timber.log.Timber;

public class RecipeFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    RecipeViewModel viewModel;
    private boolean isTwoPane;

    public static RecipeFragment newInstance() {

        Bundle args = new Bundle();

        RecipeFragment fragment = new RecipeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isTwoPane = getResources().getBoolean(R.bool.is_twopane);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RecipeViewModel.class);
        viewModel.getRecipeLiveData().observe(this, recipes -> adapter.setRecipes(recipes));
        viewModel.getRecipeBundleClickLiveData().observe(this, masterDetailBundlePair -> {
            Timber.d("recipe=" + masterDetailBundlePair);
            goToMasterDetailFragment(RecipeStepListFragment.class, RecipeAllDetailFragment.class,
                    masterDetailBundlePair.first, masterDetailBundlePair.second);

        });
        adapter = new RecipeAdapter(viewModel.getClickListener());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager = new GridLayoutManager(view.getContext(), 2);
        } else {
            layoutManager = new GridLayoutManager(view.getContext(), 1);
        }

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        viewModel.loadRecipe();
    }
}
