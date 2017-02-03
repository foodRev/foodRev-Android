package io.fisache.firebase_auth.data.friend;

import dagger.Module;
import dagger.Provides;
import io.fisache.firebase_auth.base.annotation.FriendScope;
import io.fisache.firebase_auth.data.model.Friend;

@Module
public class FriendModule {
    private Friend friend;

    public FriendModule(Friend friend) {
        this.friend = friend;
    }

    @FriendScope
    @Provides
    Friend provideFriend() {
        return friend;
    }
}
