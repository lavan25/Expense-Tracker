package com.example.sqlite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {
    private List<Expense> expenseList;

    public ExpenseAdapter(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    public void setExpenseList(List<Expense> newExpenseList) {
        this.expenseList = newExpenseList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item_layout, parent, false);
        return new ExpenseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = expenseList.get(position);
        holder.bindData(expense);
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    class ExpenseViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryTextView;
        private TextView dateTextView;
        private TextView timeTextView;
        private TextView amountTextView;
        private TextView descriptionTextView;
        private TextView ratingTextView;

        public ExpenseViewHolder(@NonNull final View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            amountTextView = itemView.findViewById(R.id.amountTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
        }

        public void bindData(final Expense expense) {
            categoryTextView.setText("Category: " + expense.getCategory());
            dateTextView.setText("Date: " + expense.getDate());
            timeTextView.setText("Time: " + expense.getTime());
            amountTextView.setText("Amount: " + expense.getAmount());
            descriptionTextView.setText("Description: " + expense.getDescription());
            ratingTextView.setText("Rating: " + String.valueOf(expense.getRating()));

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showOptionsDialog(expense.getId());
                    return true;
                }
            });
        }

        private void showOptionsDialog(final int expenseId) {
            CharSequence[] options = new CharSequence[]{"Edit", "Delete"};

            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            builder.setTitle("Choose option");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {  // Edit
                        Intent intent = new Intent(itemView.getContext(), editrecord.class);
                        intent.putExtra("expenseId", expenseId);
                        itemView.getContext().startActivity(intent);
                    } else {
                        // Delete
                        DatabaseHelper databaseHelper = new DatabaseHelper(itemView.getContext());
                        databaseHelper.deleteExpense(expenseId);
                        List<Expense> newExpenseList = databaseHelper.getAllExpenses();
                        setExpenseList(newExpenseList);
                    }
                }
            });
            builder.show();
        }
    }
}
