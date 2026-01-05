package com.example.buspasssystem.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.buspasssystem.database.DBHelper;
import com.example.buspasssystem.R;

public class RegisterActivity extends AppCompatActivity {

    EditText emailEt, passwordEt;
    Button registerBtn;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        registerBtn = findViewById(R.id.registerBtn);

        db = new DBHelper(this);

        registerBtn.setOnClickListener(v -> {
            String email = emailEt.getText().toString();
            String pass = passwordEt.getText().toString();

            if(db.insertUser(email, pass)) {
                Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
