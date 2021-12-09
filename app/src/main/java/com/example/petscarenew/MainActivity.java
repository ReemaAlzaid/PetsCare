package com.example.petscarenew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void PetsList(View view) {
        Intent intent = new Intent(MainActivity.this, MainActivityPetsList.class);
        MainActivity.this.startActivity(intent);
    }
    public void TodoBtn(View view) {
        Intent intent = new Intent(MainActivity.this, ToDo.class);
        MainActivity.this.startActivity(intent);
    }

}