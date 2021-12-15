package com.example.petscarenew;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    @BindView(R.id.TitleAdd)
    TextView TitleAdd;
    int petID;
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

    public void setPetId(int PetId, boolean isEdit, setRefreshListener setRefreshListener, MainActivityPetsList activity) {
        this.petID = PetId;
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
            createPet();
        });
        if (isEdit) {
            showPetFromId();
        }
    }

    public boolean validateFields() {
        int counter =0;


        if(Petname.getText().toString().equalsIgnoreCase("")) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity, R.style.AppTheme_Dialog);
            alertDialogBuilder.setTitle("Warning").setMessage("Please enter a valid name").show();
            return false;
        }
        if(Description.getText().toString().equalsIgnoreCase("")) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity, R.style.AppTheme_Dialog);
            alertDialogBuilder.setTitle("Warning").setMessage("Please enter a valid description").show();

            return false;
        }
        if(Age.getText().toString().equalsIgnoreCase("")) {
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

        try{
            int checkAge = Integer.parseInt(Age.getText().toString());
        }catch (NumberFormatException ex) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity, R.style.AppTheme_Dialog);
            alertDialogBuilder.setTitle("Warning").setMessage("Age must be a number only").show();
            return false;
        }

        if(Age.getText().length()<=2 && Age.getText().length()>0)
            counter++;
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity, R.style.AppTheme_Dialog);
            alertDialogBuilder.setTitle("Warning").setMessage("Age must be 2 or 1 digit only").show();
            return false;
        }

        if(Description.getText().length()<=30 && Description.getText().length()>0)
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

    private void createPet() {
        class savePetInBackend extends AsyncTask<Void, Void, Void> {
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
                            .updateAnExistingRow(petID, Petname.getText().toString(),
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
        savePetInBackend st = new savePetInBackend();
        st.execute();
    }


    private void showPetFromId() {
        class showPetFromId extends AsyncTask<Void, Void, Void> {
            @SuppressLint("WrongThread")
            @Override
            protected Void doInBackground(Void... voids) {
                pet = DatabaseClient.getInstance(getActivity()).getAppDatabase()
                        .dataBaseAction().selectDataFromAnId(petID);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                setDataInUI();
            }
        }
        showPetFromId st = new showPetFromId();
        st.execute();
    }

    private void setDataInUI() {
        Petname.setText(pet.getName());
        Description.setText(pet.getDescription());
        Age.setText(pet.getAge());
        TitleAdd.setText("Update Pet");
        addPet.setText("Update Pet");
    }

    public interface setRefreshListener {
        void refresh();
    }

    public boolean validateFieldsTest(String name,String Age,String des) {
        int counter =0;
        if(name.equalsIgnoreCase("")) {
            System.out.println("Please enter a valid name");
            Log.d("myTag", "This is my message");
            return false;
        }
        if(des.equalsIgnoreCase("")) {
            System.out.println("Please enter a valid description");

            return false;
        }
        if(Age.equalsIgnoreCase("")) {
            System.out.println("Please enter a valid Age");
            return false;
        }
        if(name.length()<=15 && name.length()>0)
            counter++;
        else {
            System.out.println("Name must be less than or equal to 15");
            return false;
        }

        try{
            int checkAge = Integer.parseInt(Age);
        }catch (NumberFormatException ex) {
            System.out.println("Age must be a number only");
            return false;
        }

        if(Age.length()<=2 && Age.length()>0)
            counter++;
        else {
            System.out.println("Age must be 2 or 1 digit only");
            return false;
        }

        if(des.length()<=30 && des.length()>0)
            counter++;
        else {
            System.out.println("Description must be less than or equal to 30");
            return false;
        }

        if(counter==3){
            return true;
        }
        else{
            System.out.println("Pet has not been added successfully. Please check all fields");

            return false; }

    }
}
