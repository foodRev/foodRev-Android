package me.lolevsky.nasaplanetary;

import android.app.Application;

import me.lolevsky.nasaplanetary.presentation.di.ApplicationComponent;
import me.lolevsky.nasaplanetary.presentation.di.DaggerApplicationComponent;
import me.lolevsky.nasaplanetary.presentation.di.DataModule;
import me.lolevsky.nasaplanetary.presentation.di.DomainModule;
import me.lolevsky.nasaplanetary.presentation.di.NetworkModule;
import me.lolevsky.nasaplanetary.presentation.di.Rx;

public class MainApplication extends Application {

    private ApplicationComponent component;
    private Boolean isPersistent = false;

    @Override public void onCreate() {
        super.onCreate();
        inject();
    }

    private void inject(){
        component = DaggerApplicationComponent.builder()
                                              .networkModule(new NetworkModule())
                                              .dataModule(new DataModule(this))
                                              .domainModule(new DomainModule())
                                              .rx(new Rx())
                                              .build();
        component.inject(this);
    }

    public ApplicationComponent getApplicationComponent(){
        return component;
    }
}
