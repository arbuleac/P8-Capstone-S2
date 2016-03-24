package com.arbuleac.loan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.arbuleac.loan.adapter.LoanDetailsAdapter;
import com.arbuleac.loan.data.Loan;
import com.arbuleac.loan.data.TableRow;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @since 3/23/16.
 */
public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_LOAN = "extra_loan";
    public static final String EXTRA_AMOUNT = "extra_amount";
    public static final String EXTRA_DURATION = "extra_duration";

    @Bind(R.id.rv_list)
    RecyclerView rvList;

    private Loan loan;
    private int amount;
    private int duration;
    private LoanDetailsAdapter adapter;

    public static Intent newIntent(Context context, Loan loan, int amount, int duration) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(EXTRA_LOAN, loan);
        intent.putExtra(EXTRA_AMOUNT, amount);
        intent.putExtra(EXTRA_DURATION, duration);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.loan = getIntent().getParcelableExtra(EXTRA_LOAN);
        this.amount = getIntent().getIntExtra(EXTRA_AMOUNT, 0);
        this.duration = getIntent().getIntExtra(EXTRA_DURATION, 0);

        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(loan.getBank().getName() + " - " + getString(R.string.loan_details));
        }


        List<TableRow> values = getTableData();

        rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvList.setHasFixedSize(true);
        adapter = new LoanDetailsAdapter(this, values);
        rvList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.action_contact);
        menuItem.setVisible(loan.getBank().getContactAddress() != null && !"".equals(loan.getBank().getContactAddress()));
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_contact:
                ShareCompat.IntentBuilder.from(this)
                        .setType("message/rfc822")
                        .addEmailTo(loan.getBank().getContactAddress())
                        .setSubject("Loan information.")
                        .setText("Hello, I need a loan of " + amount + " for a " + duration + " years period. Please let me know when I can come and get one. Best regards")
                        .setChooserTitle("Contact with...")
                        .startChooser();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private List<TableRow> getTableData() {
        double doubleDae = Double.parseDouble(loan.getDae().replace("%", "").replace(",", ".").trim());
        //Source: http://www.calcunation.com/calculator/mortgage-total-cost.php
        double finalAmount = doubleDae / 1200 * amount / (1 - Math.pow(1 + doubleDae / 1200, -duration * 12)) * duration * 12;

        //TODO Consult with someone to get translations in english and create respective values-ro, values dirs
        //TODO Add all strings to resources when translations are ready
        List<TableRow> result = new ArrayList<>();

        tableRow(result, loan.getBank().getName(), null, TableRow.Type.HEADER);
        tableRow(result, "Denumire produs", loan.getName(), TableRow.Type.DATA);
        tableRow(result, "", "", TableRow.Type.DATA);

        tableRow(result, "COSTUL TOTAL AL CREDITULUI", null, TableRow.Type.HEADER);
        tableRow(result, "Comision la acordare (platit la obtinerea creditului)", loan.getInitialCommission(), TableRow.Type.DATA);
        tableRow(result, "Prima rata", (int) finalAmount / (12 * duration) + " RON", TableRow.Type.DATA);
        tableRow(result, "Comision anual (primul an)", loan.getAnnualCommission(), TableRow.Type.DATA);
        tableRow(result, "Costul cu asigurarile (primul an)", loan.getInsuranceCost(), TableRow.Type.DATA);
        tableRow(result, "DAE", loan.getDae(), TableRow.Type.DATA);
        tableRow(result, "Suma de rambursat", (int) finalAmount + " RON", TableRow.Type.DATA);

        tableRow(result, "INFORMATII DESPRE DOBANDA", null, TableRow.Type.HEADER);
        tableRow(result, "Tip dobanda", loan.getLoanInterestsType(), TableRow.Type.DATA);
        tableRow(result, "Mod de fluctuatie dobanda", loan.getLoanInterestsDetails(), TableRow.Type.DATA);

        tableRow(result, "ALTE COMISIOANE", null, TableRow.Type.HEADER);
        tableRow(result, "Conditii speciale", loan.getSpecialConditions(), TableRow.Type.DATA);
        tableRow(result, "Comision de rambursare anticipata", loan.getAnticipatedCommission(), TableRow.Type.DATA);

        tableRow(result, "SOLICITANTI ELIGIBILI", null, TableRow.Type.HEADER);
        tableRow(result, "Varsta minima", loan.getMinimumAge(), TableRow.Type.DATA);
        tableRow(result, "Grad maxim de indatorare", loan.getMaximumDebt(), TableRow.Type.DATA);

        tableRow(result, "CONDITII DE ACORDARE", null, TableRow.Type.HEADER);
        tableRow(result, "Perioada de gratie", loan.getFreePeriod(), TableRow.Type.DATA);
        tableRow(result, "Avans minim", loan.getMinimalInitialPayment(), TableRow.Type.DATA);
        tableRow(result, "Asigurari", loan.getInsurance(), TableRow.Type.DATA);
        tableRow(result, "Garantii", loan.getGuarantee(), TableRow.Type.DATA);

        //Remove las element if header
        if (result.size() > 0) {
            if (result.get(result.size() - 1).getType() == TableRow.Type.HEADER) {
                result.remove(result.size() - 1);
            }
        }
        return result;
    }

    private void tableRow(List<TableRow> result, String title, String description, int type) {
        if (type == TableRow.Type.DATA && description == null) {
            return;
        }

        //Check if we are adding 2 headers in a row.
        if (result.size() > 0) {
            if (result.get(result.size() - 1).getType() == TableRow.Type.HEADER && type == TableRow.Type.HEADER) {
                result.remove(result.size() - 1);
                return;
            }
        }
        result.add(new TableRow(title, description, type));
    }


}
