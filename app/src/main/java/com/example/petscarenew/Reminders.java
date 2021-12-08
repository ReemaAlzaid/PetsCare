package com.example.petscarenew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Reminders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
    }

    public void Home2(View view) {
        Intent intent = new Intent(Reminders.this, MainActivity.class);
        Reminders.this.startActivity(intent);
    }
}