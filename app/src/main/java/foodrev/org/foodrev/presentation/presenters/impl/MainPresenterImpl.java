package foodrev.org.foodrev.presentation.presenters.impl;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import foodrev.org.foodrev.domain.interactors.SampleInteractor;
import java.io.Serializable;

import foodrev.org.foodrev.App;
import foodrev.org.foodrev.domain.executor.Executor;
import foodrev.org.foodrev.domain.executor.MainThread;
import foodrev.org.foodrev.domain.infos.CareInfo;
import foodrev.org.foodrev.domain.infos.CommunityCenterInfo;
import foodrev.org.foodrev.domain.infos.DonationCenterInfo;
import foodrev.org.foodrev.domain.infos.DriverInfo;
import foodrev.org.foodrev.domain.infos.PopulateInfos;
import foodrev.org.foodrev.domain.infos.models.Destination;
import foodrev.org.foodrev.domain.infos.models.DonationCenter;
import foodrev.org.foodrev.domain.interactors.GetFirebaseInfoInteractor;
import foodrev.org.foodrev.domain.interactors.impl.GetFirebaseInfoInteractorImpl;
import foodrev.org.foodrev.domain.wrappers.FirebaseDatabaseWrapper;
import foodrev.org.foodrev.presentation.presenters.MainPresenter;


public class MainPresenterImpl implements MainPresenter, GetFirebaseInfoInteractor.Callback {

    private MainPresenter.View mView;
    private Executor mExecutor;
    private MainThread mMainThread;
    private boolean mCareInfoPopulated = false;
    private boolean mCommunityCenterInfoPopulated = false;
    private boolean mDonationCenterInfoPopulated = false;
    private boolean mDriverInfoPopulated = false;

    public MainPresenterImpl(Executor executor, MainThread mainThread) {
        mExecutor = executor;
        mMainThread = mainThread;
    }

    private void checkAllPopulated() {
        if(
//                mCareInfoPopulated
//                &&
        mCommunityCenterInfoPopulated
                && mDonationCenterInfoPopulated
                && mDriverInfoPopulated) {
            mView.onAllPopulated();
        }
    }
    @Override
    public void onCommunityCenterInfoUpdated(CommunityCenterInfo communityCenterInfo) {
        mView.refreshCommunityCenterInfos(communityCenterInfo);
        mCommunityCenterInfoPopulated = true;
        checkAllPopulated();
    }

    @Override
    public void onCareInfoUpdated(CareInfo careInfo) {
        //TODO: fix CareInfo
        mCareInfoPopulated = true;
        checkAllPopulated();
    }

    @Override
    public void onDonationCenterInfoUpdated(DonationCenterInfo donationCenterInfo) {
        mView.refreshDonationCenterInfos(donationCenterInfo);
        mDonationCenterInfoPopulated = true;
        checkAllPopulated();
    }

    @Override
    public void onDriverInfoUpdated(DriverInfo driverInfo) {
        mView.refreshDriverInfos(driverInfo);
        mDriverInfoPopulated = true;
        checkAllPopulated();
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
    public void signOut() {
        // Firebase sign out
        if(mView != null) {
            FirebaseAuth.getInstance().signOut();
            mView.goToSignInActivity();
        }
    }
}
