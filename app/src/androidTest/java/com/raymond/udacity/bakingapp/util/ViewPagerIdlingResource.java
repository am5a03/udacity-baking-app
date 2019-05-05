package com.raymond.udacity.bakingapp.util;

import androidx.test.espresso.IdlingResource;
import androidx.viewpager.widget.ViewPager;

public class ViewPagerIdlingResource implements IdlingResource {

    private final ViewPager viewPager;
    private final String name;
    private ResourceCallback resourceCallback;
    private boolean idle = true;

    public ViewPagerIdlingResource(ViewPager viewPager, String name) {
        this.viewPager = viewPager;
        this.viewPager.addOnPageChangeListener(new ViewPagerListener());
        this.name = name;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isIdleNow() {
        return false;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }

    private class ViewPagerListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int state) {
            idle = (state == ViewPager.SCROLL_STATE_IDLE || state == ViewPager.SCROLL_STATE_DRAGGING);
            if (idle && resourceCallback != null) {
                resourceCallback.onTransitionToIdle();
            }
        }
    }
}
