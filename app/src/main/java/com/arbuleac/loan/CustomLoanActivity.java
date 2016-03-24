package com.arbuleac.loan;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arbuleac.loan.data.Bank;
import com.arbuleac.loan.data.Loan;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * @since 3/23/16.
 */
public class CustomLoanActivity extends AppCompatActivity {

    @Bind(R.id.et_loan_amount)
    EditText etLoanAmount;
    @Bind(R.id.et_time)
    EditText etTime;
    @Bind(R.id.et_dae)
    EditText etDae;
    @Bind(R.id.btn_search_loan)
    Button btnSearchLoan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initUI();
    }

    private void initUI() {
        int btnColor;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            btnColor = getColor(R.color.colorAccent);
        } else {
            btnColor = getResources().getColor(R.color.colorAccent);
        }

        btnSearchLoan.getBackground().setColorFilter(btnColor, PorterDuff.Mode.MULTIPLY);
        btnSearchLoan.setTextColor(Color.WHITE);
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

    @OnClick(R.id.btn_search_loan)
    public void onSearch() {
        gotoDetails();
    }

    private void gotoDetails() {
        double dae;

        try {
            dae = Double.parseDouble(etDae.getText().toString());
        } catch (NumberFormatException ex) {
            Toast.makeText(this, R.string.error_dae, Toast.LENGTH_LONG).show();
            Timber.w("DAE was not entered properly: %s", etDae.getText().toString());
            return;
        }

        int duration;
        try {
            duration = Integer.parseInt(etTime.getText().toString());
        } catch (NumberFormatException ex) {
            Toast.makeText(this, R.string.error_duration, Toast.LENGTH_LONG).show();
            Timber.w("Duration was not entered properly: %s", etTime.getText().toString());
            return;
        }

        int loanAmount;
        try {
            loanAmount = Integer.parseInt(etLoanAmount.getText().toString());
        } catch (NumberFormatException ex) {
            Toast.makeText(this, R.string.error_amount, Toast.LENGTH_LONG).show();
            Timber.w("Loan amount was not entered properly: %s", etLoanAmount.getText().toString());
            return;
        }

        Loan loan = new Loan();
        Bank bank = new Bank();
        bank.setName("Custom");
        loan.setDae(dae + "%");
        loan.setBank(bank);
        loan.setName("Custom loan product");
        startActivity(DetailsActivity.newIntent(this, loan, loanAmount, duration));
    }
}
