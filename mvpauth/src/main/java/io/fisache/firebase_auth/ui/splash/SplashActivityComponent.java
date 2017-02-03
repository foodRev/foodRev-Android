package io.fisache.firebase_auth.ui.splash;

import dagger.Subcomponent;
import io.fisache.firebase_auth.base.annotation.ActivityScope;

@ActivityScope
@Subcomponent(
        modules = {
                SplashActivityModule.class
        }
)
public interface SplashActivityComponent {
    SplashActivity inject(SplashActivity activity);
}
