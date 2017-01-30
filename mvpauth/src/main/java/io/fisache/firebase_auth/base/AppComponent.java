package io.fisache.firebase_auth.base;

import javax.inject.Singleton;

import dagger.Component;
import io.fisache.firebase_auth.data.firebase.FirebaseModule;
import io.fisache.firebase_auth.data.user.UserComponent;
import io.fisache.firebase_auth.data.user.UserModule;
import io.fisache.firebase_auth.ui.login.LoginActivityComponent;
import io.fisache.firebase_auth.ui.login.LoginActivityModule;
import io.fisache.firebase_auth.ui.splash.SplashActivityComponent;
import io.fisache.firebase_auth.ui.splash.SplashActivityModule;

@Singleton
@Component(
        modules = {
                AppModule.class,
                FirebaseModule.class
        }
)
public interface AppComponent {

    SplashActivityComponent plus(SplashActivityModule activityModule);

    LoginActivityComponent plus(LoginActivityModule activityModule);

    UserComponent plus(UserModule userModule);
}
