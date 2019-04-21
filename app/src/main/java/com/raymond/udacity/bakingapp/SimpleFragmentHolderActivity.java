package com.raymond.udacity.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import butterknife.BindView;
import timber.log.Timber;

public class SimpleFragmentHolderActivity extends BaseActivity {
    public static final String KEY_FRAGMENT_CLASS = "fragment_class";
    public static final String KEY_FRAGMENT_ARGS = "fragment_args";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DISPLAY_HOME_AS_UP_ENABLED = "display_home_as_up";

    public static final String KEY_FRAGMENT_DETAIL_CLASS = "fragment_detail_class";
    public static final String KEY_FRAGMENT_DETAIL_ARGS = "fragment_detail_args";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fragment_container_detail)
    @Nullable FrameLayout detailFragmentContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_fragment_holder);

        final Intent intent = getIntent();
        final String className = intent.getStringExtra(KEY_FRAGMENT_CLASS);
        final String detailClassName = intent.getStringExtra(KEY_FRAGMENT_DETAIL_CLASS);
        final Bundle fragmentArgs = intent.getBundleExtra(KEY_FRAGMENT_ARGS);
        final Bundle detailFragmentArgs = intent.getBundleExtra(KEY_FRAGMENT_DETAIL_ARGS);
        final String title = fragmentArgs.getString(KEY_TITLE);

        if (getResources().getBoolean(R.bool.is_twopane) && detailClassName != null && !detailClassName.isEmpty()) {
            instantiateMasterDetailFragment(className, detailClassName, fragmentArgs, detailFragmentArgs);
        } else {
            instantiateFragment(className, fragmentArgs);
            // On rotation to landscape, but single fragment is inited
            if (detailFragmentContainer != null) {
                detailFragmentContainer.setVisibility(View.GONE);
            }
        }

        if (title != null && !title.isEmpty()) {
            toolbar.setTitle(title);
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(intent.getBooleanExtra(KEY_DISPLAY_HOME_AS_UP_ENABLED, false));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void instantiateMasterDetailFragment(final String masterClassName,
                                                 final String detailClassName,
                                                 final Bundle masterArguments,
                                                 final Bundle detailArguments) {
        try {
            final FragmentManager fm = getSupportFragmentManager();
            final FragmentTransaction transaction = fm.beginTransaction();

            Fragment masterFragment = (Fragment) Class.forName(masterClassName).newInstance();
            masterFragment.setArguments(masterArguments);
            transaction.replace(R.id.fragment_container, masterFragment);

            if (detailFragmentContainer != null) {
                Fragment detailFragment = (Fragment) Class.forName(detailClassName).newInstance();
                detailFragment.setArguments(detailArguments);
                transaction.replace(R.id.fragment_container_detail, detailFragment);
            }

            transaction.commit();
        } catch (ClassCastException e1) {
            Timber.e(e1);
        } catch (IllegalAccessException e) {
            Timber.e(e);
        } catch (InstantiationException e) {
            Timber.e(e);
        } catch (ClassNotFoundException e) {
            Timber.e(e);
        }
    }

    private void instantiateFragment(final String className,
                                     final Bundle arguments) {
        try {
            Fragment fragment = (Fragment) Class.forName(className).newInstance();
            fragment.setArguments(arguments);

            final FragmentManager fm = getSupportFragmentManager();
            final FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        } catch (ClassCastException e1) {
            Timber.e(e1);
        } catch (IllegalAccessException e) {
            Timber.e(e);
        } catch (InstantiationException e) {
            Timber.e(e);
        } catch (ClassNotFoundException e) {
            Timber.e(e);
        }

    }
}
