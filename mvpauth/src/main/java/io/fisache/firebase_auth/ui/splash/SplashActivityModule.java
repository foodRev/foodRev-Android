package io.fisache.firebase_auth.ui.splash;

import dagger.Module;
import dagger.Provides;
import io.fisache.firebase_auth.base.annotation.ActivityScope;
import io.fisache.firebase_auth.data.source.remote.UserService;

@Module
public class SplashActivityModule {
    private SplashActivity activity;

    public SplashActivityModule(SplashActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    SplashActivity provideSplashActivity() {
        return activity;
    }

    @Provides
    @ActivityScope
    SplashPresenter provideSplashActivityPresenter(UserService userService) {
        return new SplashPresenter(activity, userService);
    }

}
