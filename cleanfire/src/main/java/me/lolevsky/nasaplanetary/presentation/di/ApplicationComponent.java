package me.lolevsky.nasaplanetary.presentation.di;

import javax.inject.Singleton;

import dagger.Component;
import me.lolevsky.nasaplanetary.MainApplication;
import me.lolevsky.nasaplanetary.presentation.view.screens.main.MainActivity;
import me.lolevsky.nasaplanetary.presentation.view.screens.marsphoto.MarsPhotosActivity;
import me.lolevsky.nasaplanetary.presentation.view.screens.photocomment.PhotoCommentsActivity;
import me.lolevsky.nasaplanetary.presentation.view.screens.planetoryapod.PlanetaryApodActivity;
import me.lolevsky.nasaplanetary.presentation.view.screens.splash.SplashActivity;

@Singleton
@Component(
        modules = {
                NetworkModule.class,
                DataModule.class,
                DomainModule.class,
                Rx.class
        }
)

public interface ApplicationComponent {
    void inject(MainApplication mainApplication);

    void inject(SplashActivity splashActivity);

    void inject(MainActivity mainActivity);

    void inject(PlanetaryApodActivity planetaryApodActivity);

    void inject(MarsPhotosActivity marsPhotosActivity);

    void inject(PhotoCommentsActivity photoCommentsActivity);
}
