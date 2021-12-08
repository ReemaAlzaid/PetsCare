package com.example.petscarenew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivityPetsList extends AppCompatActivity {
    DBPets myDP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pets_list);
        ViewAll();
    }

    public void ViewAll(){
        /*
        Cursor res = myDP.getAllData();
        if(res.getCount()==0){
            // show message
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            res.getString(1)+"\n");
            res.getString(2)+"\n");
            res.getString(3)+"\n\n";
        }

         */
    }


    public void insertPet(View view) {
        Intent intent = new Intent(MainActivityPetsList.this, AddPet.class);
        MainActivityPetsList.this.startActivity(intent);
    }

    public void Home1(View view) {
        Intent intent = new Intent(MainActivityPetsList.this, MainActivity.class);
        MainActivityPetsList.this.startActivity(intent);
    }
}