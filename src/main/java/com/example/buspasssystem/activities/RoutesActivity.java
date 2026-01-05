package com.example.buspasssystem.activities;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.buspasssystem.database.DBHelper;
import com.example.buspasssystem.R;
import java.util.ArrayList;

public class RoutesActivity extends AppCompatActivity {

    ListView routesLv;
    DBHelper db;
    int userId;
    ArrayList<String> routesList = new ArrayList<>();
    ArrayList<Integer> routeIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        userId = getIntent().getIntExtra("userId", -1);

        routesLv = findViewById(R.id.routesLv);
        db = new DBHelper(this);

        loadRoutes();

        routesLv.setOnItemClickListener((parent, view, position, id) -> {
            int routeId = routeIds.get(position);
            String routeInfo = routesList.get(position);

            Cursor userCursor = db.getUserById(userId);
            if(userCursor.moveToFirst()) {
                int balance = userCursor.getInt(userCursor.getColumnIndexOrThrow("balance"));
                Cursor routeCursor = db.getRoutes();
                if(routeCursor.moveToPosition(position)) {
                    int fare = routeCursor.getInt(routeCursor.getColumnIndexOrThrow("fare"));
                    int seats = routeCursor.getInt(routeCursor.getColumnIndexOrThrow("seats"));
                    if(seats <= 0) {
                        Toast.makeText(this, "No seats available", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(balance < fare) {
                        Toast.makeText(this, "Insufficient balance", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    new AlertDialog.Builder(this)
                            .setTitle("Confirm Booking")
                            .setMessage("Book this pass for " + routeInfo + "?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                db.insertPass(userId, routeInfo, fare);
                                db.updateBalance(userId, balance - fare);
                                db.updateSeats(routeId, seats - 1);
                                Toast.makeText(this, "Pass booked!", Toast.LENGTH_SHORT).show();
                                loadRoutes();
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            }
        });
    }

    private void loadRoutes() {
        routesList.clear();
        routeIds.clear();
        Cursor cursor = db.getRoutes();
        if(cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String from = cursor.getString(cursor.getColumnIndexOrThrow("from_city"));
                String to = cursor.getString(cursor.getColumnIndexOrThrow("to_city"));
                int fare = cursor.getInt(cursor.getColumnIndexOrThrow("fare"));
                int seats = cursor.getInt(cursor.getColumnIndexOrThrow("seats"));

                routesList.add(from + " → " + to + " | Fare: " + fare + " | Seats: " + seats);
                routeIds.add(id);
            } while(cursor.moveToNext());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, routesList);
        routesLv.setAdapter(adapter);
    }
}
