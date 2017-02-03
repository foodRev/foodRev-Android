package me.lolevsky.nasaplanetary.presentation.view.screens.main;

import java.util.List;

import javax.inject.Inject;

import dagger.internal.Preconditions;
import me.lolevsky.nasaplanetary.domain.entety.MainScreenEntity;
import me.lolevsky.nasaplanetary.domain.interactor.MainScreenInterapter;
import me.lolevsky.nasaplanetary.domain.tracking.ITracking;
import me.lolevsky.nasaplanetary.presentation.mapper.MainScreenModelDataMapper;
import me.lolevsky.nasaplanetary.presentation.model.MainScreenModule;
import me.lolevsky.nasaplanetary.presentation.view.presenter.BasePresenter;
import me.lolevsky.nasaplanetary.presentation.view.IView;

public class MainPresenter extends BasePresenter<IView, MainScreenModule, List<MainScreenEntity>> {
    @Inject
    public MainPresenter(MainScreenInterapter mainScreenInterapter,
                         MainScreenModelDataMapper mainScreenModelDataMapper,
                         ITracking tracking) {
        super(Preconditions.checkNotNull(mainScreenInterapter), Preconditions.checkNotNull(mainScreenModelDataMapper)
                , Preconditions.checkNotNull(tracking));

        tracking.LogEventScreen("MainScreen");
    }

    @Override public void resume() {

    }

    @Override public void pause() {

    }

    @Override public void pagingAddNewData(MainScreenModule newModel) {

    }

    @Override public void onNewPageRequest(int lastItemIndex) {

    }
}
