package com.example.petscarenew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPet extends AppCompatActivity {
    DBPets myDP;
    Button btnAddData;
    EditText editName,editAge,editDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);
        myDP=new DBPets(this);

        editName=(EditText)findViewById(R.id.addname) ;
        editAge=(EditText)findViewById(R.id.addage) ;
        editDescription=(EditText)findViewById(R.id.insertdes) ;

        btnAddData=(Button)findViewById(R.id.addbutton);
    }

    public void onClick(View view) {
        int counter=0;
        if(editName.getText().length()<=15 && editName.getText().length()>0)
            counter++;
        else
            Toast.makeText(AddPet.this,"Name must be less than or equal to 15",Toast.LENGTH_LONG).show();

        if(editAge.getText().length()<=2 && editAge.getText().length()>0)
            counter++;
        else
            Toast.makeText(AddPet.this,"Age must be 2 or 1 digit only",Toast.LENGTH_LONG).show();

        if(editDescription.getText().length()<=30 && editAge.getText().length()>0)
            counter++;
        else
            Toast.makeText(AddPet.this,"Description must be less than or equal to 30",Toast.LENGTH_LONG).show();

        if(editName.getText().toString()=="")
            Toast.makeText(AddPet.this,"Name field is empty",Toast.LENGTH_LONG).show();


        if(editAge.getText().toString()=="")
            Toast.makeText(AddPet.this,"Age field is empty",Toast.LENGTH_LONG).show();

        if(editDescription.getText().toString()=="")
            Toast.makeText(AddPet.this,"Description field is empty",Toast.LENGTH_LONG).show();

        if(counter==3){
            boolean isInserted= myDP.insertdata(editName.getText().toString(),
                    editAge.getText().toString(),
                    editDescription.getText().toString());
            if(isInserted==true) {
                Toast.makeText(AddPet.this, "Pet has been successfully added ", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AddPet.this, MainActivityPetsList.class);
                AddPet.this.startActivity(intent);
            }
            else
                Toast.makeText(AddPet.this,"Pet has not been added successfully. Please check all fields",Toast.LENGTH_LONG).show();
        }

    }

}