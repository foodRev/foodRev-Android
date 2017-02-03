package me.lolevsky.nasaplanetary.presentation.view.screens.planetoryapod;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.lolevsky.nasaplanetary.MainApplication;
import me.lolevsky.nasaplanetary.R;
import me.lolevsky.nasaplanetary.domain.imageloader.IImageLoader;
import me.lolevsky.nasaplanetary.presentation.model.ApodModel;
import me.lolevsky.nasaplanetary.presentation.view.presenter.Presenter;
import me.lolevsky.nasaplanetary.presentation.view.BaseActivity;
import me.lolevsky.nasaplanetary.presentation.widget.ProgressImageView;

public class PlanetaryApodActivity extends BaseActivity<ApodModel> {
    @Inject PlanetaryApodPresenter planetaryApodPresenter;
    @Inject IImageLoader imageLoader;

    @BindView(R.id.date) TextView dateTextView;
    @BindView(R.id.explanation) TextView explanationTextView;
    @BindView(R.id.copyright) TextView copyrightTextView;
    @BindView(R.id.collapsible_toolbar) Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_layout) CollapsingToolbarLayout cllapsingToolbarLayout;
    @BindView(R.id.coordinator_layout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.image_header) ProgressImageView imageView;

    boolean isFirstTimeCalled = false;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainApplication) getApplication()).getApplicationComponent().inject(this);

        setContentView(R.layout.activity_planetary_apod);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        intActionBar();
    }

    @Override protected void onStart() {
        super.onStart();

        if(!isFirstTimeCalled){
            isFirstTimeCalled = true;
            planetaryApodPresenter.loadData();
        }
    }

    private void intActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }

        cllapsingToolbarLayout.setTitle(title);
    }

    @Override protected Presenter getPresenter() {
        return planetaryApodPresenter;
    }

    @Override public void onLoading() {
        progressBar.setVisibility(View.VISIBLE);
        coordinatorLayout.setVisibility(View.GONE);
    }

    @Override public void onLoadingMore() {

    }

    @Override public void onComplete(ApodModel model) {
        setTitle(model.getTitle());
        dateTextView.setText(model.getDate());
        explanationTextView.setText(model.getExplanation());
        copyrightTextView.setText(model.getCopyright());

        imageLoader.loadImage(model.getUrl(), R.drawable.back_main, imageView.getImageView(), imageView.getProgressBar());

        progressBar.setVisibility(View.GONE);
        coordinatorLayout.setVisibility(View.VISIBLE);
    }

    @Override public void onError(String error) {
        Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_SHORT).show();

        progressBar.setVisibility(View.GONE);
        coordinatorLayout.setVisibility(View.VISIBLE);
    }
}
