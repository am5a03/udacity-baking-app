package com.raymond.udacity.bakingapp.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.raymond.udacity.bakingapp.BaseActivity;
import com.raymond.udacity.bakingapp.di.AppViewModelFactory;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

public abstract class BaseFragment extends DaggerFragment {
    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    public NavController navController = new NavController();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    public <T extends Fragment> void goToFragment(final Class<T> fragmentClazz, final Bundle arguments) {
        navController.goToFragment(getActivity(), fragmentClazz, arguments);
    }

    public  <MASTER extends Fragment, DETAIL extends Fragment> void goToMasterDetailFragment(final Class<MASTER> masterClazz,
                                                                                               final Class<DETAIL> detailClazz,
                                                                                               final Bundle masterBundle,
                                                                                               final Bundle detailBundle) {
        navController.goToMasterDetailFragment(getActivity(), masterClazz, detailClazz, masterBundle, detailBundle);
    }
}
