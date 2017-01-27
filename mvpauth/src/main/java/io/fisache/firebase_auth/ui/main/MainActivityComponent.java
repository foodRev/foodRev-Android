package io.fisache.firebase_auth.ui.main;

import dagger.Subcomponent;
import io.fisache.firebase_auth.base.annotation.ActivityScope;

@ActivityScope
@Subcomponent(
        modules = {
                MainActivityModule.class
        }

)
public interface MainActivityComponent {
    MainActivity inject(MainActivity activity);
}
