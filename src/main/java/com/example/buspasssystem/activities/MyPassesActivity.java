package com.example.buspasssystem.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.buspasssystem.database.DBHelper;
import com.example.buspasssystem.R;
import java.util.ArrayList;

public class MyPassesActivity extends AppCompatActivity {

    ListView passesLv;
    DBHelper db;
    int userId;
    ArrayList<String> passesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passes);

        userId = getIntent().getIntExtra("userId", -1);

        passesLv = findViewById(R.id.passesLv);
        db = new DBHelper(this);

        loadPasses();
    }

    private void loadPasses() {
        passesList.clear();
        Cursor cursor = db.getUserPasses(userId);
        if(cursor.moveToFirst()) {
            do {
                String route = cursor.getString(cursor.getColumnIndexOrThrow("route"));
                int fare = cursor.getInt(cursor.getColumnIndexOrThrow("fare"));
                passesList.add(route + " | Fare: " + fare);
            } while(cursor.moveToNext());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, passesList);
        passesLv.setAdapter(adapter);
    }
}
