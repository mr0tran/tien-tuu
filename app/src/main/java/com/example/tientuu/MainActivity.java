package com.example.tientuu;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText edtPlayerCount;
    Button btnConfirmCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtPlayerCount = findViewById(R.id.edtPlayerCount);
        btnConfirmCount = findViewById(R.id.btnConfirmCount);

        btnConfirmCount.setOnClickListener(v -> {
            String countStr = edtPlayerCount.getText().toString().trim();

            if (TextUtils.isEmpty(countStr)) {
                Toast.makeText(this, "Vui lòng nhập số người chơi", Toast.LENGTH_SHORT).show();
                return;
            }

            int count = Integer.parseInt(countStr);
            if (count < 2) {
                Toast.makeText(this, "Cần ít nhất 2 người chơi", Toast.LENGTH_SHORT).show();
                return;
            }

            // Chuyển sang StartActivity và truyền số người chơi
            Intent intent = new Intent(MainActivity.this, StartActivity.class);
            intent.putExtra("numPlayers", count);
            startActivity(intent);
        });
    }
}
