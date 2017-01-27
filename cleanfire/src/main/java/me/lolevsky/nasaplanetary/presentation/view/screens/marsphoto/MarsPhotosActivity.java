package me.lolevsky.nasaplanetary.presentation.view.screens.marsphoto;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.lolevsky.nasaplanetary.MainApplication;
import me.lolevsky.nasaplanetary.R;
import me.lolevsky.nasaplanetary.presentation.adapters.MarsPhotosAdapter;
import me.lolevsky.nasaplanetary.domain.imageloader.IImageLoader;
import me.lolevsky.nasaplanetary.presentation.model.MarsPhotosModel;
import me.lolevsky.nasaplanetary.presentation.view.presenter.Presenter;
import me.lolevsky.nasaplanetary.presentation.view.BaseActivity;

public class MarsPhotosActivity extends BaseActivity<MarsPhotosModel> {
    @Inject MainApplication context;
    @Inject MarsPhotosPresenter marsPhotosPresenter;
    @Inject IImageLoader imageLoader;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recycler) RecyclerView recyclerView;
    @BindView(R.id.coordinator_layout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.progress_bar_more) ProgressBar progressBarMore;

    MarsPhotosAdapter marsPhotosAdapter;

    boolean isFirstTimeCalled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainApplication) getApplication()).getApplicationComponent().inject(this);

        setContentView(R.layout.activity_mars_photos);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        intActionBar();

        marsPhotosAdapter = new MarsPhotosAdapter(imageLoader, marsPhotosPresenter);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(marsPhotosAdapter);

        marsPhotosPresenter.initPageController(recyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!isFirstTimeCalled){
            isFirstTimeCalled = true;
            marsPhotosPresenter.loadData();
        }
    }

    private void intActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override protected Presenter getPresenter() {
        return marsPhotosPresenter;
    }

    @Override public void onLoading() {
        progressBar.setVisibility(View.VISIBLE);
        coordinatorLayout.setVisibility(View.GONE);
    }

    @Override public void onLoadingMore() {
        progressBarMore.setVisibility(View.VISIBLE);
    }

    @Override public void onComplete(MarsPhotosModel model) {
        marsPhotosAdapter.notifyDataSetChanged();

        progressBar.setVisibility(View.GONE);
        progressBarMore.setVisibility(View.GONE);
        coordinatorLayout.setVisibility(View.VISIBLE);
    }

    @Override public void onError(String error) {
        Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_SHORT).show();

        progressBar.setVisibility(View.GONE);
        progressBarMore.setVisibility(View.GONE);
        coordinatorLayout.setVisibility(View.VISIBLE);
    }
}
