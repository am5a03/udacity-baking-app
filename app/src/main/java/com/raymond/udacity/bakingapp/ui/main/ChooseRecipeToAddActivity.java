package com.raymond.udacity.bakingapp.ui.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.raymond.udacity.bakingapp.BaseActivity;
import com.raymond.udacity.bakingapp.R;

import butterknife.BindView;

public class ChooseRecipeToAddActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar.setTitle(R.string.choose_recipe);
        setSupportActionBar(toolbar);

        initFragment(ChooseRecipeToAddFragment.newInstance(getIntent().getExtras()));
    }
}
