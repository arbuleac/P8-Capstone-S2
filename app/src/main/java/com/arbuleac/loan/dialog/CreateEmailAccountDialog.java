package com.arbuleac.loan.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.arbuleac.loan.R;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * @since 3/23/16.
 */
public class CreateEmailAccountDialog extends DialogFragment {

    @Bind(R.id.email)
    EditText email;
    @Bind(R.id.password)
    EditText password;
    private Firebase firebase;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogView = inflater.inflate(R.layout.create_account_dialog, null);
        builder.setView(dialogView);
        ButterKnife.bind(this, dialogView);
        this.setRetainInstance(true);
        return builder.create();
    }

    public CreateEmailAccountDialog setFirebase(Firebase firebase) {
        this.firebase = firebase;
        return this;
    }

    @OnClick(R.id.password_button)
    public void onCreateAccount() {
        final String email = this.email.getText().toString();
        final String password = this.password.getText().toString();
        firebase.createUser(email, password, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                //TODO automatically login or wait for create account feature to be implemented in firebaseUI.
                showMessage("Account create successfully, login using email");
                CreateEmailAccountDialog.this.dismiss();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                showError(firebaseError.getMessage());
            }
        });
    }

    private void showMessage(String s) {
        Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    private void showError(String message) {
        Timber.w("Create account error, Reason: %s", message);
        showMessage("Failed to create account. " + message);
    }
}
