package com.arbuleac.loan;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @since 3/23/16.
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_search_loan)
    Button btnSearchLoan;
    @Bind(R.id.btn_custom_loan)
    Button btnCustomLoan;
    @Bind(R.id.tv_headline)
    TextView tvHeadline;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initUI();

        //TODO Remove this from production
//        try {
//            List<Loan> loanList = BankParser.parseLoans(this);
//            Injector.obtain(Firebase.class).push().setValue(loanList);
//            Timber.d("Parsed loans, got %d types.", loanList.size());
//        } catch (IOException e) {
//            Timber.e("Failed to parse loans.");
//        }
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
}
