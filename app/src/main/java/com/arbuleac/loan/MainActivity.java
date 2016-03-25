package com.arbuleac.loan;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.arbuleac.loan.provider.StatsContract;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @since 3/23/16.
 */
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int STATS_LOADER = 1337;
    @Bind(R.id.btn_search_loan)
    Button btnSearchLoan;
    @Bind(R.id.btn_custom_loan)
    Button btnCustomLoan;
    @Bind(R.id.tv_headline)
    TextView tvHeadline;
    @Bind(R.id.av_banner)
    AdView avBanner;
    @Bind(R.id.tb_actionbar)
    Toolbar tbActionbar;
    @Bind(R.id.tv_log)
    TextView tvLog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        trackAppStart();

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initUI();

        setSupportActionBar(tbActionbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.app_name));
        }

        //TODO Remove this from production
//        try {
//            List<Loan> loanList = BankParser.parseLoans(this);
//            Injector.obtain(Firebase.class).push().setValue(loanList);
//            Timber.d("Parsed loans, got %d types.", loanList.size());
//        } catch (IOException e) {
//            Timber.e("Failed to parse loans.");
//        }

        AdRequest adRequest = new AdRequest.Builder().build();
        avBanner.loadAd(adRequest);

        Tracker tracker = ((LoanApplication) getApplication()).getDefaultTracker();
        tracker.setScreenName(MainActivity.class.getSimpleName());
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

        getSupportLoaderManager().restartLoader(STATS_LOADER, null, this);
    }

    private void trackAppStart() {
        ContentValues content = new ContentValues();
        content.put(StatsContract.StatsEntry.COLUMN_VALUE, "App started " + System.currentTimeMillis());
        getContentResolver().insert(StatsContract.StatsEntry.CONTENT_URI, content);
    }

    private void initUI() {
        int btnColor;
        int btnTextColor;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            btnColor = getColor(R.color.colorAccent);
            btnTextColor = getColor(android.R.color.white);
        } else {
            btnColor = getResources().getColor(R.color.colorAccent);
            btnTextColor = getResources().getColor(android.R.color.white);
        }

        btnSearchLoan.getBackground().setColorFilter(btnColor, PorterDuff.Mode.MULTIPLY);
        btnCustomLoan.getBackground().setColorFilter(btnColor, PorterDuff.Mode.MULTIPLY);

        btnSearchLoan.setTextColor(btnTextColor);
        btnCustomLoan.setTextColor(btnTextColor);
    }

    @OnClick(R.id.btn_search_loan)
    public void onSearchLoan(View view) {
        startActivity(new Intent(this, SearchActivity.class));
    }

    @OnClick(R.id.btn_custom_loan)
    public void onCustomLoan(View view) {
        startActivity(new Intent(this, CustomLoanActivity.class));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, StatsContract.StatsEntry.CONTENT_URI, null, null, null, StatsContract.StatsEntry.COLUMN_VALUE + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null) {
            return;
        }

        if (data.moveToFirst()) {
            tvLog.setText(data.getString(data.getColumnIndex(StatsContract.StatsEntry.COLUMN_VALUE)));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //ignore
    }
}
