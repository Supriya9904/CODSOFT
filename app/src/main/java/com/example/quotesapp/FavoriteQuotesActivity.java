package com.example.quotesapp;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class FavoriteQuotesActivity extends AppCompatActivity {

    private RecyclerView favoritesRecyclerView;
    private List<String> favoritesList;
    private FavoriteQuotesAdapter adapter;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_quotes);


        favoritesRecyclerView = findViewById(R.id.recyclerViewQ);
        backButton = findViewById(R.id.btnBack);


        favoritesList = new ArrayList<>();
        adapter = new FavoriteQuotesAdapter(favoritesList);


        favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        favoritesRecyclerView.setAdapter(adapter);


        loadFavorites();


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadFavorites() {
        DatabaseReference favRef = FirebaseDatabase.getInstance().getReference("fav");
        favRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                favoritesList.clear();
                for (DataSnapshot favSnapshot : snapshot.getChildren()) {
                    String quote = favSnapshot.getValue(String.class);
                    favoritesList.add(quote);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
