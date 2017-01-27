package io.fisache.firebase_auth.ui.main;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;
import io.fisache.firebase_auth.base.annotation.ActivityScope;
import io.fisache.firebase_auth.data.model.User;
import io.fisache.firebase_auth.data.source.remote.FirebaseUserService;
import io.fisache.firebase_auth.data.source.remote.FriendsService;
import io.fisache.firebase_auth.data.source.remote.UserService;

@Module
public class MainActivityModule {
    private MainActivity activity;

    public MainActivityModule(MainActivity activity) {
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    MainActivity provideMainActivity() {
        return activity;
    }

    @ActivityScope
    @Provides
    MainPresenter provideMainPresenter(User user,
                                       FirebaseUserService firebaseUserService, UserService userService, FriendsService friendsService) {
        return new MainPresenter(activity, user, firebaseUserService, userService, friendsService);

    }

    @ActivityScope
    @Provides
    LinearLayoutManager provideLinearLayoutManager() {
        return new LinearLayoutManager(activity);
    }

    @ActivityScope
    @Provides
    MainAdapter provideMainAdapter() {
        return new MainAdapter(activity);
    }

    @Provides
    @ActivityScope
    AlertDialog.Builder provideAlerDialogBuilder() {
        return new AlertDialog.Builder(activity);
    }
}
