package com.example.buspasssystem.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.buspasssystem.database.DBHelper;
import com.example.buspasssystem.R;

public class DepositActivity extends AppCompatActivity {

    EditText amountEt;
    Button depositBtn;
    DBHelper db;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        userId = getIntent().getIntExtra("userId", -1);

        amountEt = findViewById(R.id.amountEt);
        depositBtn = findViewById(R.id.depositBtn);

        db = new DBHelper(this);

        depositBtn.setOnClickListener(v -> {
            String amtStr = amountEt.getText().toString();
            if(amtStr.isEmpty()) {
                Toast.makeText(this, "Enter amount", Toast.LENGTH_SHORT).show();
                return;
            }
            int amount = Integer.parseInt(amtStr);
            Cursor cursor = db.getUserById(userId);
            if(cursor.moveToFirst()) {
                int balance = cursor.getInt(cursor.getColumnIndexOrThrow("balance"));
                int newBalance = balance + amount;
                if(db.updateBalance(userId, newBalance)) {
                    Toast.makeText(this, "Deposit successful", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
