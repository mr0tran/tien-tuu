package com.example.tientuu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.button.MaterialButton;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView tvPlayer1Name, tvPlayer2Name, tvCurrentPlayer;
    private TextView tvCardSuit, tvCardValue, tvTask;
    private MaterialButton btnDrawCard, btnEndGame;
    private CardView cardView;

    private String player1Name, player2Name;
    private int currentPlayer = 1; // 1 for player 1, 2 for player 2
    private int player1Drinks = 0, player2Drinks = 0;
    private int totalRounds = 0;

    // Card data
    private String[] suits = {"♠", "♥", "♦", "♣"};
    private String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private String[] tasks = {
        "Uống 1 ly", "Uống 2 ly", "Uống 3 ly", "Uống 4 ly", "Uống 5 ly",
        "Người chơi khác uống 1 ly", "Người chơi khác uống 2 ly",
        "Cả hai cùng uống 1 ly", "Cả hai cùng uống 2 ly",
        "Hát một bài hát", "Nhảy một điệu nhảy", "Kể một câu chuyện cười",
        "Làm theo yêu cầu của người chơi khác", "Tự do (không uống)",
        "Uống 1 ly và đặt tên cho người chơi khác uống 1 ly"
    };

    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Get player names from intent
        player1Name = getIntent().getStringExtra("player1_name");
        player2Name = getIntent().getStringExtra("player2_name");

        // Initialize views
        initializeViews();
        updatePlayerDisplay();
    }

    private void initializeViews() {
        tvPlayer1Name = findViewById(R.id.tvPlayer1Name);
        tvPlayer2Name = findViewById(R.id.tvPlayer2Name);
        tvCurrentPlayer = findViewById(R.id.tvCurrentPlayer);
        tvCardSuit = findViewById(R.id.tvCardSuit);
        tvCardValue = findViewById(R.id.tvCardValue);
        tvTask = findViewById(R.id.tvTask);
        btnDrawCard = findViewById(R.id.btnDrawCard);
        btnEndGame = findViewById(R.id.btnEndGame);
        cardView = findViewById(R.id.cardView);

        // Set player names
        tvPlayer1Name.setText(player1Name);
        tvPlayer2Name.setText(player2Name);

        // Set click listeners
        btnDrawCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawCard();
            }
        });

        btnEndGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endGame();
            }
        });
    }

    private void drawCard() {
        // Generate random card
        String suit = suits[random.nextInt(suits.length)];
        String value = values[random.nextInt(values.length)];
        String task = tasks[random.nextInt(tasks.length)];

        // Update card display
        tvCardSuit.setText(suit);
        tvCardValue.setText(value);
        tvTask.setText(task);

        // Set card color based on suit
        if (suit.equals("♥") || suit.equals("♦")) {
            tvCardSuit.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            tvCardValue.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        } else {
            tvCardSuit.setTextColor(getResources().getColor(android.R.color.black));
            tvCardValue.setTextColor(getResources().getColor(android.R.color.black));
        }

        // Show card
        cardView.setVisibility(View.VISIBLE);

        // Update drink count based on task
        updateDrinkCount(task);

        // Switch player
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
        updatePlayerDisplay();

        totalRounds++;
    }

    private void updateDrinkCount(String task) {
        if (task.contains("Uống 1 ly")) {
            if (currentPlayer == 1) player1Drinks++;
            else player2Drinks++;
        } else if (task.contains("Uống 2 ly")) {
            if (currentPlayer == 1) player1Drinks += 2;
            else player2Drinks += 2;
        } else if (task.contains("Uống 3 ly")) {
            if (currentPlayer == 1) player1Drinks += 3;
            else player2Drinks += 3;
        } else if (task.contains("Uống 4 ly")) {
            if (currentPlayer == 1) player1Drinks += 4;
            else player2Drinks += 4;
        } else if (task.contains("Uống 5 ly")) {
            if (currentPlayer == 1) player1Drinks += 5;
            else player2Drinks += 5;
        } else if (task.contains("Người chơi khác uống 1 ly")) {
            if (currentPlayer == 1) player2Drinks++;
            else player1Drinks++;
        } else if (task.contains("Người chơi khác uống 2 ly")) {
            if (currentPlayer == 1) player2Drinks += 2;
            else player1Drinks += 2;
        } else if (task.contains("Cả hai cùng uống 1 ly")) {
            player1Drinks++;
            player2Drinks++;
        } else if (task.contains("Cả hai cùng uống 2 ly")) {
            player1Drinks += 2;
            player2Drinks += 2;
        } else if (task.contains("Uống 1 ly và đặt tên")) {
            if (currentPlayer == 1) {
                player1Drinks++;
                player2Drinks++;
            } else {
                player2Drinks++;
                player1Drinks++;
            }
        }
    }

    private void updatePlayerDisplay() {
        String currentPlayerName = (currentPlayer == 1) ? player1Name : player2Name;
        tvCurrentPlayer.setText("Lượt của: " + currentPlayerName);

        // Update player name colors
        if (currentPlayer == 1) {
            tvPlayer1Name.setBackgroundResource(R.drawable.player_background);
            tvPlayer2Name.setBackgroundResource(0);
        } else {
            tvPlayer2Name.setBackgroundResource(R.drawable.player_background);
            tvPlayer1Name.setBackgroundResource(0);
        }
    }

    private void endGame() {
        Intent intent = new Intent(GameActivity.this, ResultActivity.class);
        intent.putExtra("player1_name", player1Name);
        intent.putExtra("player2_name", player2Name);
        intent.putExtra("player1_drinks", player1Drinks);
        intent.putExtra("player2_drinks", player2Drinks);
        intent.putExtra("total_rounds", totalRounds);
        startActivity(intent);
        finish();
    }
}
