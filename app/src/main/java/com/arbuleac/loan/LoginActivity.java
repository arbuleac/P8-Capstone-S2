package com.arbuleac.loan;

import android.content.Intent;
import android.os.Bundle;

import com.arbuleac.loan.dialog.CreateEmailAccountDialog;
import com.arbuleac.loan.utils.Injector;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.ui.auth.core.AuthProviderType;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;
import com.firebase.ui.auth.core.FirebaseLoginError;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class LoginActivity extends FirebaseLoginBaseActivity {

    @Override
    protected void onStart() {
        super.onStart();
        setEnabledAuthProvider(AuthProviderType.FACEBOOK);
        setEnabledAuthProvider(AuthProviderType.PASSWORD);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected Firebase getFirebaseRef() {
        return Injector.obtain(Firebase.class);
    }

    @Override
    protected void onFirebaseLoginProviderError(FirebaseLoginError firebaseLoginError) {
        showError(firebaseLoginError.message);
    }

    @Override
    protected void onFirebaseLoginUserError(FirebaseLoginError firebaseLoginError) {
        showError(firebaseLoginError.message);
    }

    @Override
    protected void onFirebaseLoggedIn(AuthData authData) {
        super.onFirebaseLoggedIn(authData);
        Timber.d("Successfully logged in.");
        gotoMain();
    }

    @OnClick(R.id.btn_login)
    public void onLogin() {
        showFirebaseLoginPrompt();
    }

    @OnClick(R.id.btn_signup)
    public void onSignUp() {
        showCreateAccountPrompt();
    }

    private void showCreateAccountPrompt() {
        CreateEmailAccountDialog createEmailAccountDialog = new CreateEmailAccountDialog();
        createEmailAccountDialog.setFirebase(getFirebaseRef());
        createEmailAccountDialog.show(getFragmentManager(), "create_account_dialog");
    }

    private void gotoMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void showError(String message) {
        dismissFirebaseLoginPrompt();
        Timber.w("Failed to login. Reason: %s", message);
    }
}
