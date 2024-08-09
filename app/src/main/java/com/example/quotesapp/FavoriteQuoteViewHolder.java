package com.example.quotesapp;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteQuoteViewHolder extends RecyclerView.ViewHolder {
        public TextView quoteTextView;

        public FavoriteQuoteViewHolder(@NonNull View itemView) {
            super(itemView);
            quoteTextView = itemView.findViewById(R.id.quoteText);
        }
    }


