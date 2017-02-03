package io.fisache.firebase_auth.ui.login;

import dagger.Subcomponent;
import io.fisache.firebase_auth.base.annotation.ActivityScope;

@ActivityScope
@Subcomponent(
        modules = {
                LoginActivityModule.class
        }
)
public interface LoginActivityComponent {
    LoginActivity inject(LoginActivity activity);
}
