package com.raymond.udacity.bakingapp.testing;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.raymond.udacity.bakingapp.BaseActivity;
import com.raymond.udacity.bakingapp.R;

import dagger.android.AndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Fragment holder for Espresso test copied from
 *
 * @link https://github.com/googlesamples/android-architecture-components/blob/17c315a050745c61ae8e79000bc0251305bd148b/GithubBrowserSample/app/src/debug/java/com/android/example/github/testing/SingleFragmentActivity.kt
 */
public class SingleFragmentActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    private AndroidInjector<Fragment> injector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout content = new FrameLayout(this);
        content.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        content.setId(R.id.container);
        setContentView(content);
    }

    public void setFragment(Fragment fragment, AndroidInjector injector) {
        this.injector = injector;
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment, "TEST")
                .commit();
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).commit();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return injector;
    }
}
