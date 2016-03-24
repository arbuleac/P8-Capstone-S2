package com.arbuleac.loan.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arbuleac.loan.R;
import com.arbuleac.loan.data.TableRow;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @since 3/23/16.
 */
public class LoanDetailsAdapter extends RecyclerView.Adapter<LoanDetailsAdapter.TableRowHolder> {

    private final List<TableRow> tableRows;
    private final LayoutInflater inflater;

    public LoanDetailsAdapter(Context context, List<TableRow> tableRows) {
        this.inflater = LayoutInflater.from(context);
        this.tableRows = tableRows;
    }

    @Override
    public TableRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TableRow.Type.DATA:
                return new DataRowHolder(inflater.inflate(R.layout.item_tablerow_data, parent, false));
            case TableRow.Type.HEADER:
                return new HeaderRowHolder(inflater.inflate(R.layout.item_tablerow_header, parent, false));
            default:
                throw new IllegalArgumentException("Type can not be " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(TableRowHolder holder, int position) {
        holder.bind(tableRows.get(position), position);
    }

    @Override
    public int getItemCount() {
        return tableRows == null ? 0 : tableRows.size();
    }

    @Override
    public int getItemViewType(int position) {
        return tableRows.get(position).getType();
    }

    public static abstract class TableRowHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_title)
        TextView tvTitle;

        public TableRowHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public abstract void bind(TableRow tableRow, int position);
    }

    public static class HeaderRowHolder extends TableRowHolder {

        public HeaderRowHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bind(TableRow tableRow, int position) {
            tvTitle.setText(tableRow.getTitle());
        }
    }

    public static class DataRowHolder extends TableRowHolder {

        @Bind(R.id.tv_description)
        TextView tvDescription;

        public DataRowHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bind(TableRow tableRow, int position) {
            tvTitle.setText(tableRow.getTitle());
            tvDescription.setText(tableRow.getDescription());
            itemView.setBackgroundColor(position % 2 == 1 ? Color.parseColor("#F5F6F6") : Color.WHITE);
        }
    }
}
