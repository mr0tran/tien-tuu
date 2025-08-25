package com.example.tientuu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class ResultActivity extends AppCompatActivity {

    private TextView tvPlayer1Name, tvPlayer2Name;
    private TextView tvPlayer1Drinks, tvPlayer2Drinks;
    private TextView tvWinner, tvTotalRounds, tvTotalDrinks;
    private MaterialButton btnPlayAgain, btnNewGame;

    private String player1Name, player2Name;
    private int player1Drinks, player2Drinks, totalRounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Get data from intent
        getDataFromIntent();

        // Initialize views
        initializeViews();

        // Update display
        updateDisplay();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        player1Name = intent.getStringExtra("player1_name");
        player2Name = intent.getStringExtra("player2_name");
        player1Drinks = intent.getIntExtra("player1_drinks", 0);
        player2Drinks = intent.getIntExtra("player2_drinks", 0);
        totalRounds = intent.getIntExtra("total_rounds", 0);
    }

    private void initializeViews() {
        tvPlayer1Name = findViewById(R.id.tvPlayer1Name);
        tvPlayer2Name = findViewById(R.id.tvPlayer2Name);
        tvPlayer1Drinks = findViewById(R.id.tvPlayer1Drinks);
        tvPlayer2Drinks = findViewById(R.id.tvPlayer2Drinks);
        tvWinner = findViewById(R.id.tvWinner);
        tvTotalRounds = findViewById(R.id.tvTotalRounds);
        tvTotalDrinks = findViewById(R.id.tvTotalDrinks);
        btnPlayAgain = findViewById(R.id.btnPlayAgain);
        btnNewGame = findViewById(R.id.btnNewGame);

        // Set click listeners
        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
            }
        });

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
            }
        });
    }

    private void updateDisplay() {
        // Set player names
        tvPlayer1Name.setText(player1Name);
        tvPlayer2Name.setText(player2Name);

        // Set drink counts
        tvPlayer1Drinks.setText(String.valueOf(player1Drinks));
        tvPlayer2Drinks.setText(String.valueOf(player2Drinks));

        // Determine winner
        String winner;
        if (player1Drinks < player2Drinks) {
            winner = player1Name + " thắng!";
        } else if (player2Drinks < player1Drinks) {
            winner = player2Name + " thắng!";
        } else {
            winner = "Hòa!";
        }
        tvWinner.setText(winner);

        // Set statistics
        tvTotalRounds.setText(String.valueOf(totalRounds));
        tvTotalDrinks.setText(String.valueOf(player1Drinks + player2Drinks));
    }

    private void playAgain() {
        // Start new game with same players
        Intent intent = new Intent(ResultActivity.this, GameActivity.class);
        intent.putExtra("player1_name", player1Name);
        intent.putExtra("player2_name", player2Name);
        startActivity(intent);
        finish();
    }

    private void newGame() {
        // Go back to start screen
        Intent intent = new Intent(ResultActivity.this, StartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
