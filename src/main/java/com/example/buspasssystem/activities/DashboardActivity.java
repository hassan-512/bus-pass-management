package com.example.buspasssystem.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.buspasssystem.database.DBHelper;
import com.example.buspasssystem.R;

public class DashboardActivity extends AppCompatActivity {

    TextView welcomeTv, balanceTv;
    Button depositBtn, routesBtn, myPassesBtn;
    DBHelper db;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        userId = getIntent().getIntExtra("userId", -1);

        welcomeTv = findViewById(R.id.welcomeTv);
        balanceTv = findViewById(R.id.balanceTv);
        depositBtn = findViewById(R.id.depositBtn);
        routesBtn = findViewById(R.id.routesBtn);
        myPassesBtn = findViewById(R.id.myPassesBtn);

        db = new DBHelper(this);

        loadUserData();

        depositBtn.setOnClickListener(v -> {
            Intent i = new Intent(this, DepositActivity.class);
            i.putExtra("userId", userId);
            startActivity(i);
        });

        routesBtn.setOnClickListener(v -> {
            Intent i = new Intent(this, RoutesActivity.class);
            i.putExtra("userId", userId);
            startActivity(i);
        });

        myPassesBtn.setOnClickListener(v -> {
            Intent i = new Intent(this, MyPassesActivity.class);
            i.putExtra("userId", userId);
            startActivity(i);
        });
    }

    private void loadUserData() {
        Cursor cursor = db.getUserById(userId);
        if(cursor.moveToFirst()) {
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            int balance = cursor.getInt(cursor.getColumnIndexOrThrow("balance"));

            welcomeTv.setText("Welcome, " + email);
            balanceTv.setText("Balance: " + balance);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserData();
    }
}
