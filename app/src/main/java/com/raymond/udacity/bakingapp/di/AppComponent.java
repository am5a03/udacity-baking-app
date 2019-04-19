package com.raymond.udacity.bakingapp.di;

import com.raymond.udacity.bakingapp.MainApplication;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                AppModule.class,
                RetrofitModule.class,
                RoomModule.class,
                RepositoryModule.class,
                ActivityBindingModule.class
        }
)
public interface AppComponent extends AndroidInjector<MainApplication> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<MainApplication> {

    }
}
