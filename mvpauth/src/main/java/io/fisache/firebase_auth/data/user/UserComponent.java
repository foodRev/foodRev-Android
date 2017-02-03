package io.fisache.firebase_auth.data.user;

import dagger.Subcomponent;
import io.fisache.firebase_auth.base.annotation.UserScope;
import io.fisache.firebase_auth.data.friend.FriendComponent;
import io.fisache.firebase_auth.data.friend.FriendModule;
import io.fisache.firebase_auth.ui.main.MainActivityComponent;
import io.fisache.firebase_auth.ui.main.MainActivityModule;

@UserScope
@Subcomponent(
        modules = {
                UserModule.class
        }
)
public interface UserComponent {
        MainActivityComponent plus(MainActivityModule activityModule);

        FriendComponent plus(FriendModule friendModule);
}
