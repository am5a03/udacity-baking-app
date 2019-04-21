package com.raymond.udacity.bakingapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.raymond.udacity.bakingapp.repository.RecipeRepository;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    RecipeRepository recipeRepository;

    public <T extends Fragment> void goToFragment(final Class<T> fragmentClazz, final Bundle arguments) {
        final Intent intent = new Intent(this, SimpleFragmentHolderActivity.class);
        intent.putExtra(SimpleFragmentHolderActivity.KEY_FRAGMENT_CLASS, fragmentClazz.getName());
        intent.putExtra(SimpleFragmentHolderActivity.KEY_FRAGMENT_ARGS, arguments);
        intent.putExtra(SimpleFragmentHolderActivity.KEY_TITLE, arguments.getString(SimpleFragmentHolderActivity.KEY_TITLE));
        intent.putExtra(SimpleFragmentHolderActivity.KEY_DISPLAY_HOME_AS_UP_ENABLED, arguments.getBoolean(SimpleFragmentHolderActivity.KEY_DISPLAY_HOME_AS_UP_ENABLED));
        startActivity(intent);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }
}
