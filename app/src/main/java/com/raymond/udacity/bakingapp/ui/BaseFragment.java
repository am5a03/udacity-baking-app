package com.raymond.udacity.bakingapp.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.raymond.udacity.bakingapp.BaseActivity;
import com.raymond.udacity.bakingapp.di.AppViewModelFactory;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

public abstract class BaseFragment extends DaggerFragment {
    @Inject
    public AppViewModelFactory viewModelFactory;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    protected <T extends Fragment> void goToFragment(final Class<T> fragmentClazz, final Bundle arguments) {
        getBaseActivity().goToFragment(fragmentClazz, arguments);
    }
}
