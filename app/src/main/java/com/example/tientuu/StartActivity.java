package com.example.tientuu;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class StartActivity extends AppCompatActivity {

    private TextInputEditText etPlayer1, etPlayer2;
    private MaterialButton btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Initialize views
        etPlayer1 = findViewById(R.id.etPlayer1);
        etPlayer2 = findViewById(R.id.etPlayer2);
        btnStart = findViewById(R.id.btnStart);

        // Set click listener for start button
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
    }

    private void startGame() {
        String player1Name = etPlayer1.getText().toString().trim();
        String player2Name = etPlayer2.getText().toString().trim();

        // Validate input
        if (TextUtils.isEmpty(player1Name)) {
            etPlayer1.setError("Vui lòng nhập tên người chơi 1");
            return;
        }

        if (TextUtils.isEmpty(player2Name)) {
            etPlayer2.setError("Vui lòng nhập tên người chơi 2");
            return;
        }

        if (player1Name.equals(player2Name)) {
            Toast.makeText(this, "Tên người chơi không được trùng nhau", Toast.LENGTH_SHORT).show();
            return;
        }

        // Start game activity with player names
        Intent intent = new Intent(StartActivity.this, GameActivity.class);
        intent.putExtra("player1_name", player1Name);
        intent.putExtra("player2_name", player2Name);
        startActivity(intent);
        finish(); // Close this activity
    }
}
