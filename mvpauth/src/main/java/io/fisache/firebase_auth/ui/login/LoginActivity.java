package io.fisache.firebase_auth.ui.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.internal.CallbackManagerImpl;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fisache.firebase_auth.R;
import io.fisache.firebase_auth.base.BaseActivity;
import io.fisache.firebase_auth.base.BaseApplication;
import io.fisache.firebase_auth.data.model.User;
import io.fisache.firebase_auth.ui.main.MainActivity;

public class LoginActivity extends BaseActivity {

    public static final int REQUEST_SIGN_GOOGLE = 9001;

    @Bind(R.id.btnGl)
    Button btnGl;
    @Bind(R.id.pbLoading)
    ProgressBar pbLoading;

    @Inject
    LoginPresenter presenter;
    @Inject
    AlertDialog.Builder addAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this).getAppComponent()
                .plus(new LoginActivityModule(this))
                .inject(this);
    }

    @OnClick(R.id.btnGl)
    public void onBtnLoginWithGoogle() {
        Intent intent = presenter.loginWithGoogle();
        startActivityForResult(intent, REQUEST_SIGN_GOOGLE);
    }

    public void showLoginFail() {
        Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
    }

    public void showLoginSuccess(User user) {
        MainActivity.startWithUser(this, user);
    }

    public void showLoading(boolean loading) {
        pbLoading.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    public void showInsertUsername(final User user) {

        addAlertDialog.setTitle("Insert your username");
        addAlertDialog.setMessage("Be sure to enter");

        final EditText etUsername = new EditText(this);
        etUsername.setSingleLine();
        addAlertDialog.setView(etUsername);

        addAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String username = etUsername.getText().toString();
                dialog.dismiss();
                presenter.createUser(user, username);
            }
        });

        addAlertDialog.show();
    }

    public void showExistUsername(User user, String username) {
        Toast.makeText(this, "Exist username" + username, Toast.LENGTH_LONG).show();
        showInsertUsername(user);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // google
        if(requestCode == REQUEST_SIGN_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            presenter.getAuthWithGoogle(result);
        }

    }
}
