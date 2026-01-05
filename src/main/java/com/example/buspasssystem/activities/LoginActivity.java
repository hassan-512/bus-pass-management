package com.example.buspasssystem.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.buspasssystem.database.DBHelper;
import com.example.buspasssystem.R;

public class LoginActivity extends AppCompatActivity {

    EditText emailEt, passwordEt;
    Button loginBtn, registerBtn;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);

        db = new DBHelper(this);

        loginBtn.setOnClickListener(v -> {
            String email = emailEt.getText().toString();
            String pass = passwordEt.getText().toString();

            Cursor cursor = db.getUser(email, pass);
            if(cursor.moveToFirst()) {
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                Intent i = new Intent(this, DashboardActivity.class);
                i.putExtra("userId", userId);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        registerBtn.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
    }
}
