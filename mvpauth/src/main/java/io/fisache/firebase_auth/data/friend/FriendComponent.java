package io.fisache.firebase_auth.data.friend;

import dagger.Subcomponent;
import io.fisache.firebase_auth.base.annotation.FriendScope;

@FriendScope
@Subcomponent(
        modules = {
                FriendModule.class
        }
)
public interface FriendComponent {
//    ChatActivityComponent plus(ChatActivityModule activityModule);
}