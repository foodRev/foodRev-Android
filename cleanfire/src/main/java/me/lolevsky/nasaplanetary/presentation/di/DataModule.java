package me.lolevsky.nasaplanetary.presentation.di;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.lolevsky.nasaplanetary.BuildConfig;
import me.lolevsky.nasaplanetary.MainApplication;
import me.lolevsky.nasaplanetary.data.remoteconfig.RemoteConfig;
import me.lolevsky.nasaplanetary.data.repository.Comments;
import me.lolevsky.nasaplanetary.data.repository.MarsPhotos;
import me.lolevsky.nasaplanetary.domain.imageloader.IImageLoader;
import me.lolevsky.nasaplanetary.data.imageloader.ImageLoader;
import me.lolevsky.nasaplanetary.data.repository.PlanetaryApod;
import me.lolevsky.nasaplanetary.data.net.NasaService;
import me.lolevsky.nasaplanetary.domain.remoteconfig.IRemoteConfig;
import me.lolevsky.nasaplanetary.domain.repository.IComments;
import me.lolevsky.nasaplanetary.domain.repository.IMarsPhotos;
import me.lolevsky.nasaplanetary.domain.tracking.ITracking;
import me.lolevsky.nasaplanetary.data.tracking.Tracking;
import me.lolevsky.nasaplanetary.domain.repository.IPlanetaryApod;
import rx.Observable;

@Module
public class DataModule {

    MainApplication application;

    public DataModule(MainApplication mainApplication) {
        this.application = mainApplication;
    }

    @Provides MainApplication provideMainApplication() {
        return application;
    }

    @Provides IPlanetaryApod providePlanetaryApod(NasaService nasaService) {
        return new PlanetaryApod(nasaService);
    }

    @Provides IMarsPhotos provideMarsPhotos(NasaService nasaService) {
        return new MarsPhotos(nasaService);
    }

    @Singleton @Provides ITracking provideTracking(MainApplication application) {
        int playServicesStatus = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(application);
        if (playServicesStatus == ConnectionResult.SUCCESS) {
            return new Tracking(FirebaseAnalytics.getInstance(application));
        } else {
            return new ITracking() {

                @Override public void LogEventScreen(String eventName) {

                }

                @Override public void LogEventClick(String eventName) {

                }

                @Override public void LogEvent(String category, String eventName) {

                }

                @Override public void LogException(String exception) {

                }

                @Override public void setUserProperty(String experimentName, IRemoteConfig.ExperimentVariant experimentVariant) {

                }
            };
        }
    }

    @Singleton @Provides IComments provideComments(MainApplication application) {
        int playServicesStatus = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(application);
        if (playServicesStatus == ConnectionResult.SUCCESS) {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseDatabase.setPersistenceEnabled(true);
            DatabaseReference databaseReference = firebaseDatabase.getReference();
            return new Comments(databaseReference);
        }else{
            return new IComments() {
                @Override public Observable getComments(String photoId) {
                    return Observable.error(new RuntimeException("FireBase not supported"));
                }

                @Override public Observable sendComment(String photoId, String message) {
                    return Observable.error(new RuntimeException("FireBase not supported"));
                }
            };
        }
    }

    @Singleton @Provides IRemoteConfig provideRemoteConfig(MainApplication application, ITracking tracking) {
        int playServicesStatus = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(application);
        if (playServicesStatus == ConnectionResult.SUCCESS) {
            return new RemoteConfig(BuildConfig.DEBUG, tracking);
        }else{
            return new IRemoteConfig() {

                @Override public ExperimentVariant getExperimentVariant(String key) {
                    return ExperimentVariant.NONE;
                }
            };
        }
    }

    @Singleton @Provides IImageLoader provideImageLoader(MainApplication mainApplication) {
        return new ImageLoader(mainApplication);
    }
}
