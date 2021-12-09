package com.example.petscarenew;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CreatePet extends BottomSheetDialogFragment {

    Unbinder unbinder;
    @BindView(R.id.Name)
    EditText Petname;
    @BindView(R.id.Description)
    EditText Description;
    @BindView(R.id.Age)
    EditText Age;
    @BindView(R.id.addPet)
    Button addPet;
    int taskId;
    boolean isEdit;
    Pet pet;
    setRefreshListener setRefreshListener;
    MainActivityPetsList activity;

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    public void setTaskId(int taskId, boolean isEdit, setRefreshListener setRefreshListener, MainActivityPetsList activity) {
        this.taskId = taskId;
        this.isEdit = isEdit;
        this.activity = activity;
        this.setRefreshListener = setRefreshListener;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"RestrictedApi", "ClickableViewAccessibility"})
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_create_pet, null);
        unbinder = ButterKnife.bind(this, contentView);
        dialog.setContentView(contentView);
        addPet.setOnClickListener(view -> {
            if(validateFields())
            createTask();
        });
        if (isEdit) {
            showTaskFromId();
        }
    }

    public boolean validateFields() {
        int counter =0;
        if(Petname.getText().toString().equalsIgnoreCase("")) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity, R.style.AppTheme_Dialog);
            alertDialogBuilder.setTitle("Warning").setMessage("Please enter a valid name").show();
            return false;
        }
        else if(Description.getText().toString().equalsIgnoreCase("")) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity, R.style.AppTheme_Dialog);
            alertDialogBuilder.setTitle("Warning").setMessage("Please enter a valid description").show();

            return false;
        }
        else if(Age.getText().toString().equalsIgnoreCase("")) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity, R.style.AppTheme_Dialog);
            alertDialogBuilder.setTitle("Warning").setMessage("Please enter a valid Age").show();
            return false;
        }
        if(Petname.getText().length()<=15 && Petname.getText().length()>0)
            counter++;
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity, R.style.AppTheme_Dialog);
            alertDialogBuilder.setTitle("Warning").setMessage("Name must be less than or equal to 15").show();
            return false;
        }

        if(Age.getText().length()<=2 && Age.getText().length()>0)
            counter++;
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity, R.style.AppTheme_Dialog);
            alertDialogBuilder.setTitle("Warning").setMessage("Age must be 2 or 1 digit only").show();
            return false;
        }

        if(Description.getText().length()<=30 && Age.getText().length()>0)
            counter++;
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity, R.style.AppTheme_Dialog);
            alertDialogBuilder.setTitle("Warning").setMessage("Description must be less than or equal to 30").show();
            return false;
        }

        if(counter==3){
            return true;
        }
        else{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity, R.style.AppTheme_Dialog);
            alertDialogBuilder.setTitle("Warning").setMessage("Pet has not been added successfully. Please check all fields").show();
        return false; }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void createTask() {
        class saveTaskInBackend extends AsyncTask<Void, Void, Void> {
            @SuppressLint("WrongThread")
            @Override
            protected Void doInBackground(Void... voids) {
                Pet createPet = new Pet();
                createPet.setName(Petname.getText().toString());
                createPet.setDescription(Description.getText().toString());
                createPet.setAge(Age.getText().toString());

                if (!isEdit)
                    DatabaseClient.getInstance(getActivity()).getAppDatabase()
                            .dataBaseAction()
                            .insertDataIntoPetList(createPet);
                else
                    DatabaseClient.getInstance(getActivity()).getAppDatabase()
                            .dataBaseAction()
                            .updateAnExistingRow(taskId, Petname.getText().toString(),
                                    Description.getText().toString(),
                                    Age.getText().toString());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                setRefreshListener.refresh();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity, R.style.AppTheme_Dialog);
                alertDialogBuilder.setTitle("Pet added ✔").setMessage("  ").show();
                dismiss();

            }
        }
        saveTaskInBackend st = new saveTaskInBackend();
        st.execute();
    }


    private void showTaskFromId() {
        class showTaskFromId extends AsyncTask<Void, Void, Void> {
            @SuppressLint("WrongThread")
            @Override
            protected Void doInBackground(Void... voids) {
                pet = DatabaseClient.getInstance(getActivity()).getAppDatabase()
                        .dataBaseAction().selectDataFromAnId(taskId);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                setDataInUI();
            }
        }
        showTaskFromId st = new showTaskFromId();
        st.execute();
    }

    private void setDataInUI() {
        Petname.setText(pet.getName());
        Description.setText(pet.getDescription());
        Age.setText(pet.getAge());
    }

    public interface setRefreshListener {
        void refresh();
    }
}
