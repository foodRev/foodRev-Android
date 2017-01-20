package foodrev.org.foodrev.presentation.ui;

public interface BaseView {

    void showProgress();

    void hideProgress();

    void showError(String message);
}
