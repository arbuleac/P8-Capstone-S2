package com.arbuleac.loan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arbuleac.loan.R;
import com.arbuleac.loan.data.Loan;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @since 3/23/16.
 */
public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.LoanViewHolder> {

    private final LayoutInflater inflater;
    private final List<Loan> loans;
    private final int amount;
    private final int duration;
    private final LoanClickListener listener;

    public ResultsAdapter(Context context, List<Loan> loans, int amount, int duration, LoanClickListener listener) {
        inflater = LayoutInflater.from(context);
        this.loans = loans;
        this.amount = amount;
        this.duration = duration;
        this.listener = listener;

    }

    @Override
    public LoanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LoanViewHolder(inflater.inflate(R.layout.item_loan, parent, false), amount, duration, listener);
    }

    @Override
    public void onBindViewHolder(LoanViewHolder holder, int position) {
        holder.bind(loans.get(position));
    }

    @Override
    public int getItemCount() {
        return loans == null ? 0 : loans.size();
    }

    public interface LoanClickListener {
        void onLoanClicked(Loan loan);
    }

    public static class LoanViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_bankLogo)
        ImageView bankLogo;
        @Bind(R.id.tv_bankName)
        TextView bankName;
        @Bind(R.id.tv_dae)
        TextView dae;
        @Bind(R.id.tv_details)
        TextView details;

        int duration;
        int amount;
        Loan loan;

        public LoanViewHolder(View itemView, int amount, int duration, final LoanClickListener listener) {
            super(itemView);
            this.duration = duration;
            this.amount = amount;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onLoanClicked(loan);
                }
            });

        }

        public void bind(Loan loan) {
            Glide.with(itemView.getContext()).load(loan.getBank().getLogo()).into(bankLogo);
            bankName.setText(loan.getBank().getName());
            dae.setText(loan.getDae());
            double doubleDae = Double.parseDouble(loan.getDae().replace("%", "").replace(",", ".").trim());
            //Source: http://www.calcunation.com/calculator/mortgage-total-cost.php
            double finalAmount = doubleDae / 1200 * amount / (1 - Math.pow(1 + doubleDae / 1200, -duration * 12)) * duration * 12;
            details.setText(String.format(Locale.getDefault(), "Cost total: %d RON\nPrima rata: %d RON", (int) finalAmount, (int) finalAmount / (12 * duration)));
            this.loan = loan;
        }
    }
}
