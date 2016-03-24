package com.arbuleac.loan.utils;

import android.content.Context;

import com.arbuleac.loan.R;
import com.arbuleac.loan.data.Bank;
import com.arbuleac.loan.data.Loan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @since 3/23/16.
 */
public class BankParser {

    public static List<Loan> parseLoans(Context context) throws IOException {
        InputStream is = context.getResources().openRawResource(R.raw.loans);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        List<Loan> result = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            for (int i = 0; i < values.length; i++) {
                values[i] = values[i].replaceAll("\"", "");
            }

            Loan loan = new Loan();
            Bank bank = new Bank();

            bank.setName(values[0]);
            loan.setName(values[1]);
            bank.setLogo(values[2]);
            bank.setContactAddress(values[3]);
            loan.setInitialCommission(values[4]);
            loan.setAnnualCommission(values[5]);
            loan.setInsuranceCost(values[6]);
            loan.setDae(values[7]);
            loan.setLoanInterestsType(values[8]);
            loan.setLoanInterestsDetails(values[9]);
            loan.setSpecialConditions(values[10]);
            loan.setAnticipatedCommission(values[11]);
            loan.setMinimumAge(values[12]);
            loan.setMaximumDebt(values[13]);
            loan.setFreePeriod(values[14]);
            loan.setMinimalInitialPayment(values[15]);
            loan.setInsurance(values[16]);
            loan.setGuarantee(values[17]);
            loan.setBank(bank);

            result.add(loan);
        }

        return result;
    }
}
