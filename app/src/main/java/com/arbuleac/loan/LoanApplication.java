package com.arbuleac.loan;

import android.app.Application;

import com.arbuleac.loan.utils.Injector;
import com.firebase.client.Firebase;

import timber.log.Timber;

/**
 * @since 3/23/16.
 */
public class LoanApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
        Timber.plant(new Timber.DebugTree());
        Injector.init(this);
    }
}
