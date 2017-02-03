package me.lolevsky.nasaplanetary.presentation.view.screens.photocomment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.GenericTypeIndicator;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.internal.Preconditions;
import me.lolevsky.nasaplanetary.data.net.request.PhotoComment;
import me.lolevsky.nasaplanetary.domain.interactor.CommentsInteraptor;
import me.lolevsky.nasaplanetary.domain.tracking.ITracking;
import me.lolevsky.nasaplanetary.presentation.model.objects.MarsPhotoComments;
import me.lolevsky.nasaplanetary.presentation.view.presenter.Presenter;
import rx.Subscriber;

public class PhotoCommentPresenter implements Presenter<PhotoCommentsActivity, MarsPhotoComments> {
    PhotoCommentsActivity view;
    MarsPhotoComments model;
    CommentsInteraptor comments;
    ITracking tracking;

    @Inject
    public PhotoCommentPresenter(CommentsInteraptor comments,
                                 ITracking tracking) {
        this.comments = Preconditions.checkNotNull(comments);
        this.tracking = Preconditions.checkNotNull(tracking);

        tracking.LogEventScreen("PhotoCommentScreen");
    }

    @Override public void resume() {

    }

    @Override public void pause() {

    }

    @Override public void destroy() {
        view = null;
        comments.unsubscribe();
    }

    @Override public void loadData(String... params) {
        model = new MarsPhotoComments();
        model.setPhotoId(Integer.valueOf(params[0]));

        comments.execute(new Subscriber<DataSnapshot>() {
            @Override public void onCompleted() {

            }

            @Override public void onError(Throwable e) {
                if (view != null) {
                    view.onError(e.getMessage());
                }
            }

            @Override public void onNext(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, PhotoComment>> t = new GenericTypeIndicator<Map<String, PhotoComment>>() {
                };

                Map<String, PhotoComment> stringList = dataSnapshot.getValue(t);

                if (stringList != null && stringList.size() > 0) {
                    List<PhotoComment> comments = new ArrayList<>(stringList.values());

                    Collections.sort(comments, new Comparator<PhotoComment>() {
                        @Override public int compare(PhotoComment t1, PhotoComment t2) {
                            return t1.getDate().compareTo(t2.getDate());
                        }
                    });

                    model.setComments(comments);
                }

                if (view != null) {
                    view.onComplete(model);
                }
            }
        }, params[0]);
    }

    @Override public void setView(@NonNull PhotoCommentsActivity view) {
        this.view = view;
    }

    @Override public MarsPhotoComments getModel() {
        return model;
    }

    @Override public void setModel(MarsPhotoComments model) {
        this.model = model;
    }

    public void sendComment(String message) {
        tracking.LogEventClick("sendComment");

        comments.sendComment(String.valueOf(model.getPhotoId()), message).subscribe(new Subscriber<Boolean>() {
            @Override public void onCompleted() {

            }

            @Override public void onError(Throwable e) {
                if(view != null){
                    view.sendComplite(false);
                    view.onError(e.getMessage());
                }
            }

            @Override public void onNext(Boolean o) {
                if(view != null) {
                    view.sendComplite(o);
                }
            }
        });
    }

    @Override public ITracking getTracking() {
        return tracking;
    }
}
