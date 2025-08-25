package com.example.tientuu;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {

    private LinearLayout layoutPlayerInputs;
    private MaterialButton btnStart;
    private ArrayList<TextInputEditText> playerInputs = new ArrayList<>();
    private int numPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        layoutPlayerInputs = findViewById(R.id.layoutPlayerInputs);
        btnStart = findViewById(R.id.btnStart);

        // Nhận số người chơi từ MainActivity
        numPlayers = getIntent().getIntExtra("numPlayers", 2);

        // Tạo input động
        for (int i = 1; i <= numPlayers; i++) {
            TextInputLayout til = new TextInputLayout(this, null, com.google.android.material.R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox);
            til.setHint("Tên người chơi " + i);

            TextInputEditText et = new TextInputEditText(this);
            et.setMaxLines(1);
            til.addView(et);

            layoutPlayerInputs.addView(til);
            playerInputs.add(et);
        }

        // Xử lý nút Bắt đầu
        btnStart.setOnClickListener(v -> startGame());
    }

    private void startGame() {
        ArrayList<String> playerNames = new ArrayList<>();

        for (int i = 0; i < playerInputs.size(); i++) {
            String name = playerInputs.get(i).getText() != null ? playerInputs.get(i).getText().toString().trim() : "";
            if (TextUtils.isEmpty(name)) {
                name = "Người chơi " + (i + 1); // default nếu bỏ trống
            }
            playerNames.add(name);
        }

        // Truyền danh sách tên sang GameActivity
        Intent intent = new Intent(StartActivity.this, GameActivity.class);
        intent.putStringArrayListExtra("PLAYER_NAMES", playerNames);
        startActivity(intent);
    }
}
