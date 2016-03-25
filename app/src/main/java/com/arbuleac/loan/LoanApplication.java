package com.arbuleac.loan;

import android.app.Application;

import com.arbuleac.loan.utils.Injector;
import com.firebase.client.Firebase;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

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

    private Tracker tracker;

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (tracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            tracker = analytics.newTracker("UA-52363847-2");
        }
        return tracker;
    }
}
