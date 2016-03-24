package com.arbuleac.loan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.arbuleac.loan.adapter.ResultsAdapter;
import com.arbuleac.loan.data.Loan;
import com.arbuleac.loan.utils.Injector;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * @since 3/23/16.
 */
public class ResultActivity extends AppCompatActivity {

    public static final String EXTRA_DURATION = "extra_duration";
    public static final String EXTRA_AMOUNT = "extra_amount";
    @Bind(R.id.rv_list)
    RecyclerView rvResults;
    private ResultsAdapter adapter;
    private List<Loan> loanList = new ArrayList<>();
    private ResultsAdapter.LoanClickListener loanClickListener;
    private int amount;
    private int duration;


    public static Intent newResultActivityIntent(Context context, int duration, int amount) {
        Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra(EXTRA_DURATION, duration);
        intent.putExtra(EXTRA_AMOUNT, amount);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        this.amount = getIntent().getIntExtra(EXTRA_AMOUNT, 0);
        this.duration = getIntent().getIntExtra(EXTRA_DURATION, 0);
        registerDataListener();
        loanClickListener = new ResultsAdapter.LoanClickListener() {
            @Override
            public void onLoanClicked(Loan loan) {
                gotoLoan(loan);
            }
        };
        rvResults.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvResults.setHasFixedSize(true);
        adapter = new ResultsAdapter(this, loanList, amount, duration, loanClickListener);
        rvResults.setAdapter(adapter);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void registerDataListener() {
        Injector.obtain(Firebase.class).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Loan>> type = new GenericTypeIndicator<List<Loan>>() {
                };
                List<Loan> loans = dataSnapshot.getChildren().iterator().next().getValue(type);
                Timber.d("Loaded list of %d loans", loans.size());
                loanList.addAll(loans);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //ignore
            }
        });
    }

    private void gotoLoan(Loan loan) {
        startActivity(DetailsActivity.newIntent(this, loan, amount, duration));
    }
}
