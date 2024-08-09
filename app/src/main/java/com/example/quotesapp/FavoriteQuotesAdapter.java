package com.example.quotesapp;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FavoriteQuotesAdapter extends RecyclerView.Adapter<FavoriteQuoteViewHolder> {

    private List<String> favoriteQuotes;

    public FavoriteQuotesAdapter(List<String> favoriteQuotes) {
        this.favoriteQuotes = favoriteQuotes;
    }

    @NonNull
    @Override
    public FavoriteQuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_layout, parent, false);
        return new FavoriteQuoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteQuoteViewHolder holder, int position) {
        String quote = favoriteQuotes.get(position);
        holder.quoteTextView.setText(quote);
    }

    @Override
    public int getItemCount() {
        return favoriteQuotes.size();
    }
}

