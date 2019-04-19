package com.raymond.udacity.bakingapp.ui;

import com.raymond.udacity.bakingapp.di.AppViewModelFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public abstract class BaseFragment extends DaggerFragment {
    @Inject
    public AppViewModelFactory viewModelFactory;
}
