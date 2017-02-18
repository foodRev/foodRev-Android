package foodrev.org.foodrev.presentation.presenters.impl;

import com.google.firebase.database.FirebaseDatabase;

import foodrev.org.foodrev.domain.executor.Executor;
import foodrev.org.foodrev.domain.executor.MainThread;
import foodrev.org.foodrev.domain.infos.DriverInfo;
import foodrev.org.foodrev.domain.infos.PopulateInfos;
import foodrev.org.foodrev.domain.interactors.GetFirebaseInfoInteractor;
import foodrev.org.foodrev.domain.interactors.impl.GetFirebaseInfoInteractorImpl;
import foodrev.org.foodrev.domain.wrappers.FirebaseDatabaseWrapper;
import foodrev.org.foodrev.presentation.presenters.MainPresenter;


public class MainPresenterImpl implements MainPresenter, GetFirebaseInfoInteractor.Callback {

    private MainPresenter.View mView;
    private Executor mExecutor;
    private MainThread mMainThread;
    private PopulateInfos mPopulateInfos;

    public MainPresenterImpl(Executor executor, MainThread mainThread) {
        mExecutor = executor;
        mMainThread = mainThread;

    }

    @Override
    public void onCommunityCenterInfoUpdated() {
        if(mPopulateInfos != null) {
            mView.refreshCommunityCenterInfos(mPopulateInfos.getCommunityCenterInfo());
        }
    }

    @Override
    public void onCareInfoUpdated() {
        if(mPopulateInfos != null) {
            mView.refreshCareInfos(mPopulateInfos.getCareInfo());
        }
    }

    @Override
    public void onDonationCenterInfoUpdated() {
        if(mPopulateInfos != null) {
            mView.refreshDonationCenterInfos(mPopulateInfos.getDonationCenterInfo());
        }
    }

    @Override
    public void onDriverInfoUpdated(DriverInfo driverInfo) {
        if(mPopulateInfos != null) {
            mView.refreshDriverInfos(mPopulateInfos.getDriverInfo());
        }
        mView.showToastTest(driverInfo.getDriver(0).getName());
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

    @Override
    public void retrievePopulateInfos(PopulateInfos populateInfos) {
        mPopulateInfos = populateInfos;
    }
}
