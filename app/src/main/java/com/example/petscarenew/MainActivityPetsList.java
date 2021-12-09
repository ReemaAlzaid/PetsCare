package com.example.petscarenew;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
public class MainActivityPetsList extends BaseActivity implements CreatePet.setRefreshListener {

    @BindView(R.id.taskRecycler)
    RecyclerView taskRecycler;
    @BindView(R.id.addPet)
    TextView addTask;
    PetAdapter taskAdapter;
    List<Pet> pets = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pet_list);
        ButterKnife.bind(this);
        setUpAdapter();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        PackageManager pm = getPackageManager();
        addTask.setOnClickListener(view -> {
            CreatePet createPet = new CreatePet();
            createPet.setTaskId(0, false, this, MainActivityPetsList.this);
            createPet.show(getSupportFragmentManager(), createPet.getTag());
        });

        getSavedTasks();


    }

    public void setUpAdapter() {
        taskAdapter = new PetAdapter(this, pets, this);
        taskRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        taskRecycler.setAdapter(taskAdapter);
    }

    private void getSavedTasks() {

        class GetSavedTasks extends AsyncTask<Void, Void, List<Pet>> {
            @Override
            protected List<Pet> doInBackground(Void... voids) {
                pets = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .dataBaseAction()
                        .getAllTasksList();
                return pets;
            }

            @Override
            protected void onPostExecute(List<Pet> tasks) {
                super.onPostExecute(tasks);
                setUpAdapter();
            }
        }

        GetSavedTasks savedTasks = new GetSavedTasks();
        savedTasks.execute();
    }

    @Override
    public void refresh() {
        getSavedTasks();
    }

    public void Home1(View view) {
        Intent intent = new Intent(MainActivityPetsList.this, MainActivity.class);
        MainActivityPetsList.this.startActivity(intent);
    }
}
