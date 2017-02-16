package foodrev.org.foodrev.presentation.presenters.impl;

import com.google.firebase.database.FirebaseDatabase;

import foodrev.org.foodrev.domain.executor.Executor;
import foodrev.org.foodrev.domain.executor.MainThread;
import foodrev.org.foodrev.domain.infos.PopulateInfos;
import foodrev.org.foodrev.domain.interactors.GetFirebaseInfoInteractor;
import foodrev.org.foodrev.domain.interactors.impl.GetFirebaseInfoInteractorImpl;
import foodrev.org.foodrev.domain.wrappers.FirebaseDatabaseWrapper;
import foodrev.org.foodrev.presentation.presenters.MainPresenter;


public class MainPresenterImpl implements MainPresenter, GetFirebaseInfoInteractor.Callback {

    private MainPresenter.View mView;
    private Executor mExecutor;
    private MainThread mMainThread;

    public MainPresenterImpl(Executor executor, MainThread mainThread) {
        mExecutor = executor;
        mMainThread = mainThread;

    }

    @Override
    public void onDataReceived(PopulateInfos populateInfos) {
        //mView. display the info in recyclerviews
    }

    @Override
    public void onDataReceiveFailed() {

    }

    @Override
    public void attachView(View view) {
        mView = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void resume() {
        FirebaseDatabaseWrapper wrapper = new FirebaseDatabaseWrapper();
        final FirebaseDatabase firebaseDatabaseInstance = wrapper.getInstance();
        GetFirebaseInfoInteractorImpl interactor =
                new GetFirebaseInfoInteractorImpl(mExecutor, mMainThread, this, firebaseDatabaseInstance);
        interactor.execute();

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }
}
