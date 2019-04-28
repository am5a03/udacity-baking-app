package com.raymond.udacity.bakingapp;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.raymond.udacity.bakingapp.ui.main.RecipeFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);

        initFragment(RecipeFragment.newInstance());
    }
}
