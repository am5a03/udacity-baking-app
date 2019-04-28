package com.raymond.udacity.bakingapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.raymond.udacity.bakingapp.repository.RecipeRepository;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import timber.log.Timber;

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
        intent.putExtra(SimpleFragmentHolderActivity.KEY_SUPPORT_LANDSCAPE_FULL_SCREEN_MODE, arguments.getBoolean(SimpleFragmentHolderActivity.KEY_SUPPORT_LANDSCAPE_FULL_SCREEN_MODE));
        startActivity(intent);
    }

    public <MASTER extends Fragment, DETAIL extends Fragment> void goToMasterDetailFragment(final Class<MASTER> masterClazz,
                                                                                            final Class<DETAIL> detailClazz,
                                                                                            final Bundle masterBundle,
                                                                                            final Bundle detailBundle) {
        final Intent intent = new Intent(this, SimpleFragmentHolderActivity.class);
        intent.putExtra(SimpleFragmentHolderActivity.KEY_FRAGMENT_CLASS, masterClazz.getName());
        intent.putExtra(SimpleFragmentHolderActivity.KEY_FRAGMENT_DETAIL_CLASS, detailClazz.getName());
        intent.putExtra(SimpleFragmentHolderActivity.KEY_FRAGMENT_ARGS, masterBundle);
        intent.putExtra(SimpleFragmentHolderActivity.KEY_FRAGMENT_DETAIL_ARGS, detailBundle);

        intent.putExtra(SimpleFragmentHolderActivity.KEY_TITLE, masterBundle.getString(SimpleFragmentHolderActivity.KEY_TITLE));
        intent.putExtra(SimpleFragmentHolderActivity.KEY_DISPLAY_HOME_AS_UP_ENABLED, masterBundle.getBoolean(SimpleFragmentHolderActivity.KEY_DISPLAY_HOME_AS_UP_ENABLED));
        intent.putExtra(SimpleFragmentHolderActivity.KEY_SUPPORT_LANDSCAPE_FULL_SCREEN_MODE, masterBundle.getBoolean(SimpleFragmentHolderActivity.KEY_SUPPORT_LANDSCAPE_FULL_SCREEN_MODE));
        startActivity(intent);

    }

    protected void initFragment(Fragment fragment) {
        try {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();

            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }
}
