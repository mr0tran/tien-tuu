package com.example.tientuu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private LinearLayout headerLayout;
    private TextView tvCurrentPlayer, tvCardSuit, tvCardValue, tvTask;
    private MaterialButton btnDrawCard, btnEndGame;
    private CardView cardView;

    private List<String> playerNames;
    private List<TextView> playerTextViews = new ArrayList<>();
    private List<Integer> playerDrinks = new ArrayList<>();
    private int currentPlayerIndex = 0;
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

        // Lấy danh sách người chơi từ Intent
        playerNames = getIntent().getStringArrayListExtra("PLAYER_NAMES");
        if (playerNames == null || playerNames.isEmpty()) {
            playerNames = new ArrayList<>();
            playerNames.add("Người chơi 1");
        }

        // Khởi tạo số ly cho từng người
        for (int i = 0; i < playerNames.size(); i++) {
            playerDrinks.add(0);
        }

        initializeViews();
        setupPlayerHeader();
        updatePlayerDisplay();
    }

    private void initializeViews() {
        headerLayout = findViewById(R.id.headerLayout);
        tvCurrentPlayer = findViewById(R.id.tvCurrentPlayer);
        tvCardSuit = findViewById(R.id.tvCardSuit);
        tvCardValue = findViewById(R.id.tvCardValue);
        tvTask = findViewById(R.id.tvTask);
        btnDrawCard = findViewById(R.id.btnDrawCard);
        btnEndGame = findViewById(R.id.btnEndGame);
        cardView = findViewById(R.id.cardView);

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

    private void setupPlayerHeader() {
        headerLayout.removeAllViews();
        playerTextViews.clear();

        for (String name : playerNames) {
            TextView tv = new TextView(this);
            tv.setText(name + " (0 ly)");
            tv.setPadding(16, 16, 16, 16);
            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundResource(R.drawable.player_background);
            tv.setTextColor(Color.BLACK);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            tv.setLayoutParams(params);

            headerLayout.addView(tv);
            playerTextViews.add(tv);
        }
    }

    private void drawCard() {
        String suit = suits[random.nextInt(suits.length)];
        String value = values[random.nextInt(values.length)];
        String task = tasks[random.nextInt(tasks.length)];

        tvCardSuit.setText(suit);
        tvCardValue.setText(value);
        tvTask.setText(task);

        // Set card color
        if (suit.equals("♥") || suit.equals("♦")) {
            tvCardSuit.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            tvCardValue.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        } else {
            tvCardSuit.setTextColor(getResources().getColor(android.R.color.black));
            tvCardValue.setTextColor(getResources().getColor(android.R.color.black));
        }

        cardView.setVisibility(View.VISIBLE);

        // Lưu index người chơi hiện tại
        int activePlayer = currentPlayerIndex;

        // Cập nhật số ly dựa trên task
        updateDrinkCount(task, activePlayer);

        // Cập nhật header hiển thị số ly
        updatePlayerHeader();

        // Chuyển lượt
        currentPlayerIndex = (currentPlayerIndex + 1) % playerNames.size();
        updatePlayerDisplay();

        totalRounds++;
    }

    private void updateDrinkCount(String task, int activePlayer) {
        if (task.contains("Uống 1 ly")) {
            playerDrinks.set(activePlayer, playerDrinks.get(activePlayer) + 1);
        } else if (task.contains("Uống 2 ly")) {
            playerDrinks.set(activePlayer, playerDrinks.get(activePlayer) + 2);
        } else if (task.contains("Uống 3 ly")) {
            playerDrinks.set(activePlayer, playerDrinks.get(activePlayer) + 3);
        } else if (task.contains("Uống 4 ly")) {
            playerDrinks.set(activePlayer, playerDrinks.get(activePlayer) + 4);
        } else if (task.contains("Uống 5 ly")) {
            playerDrinks.set(activePlayer, playerDrinks.get(activePlayer) + 5);
        } else if (task.contains("Người chơi khác uống 1 ly")) {
            for (int i = 0; i < playerDrinks.size(); i++) {
                if (i != activePlayer)
                    playerDrinks.set(i, playerDrinks.get(i) + 1);
            }
        } else if (task.contains("Người chơi khác uống 2 ly")) {
            for (int i = 0; i < playerDrinks.size(); i++) {
                if (i != activePlayer)
                    playerDrinks.set(i, playerDrinks.get(i) + 2);
            }
        } else if (task.contains("Tất cả cùng uống")) {
            int num = 1;
            String number = task.replaceAll("[^0-9]", "");
            if (!number.isEmpty()) num = Integer.parseInt(number);
            for (int i = 0; i < playerDrinks.size(); i++) {
                playerDrinks.set(i, playerDrinks.get(i) + num);
            }
        } else if (task.contains("Uống 1 ly và đặt tên")) {
            for (int i = 0; i < playerDrinks.size(); i++) {
                playerDrinks.set(i, playerDrinks.get(i) + 1);
            }
        }
    }

    private void updatePlayerHeader() {
        for (int i = 0; i < playerTextViews.size(); i++) {
            playerTextViews.get(i).setText(playerNames.get(i) + " (" + playerDrinks.get(i) + " ly)");
        }
    }

    private void updatePlayerDisplay() {
        tvCurrentPlayer.setText("Lượt của: " + playerNames.get(currentPlayerIndex));
        for (int i = 0; i < playerTextViews.size(); i++) {
            if (i == currentPlayerIndex) {
                playerTextViews.get(i).setBackgroundResource(R.drawable.player_background);
            } else {
                playerTextViews.get(i).setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    private void endGame() {
        Intent intent = new Intent(GameActivity.this, ResultActivity.class);
        intent.putStringArrayListExtra("player_names", new ArrayList<>(playerNames));
        intent.putIntegerArrayListExtra("player_drinks", new ArrayList<>(playerDrinks));
        intent.putExtra("total_rounds", totalRounds);
        startActivity(intent);
        finish();
    }
}
