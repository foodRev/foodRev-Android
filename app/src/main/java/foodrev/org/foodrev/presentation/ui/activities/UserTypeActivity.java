package foodrev.org.foodrev.presentation.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import foodrev.org.foodrev.R;
import foodrev.org.foodrev.presentation.presenters.UserTypePresenter;
import foodrev.org.foodrev.presentation.presenters.impl.UserTypePresenterImpl;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.DriverMode.DriverModeActivity;

public class UserTypeActivity extends AppCompatActivity implements UserTypePresenter.View, View.OnClickListener {
    private UserTypePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Darin", FirebaseAuth.getInstance().getCurrentUser().getUid());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);
        attachPresenter();
        attachButtons();
    }

    public void attachPresenter() {
        mPresenter = (UserTypePresenter) getLastCustomNonConfigurationInstance();
        if (mPresenter == null) {
            mPresenter = new UserTypePresenterImpl();
        }
        mPresenter.attachView(this);
    }



    private void attachButtons() {
        findViewById(R.id.select_type_coordinator_button).setOnClickListener(this);
        findViewById(R.id.select_type_driver_button).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int buttonId = view.getId();
        if (buttonId == R.id.select_type_coordinator_button) {
            mPresenter.coordinatorSelected();
        } else if (buttonId == R.id.select_type_driver_button) {
            mPresenter.driverSelected();
        }
    }

    @Override
    public void goToCoordinatorMode() {
        startActivity(new Intent(this, MainActivity.class));
//        finish();
    }

    @Override
    public void goToDriverMode() {
        startActivity(new Intent(this, DriverModeActivity.class));
//        finish();
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
