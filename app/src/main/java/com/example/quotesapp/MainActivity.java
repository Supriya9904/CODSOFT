package com.example.quotesapp;
import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView quotes;
    RelativeLayout linearlayout;
    ImageButton favoriteIcon, shareIcon;
    View divider;
    DatabaseReference databaseReference;
    List<String> quoteList = new ArrayList<>();

    String currentQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        quotes = findViewById(R.id.quotes);
        linearlayout = findViewById(R.id.linearlayout);
        favoriteIcon = findViewById(R.id.favoriteIcon);
        shareIcon = findViewById(R.id.shareIcon);
        divider = findViewById(R.id.divider);

        databaseReference = FirebaseDatabase.getInstance().getReference("quotes");


        fetchQuotes();

        favoriteIcon.setOnClickListener(v -> addQuoteToFavorites(currentQuote));
        shareIcon.setOnClickListener(v -> share(currentQuote));


        findViewById(R.id.favoriteQuotesButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavoriteQuotesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fetchQuotes() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                quoteList.clear();
                    for (DataSnapshot quoteSnapshot : dataSnapshot.getChildren()) {
                        String quote = quoteSnapshot.getValue(String.class);
                        quoteList.add(quote);
                    }
                    displayRandomQuote();
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to fetch quotes: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayRandomQuote() {
        if (!quoteList.isEmpty()) {
            Random random = new Random();
            currentQuote = quoteList.get(random.nextInt(quoteList.size()));
            quotes.setText(currentQuote);
        }
    }

    public void expand(View view) {
        int v = (quotes.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;
        int btnVisibility = (favoriteIcon.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;

        TransitionManager.beginDelayedTransition(linearlayout, new AutoTransition());
        quotes.setVisibility(v);
        favoriteIcon.setVisibility(btnVisibility);
        divider.setVisibility(v);
        shareIcon.setVisibility(btnVisibility);


        if (v == View.VISIBLE) {
            displayRandomQuote();
        }
    }

    private void addQuoteToFavorites(String q) {
       DatabaseReference ref = FirebaseDatabase.getInstance().getReference("fav");
       ref.push().setValue(q);
        Toast.makeText(this, "Add to fav", Toast.LENGTH_SHORT).show();
    }
    private void share(String q){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, q);
        startActivity(Intent.createChooser(intent, "Share via"));
    }

}



