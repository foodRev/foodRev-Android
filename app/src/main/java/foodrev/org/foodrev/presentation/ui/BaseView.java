package foodrev.org.foodrev.presentation.ui;

public interface BaseView {
    void attachPresenter();

    void showProgressDialog();

    void hideProgressDialog();

    void showError(String message);
}
