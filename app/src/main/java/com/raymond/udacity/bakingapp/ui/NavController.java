package com.raymond.udacity.bakingapp.ui;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.raymond.udacity.bakingapp.BaseActivity;

import timber.log.Timber;

public class NavController {
    public <T extends Fragment> void goToFragment(
            final Activity activity,
            final Class<T> fragmentClazz,
            final Bundle arguments) {
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).goToFragment(fragmentClazz, arguments);
        } else {
            Timber.w("Not BaseActivity");
        }
    }

    public <MASTER extends Fragment, DETAIL extends Fragment> void goToMasterDetailFragment(
            final Activity activity,
            final Class<MASTER> masterClazz, final Class<DETAIL> detailClazz,
            final Bundle masterBundle, final Bundle detailBundle
    ) {
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).goToMasterDetailFragment(masterClazz, detailClazz, masterBundle, detailBundle);
        } else {
            Timber.w("Not BaseActivity");
        }
    }
}
