package com.example.petscarenew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ToDo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
    }

    public void Home3(View view) {
        Intent intent = new Intent(ToDo.this, MainActivity.class);
        ToDo.this.startActivity(intent);
    }
}