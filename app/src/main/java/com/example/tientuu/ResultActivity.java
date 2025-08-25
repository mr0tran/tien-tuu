package com.example.tientuu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private LinearLayout layoutPlayersDynamic; // container cho danh sách người chơi
    private CardView cardPlayer1, cardPlayer2; // 2 card cứng, sẽ ẩn nếu >2 người
    private TextView tvWinner, tvTotalRounds, tvTotalDrinks;
    private MaterialButton btnPlayAgain, btnNewGame;

    private ArrayList<String> playerNames;
    private ArrayList<Integer> playerDrinks;
    private int totalRounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getDataFromIntent();
        initializeViews();
        updateDisplay();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        playerNames = intent.getStringArrayListExtra("player_names");
        playerDrinks = intent.getIntegerArrayListExtra("player_drinks");
        totalRounds = intent.getIntExtra("total_rounds", 0);

        if (playerNames == null) playerNames = new ArrayList<>();
        if (playerDrinks == null) playerDrinks = new ArrayList<>();
    }

    private void initializeViews() {
        cardPlayer1 = findViewById(R.id.cardPlayer1);
        cardPlayer2 = findViewById(R.id.cardPlayer2);

        // Tạo container động cho nhiều người
        layoutPlayersDynamic = new LinearLayout(this);
        layoutPlayersDynamic.setOrientation(LinearLayout.VERTICAL);
        layoutPlayersDynamic.setGravity(Gravity.CENTER);
        layoutPlayersDynamic.setPadding(16, 16, 16, 16);

        // Thêm vào layout chính trên cardWinner
        LinearLayout parentLayout = findViewById(R.id.cardStats).getParent() instanceof LinearLayout ?
                (LinearLayout) findViewById(R.id.cardStats).getParent() : null;
        if (parentLayout != null) {
            parentLayout.addView(layoutPlayersDynamic, 0); // thêm trước cardWinner
        }

        tvWinner = findViewById(R.id.tvWinner);
        tvTotalRounds = findViewById(R.id.tvTotalRounds);
        tvTotalDrinks = findViewById(R.id.tvTotalDrinks);
        btnPlayAgain = findViewById(R.id.btnPlayAgain);
        btnNewGame = findViewById(R.id.btnNewGame);

        btnPlayAgain.setOnClickListener(v -> playAgain());
        btnNewGame.setOnClickListener(v -> newGame());
    }

    private void updateDisplay() {
        if (playerNames.size() > 2) {
            // Ẩn 2 card cứng
            cardPlayer1.setVisibility(CardView.GONE);
            cardPlayer2.setVisibility(CardView.GONE);

            layoutPlayersDynamic.removeAllViews();

            int minDrinks = Integer.MAX_VALUE;
            ArrayList<String> winners = new ArrayList<>();
            int totalDrinks = 0;

            for (int i = 0; i < playerNames.size(); i++) {
                String name = playerNames.get(i);
                int drinks = playerDrinks.get(i);
                totalDrinks += drinks;

                if (drinks < minDrinks) {
                    minDrinks = drinks;
                    winners.clear();
                    winners.add(name);
                } else if (drinks == minDrinks) {
                    winners.add(name);
                }

                TextView tv = new TextView(this);
                tv.setText(name + ": " + drinks + " lượt uống");
                tv.setTextSize(18f);
                tv.setGravity(Gravity.CENTER);
                tv.setPadding(0, 8, 0, 8);
                layoutPlayersDynamic.addView(tv);
            }

            // Xác định người thắng
            if (winners.size() == 1) {
                tvWinner.setText("Người thắng: " + winners.get(0));
            } else {
                tvWinner.setText("Hòa: " + String.join(", ", winners));
            }

            LinearLayout layoutPlayersStats = findViewById(R.id.layoutPlayersStats);
            layoutPlayersStats.removeAllViews();

            for (int i = 0; i < playerNames.size(); i++) {
                String name = playerNames.get(i);
                int drinks = playerDrinks.get(i);

                TextView tv = new TextView(this);
                tv.setText(name + ": " + drinks + " lượt uống");
                tv.setTextSize(18f);
                tv.setGravity(Gravity.CENTER);
                tv.setPadding(0, 4, 0, 4);

                layoutPlayersStats.addView(tv);
            }

            tvTotalDrinks.setText(String.valueOf(totalDrinks));

        } else {
            // 2 người vẫn hiển thị card cứng
            cardPlayer1.setVisibility(CardView.VISIBLE);
            cardPlayer2.setVisibility(CardView.VISIBLE);

            ((TextView) findViewById(R.id.tvPlayer1Name)).setText(playerNames.get(0));
            ((TextView) findViewById(R.id.tvPlayer2Name)).setText(playerNames.get(1));
            ((TextView) findViewById(R.id.tvPlayer1Drinks)).setText(String.valueOf(playerDrinks.get(0)));
            ((TextView) findViewById(R.id.tvPlayer2Drinks)).setText(String.valueOf(playerDrinks.get(1)));

            int totalDrinks = playerDrinks.get(0) + playerDrinks.get(1);
            tvTotalDrinks.setText(String.valueOf(totalDrinks));

            // Xác định người thắng
            if (playerDrinks.get(0) < playerDrinks.get(1)) {
                tvWinner.setText("Người thắng: " + playerNames.get(0));
            } else if (playerDrinks.get(1) < playerDrinks.get(0)) {
                tvWinner.setText("Người thắng: " + playerNames.get(1));
            } else {
                tvWinner.setText("Hòa!");
            }

            LinearLayout layoutPlayersStats = findViewById(R.id.layoutPlayersStats);
            layoutPlayersStats.removeAllViews();

            for (int i = 0; i < playerNames.size(); i++) {
                String name = playerNames.get(i);
                int drinks = playerDrinks.get(i);

                TextView tv = new TextView(this);
                tv.setText(name + ": " + drinks + " lượt uống");
                tv.setTextSize(18f);
                tv.setGravity(Gravity.CENTER);
                tv.setPadding(0, 4, 0, 4);

                layoutPlayersStats.addView(tv);
            }


        }

        tvTotalRounds.setText(String.valueOf(totalRounds));
    }

    private void playAgain() {
        Intent intent = new Intent(ResultActivity.this, GameActivity.class);
        intent.putStringArrayListExtra("player_names", playerNames);
        startActivity(intent);
        finish();
    }

    private void newGame() {
        Intent intent = new Intent(ResultActivity.this, StartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
