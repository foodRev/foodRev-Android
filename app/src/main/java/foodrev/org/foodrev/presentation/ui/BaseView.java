package foodrev.org.foodrev.presentation.ui;

public interface BaseView {

    void showProgressDialog();

    void hideProgressDialog();

    void showError(String message);
}
