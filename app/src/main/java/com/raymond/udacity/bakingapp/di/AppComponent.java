package com.raymond.udacity.bakingapp.di;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                AppModule.class,
                RetrofitModule.class,
                ActivityBindingModule.class
        }
)
public interface AppComponent {
}
