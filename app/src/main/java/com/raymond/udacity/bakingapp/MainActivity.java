package com.raymond.udacity.bakingapp;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.raymond.udacity.bakingapp.ui.detail.RecipeAllDetailFragment;
import com.raymond.udacity.bakingapp.ui.main.RecipeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        initFragment(RecipeFragment.newInstance());
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
