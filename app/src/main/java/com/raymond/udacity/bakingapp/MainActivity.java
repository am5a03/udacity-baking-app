package com.raymond.udacity.bakingapp;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.raymond.udacity.bakingapp.repository.RecipeRepository;
import com.raymond.udacity.bakingapp.ui.detail.RecipeAllDetailFragment;
import com.raymond.udacity.bakingapp.ui.detail.RecipeAllDetailViewModel;
import com.raymond.udacity.bakingapp.ui.detail.RecipeDetailFragment;
import com.raymond.udacity.bakingapp.ui.step.RecipeStepListFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import timber.log.Timber;

public class MainActivity extends DaggerAppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    RecipeRepository recipeRepository;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        initFragment(RecipeAllDetailFragment.newInstance(1));
    }

    private void initFragment(Fragment fragment) {
        try {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();

            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        } catch (Exception e) {
            Timber.e(e);
        }
    }
}
