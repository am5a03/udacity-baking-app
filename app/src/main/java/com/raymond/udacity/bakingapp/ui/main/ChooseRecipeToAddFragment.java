package com.raymond.udacity.bakingapp.ui.main;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.raymond.udacity.bakingapp.R;
import com.raymond.udacity.bakingapp.ui.BaseFragment;
import com.raymond.udacity.bakingapp.ui.widget.RecipeStepListUpdateService;
import com.raymond.udacity.bakingapp.ui.widget.RecipeStepListWidgetService;
import com.raymond.udacity.bakingapp.ui.widget.RecipeWidgetProvider;

import butterknife.BindView;
import timber.log.Timber;

public class ChooseRecipeToAddFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ChooseRecipeToAddViewModel viewModel;
    private AppWidgetManager appWidgetManager;
    private int appWidgetId;

    static ChooseRecipeToAddFragment newInstance(final Bundle extras) {

        Bundle args = new Bundle();
        args.putAll(extras);

        ChooseRecipeToAddFragment fragment = new ChooseRecipeToAddFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        appWidgetManager = AppWidgetManager.getInstance(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChooseRecipeToAddViewModel.class);
        viewModel.recipeLiveData.observe(this, recipes -> adapter.setRecipes(recipes));
        viewModel.recipeChosenLiveData.observe(this, recipe -> {
            RecipeWidgetProvider.updateViews(getContext(), recipe, appWidgetManager, appWidgetId);

            viewModel.saveWidgetState(recipe.id, appWidgetId);

            final Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            getActivity().setResult(Activity.RESULT_OK, resultValue);
            getActivity().finish();
        });
        adapter = new RecipeAdapter(viewModel.clickListener);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        viewModel.loadRecipe();

        appWidgetId = getArguments().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        Timber.d("appWidgetId=" + appWidgetId);
    }
}
