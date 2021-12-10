package com.example.petscarenew;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    private MainActivityPetsList context;
    private LayoutInflater inflater;
    private List<Pet> petList;
    Date date = null;
    String outputDateString = null;
    CreatePet.setRefreshListener setRefreshListener;

    public PetAdapter(MainActivityPetsList context, List<Pet> petList, CreatePet.setRefreshListener setRefreshListener) {
        this.context = context;
        this.petList = petList;
        this.setRefreshListener = setRefreshListener;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.item_pet, viewGroup, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        Pet pet = petList.get(position);
        holder.title.setText(pet.getName());
        holder.description.setText(pet.getDescription());
        holder.time.setText(pet.getAge());
        holder.options.setOnClickListener(view -> showPopUpMenu(view, position));

    }

    public void showPopUpMenu(View view, int position) {
        final Pet pet = petList.get(position);
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menuDelete:
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.AppTheme_Dialog);
                    alertDialogBuilder.setTitle(R.string.delete_confirmation).setMessage(R.string.sureToDelete).
                            setPositiveButton("yes", (dialog, which) -> {
                                deletePetFromId(pet.getPetID(), position);
                            })
                            .setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel()).show();
                    break;
                case R.id.menuUpdate:
                    CreatePet createPet = new CreatePet();
                    createPet.setPetId(pet.getPetID(), true, context, context);
                    createPet.show(context.getSupportFragmentManager(), createPet.getTag());
                    break;
            }
            return false;
        });
        popupMenu.show();
    }



    private void deletePetFromId(int petId, int position) {
        class GetSavedPets extends AsyncTask<Void, Void, List<Pet>> {
            @Override
            protected List<Pet> doInBackground(Void... voids) {
                DatabaseClient.getInstance(context)
                        .getAppDatabase()
                        .dataBaseAction()
                        .deletePetFromId(petId);

                return petList;
            }

            @Override
            protected void onPostExecute(List<Pet> pets) {
                super.onPostExecute(pets);
                removeAtPosition(position);
                setRefreshListener.refresh();
            }
        }
        GetSavedPets savedPets = new GetSavedPets();
        savedPets.execute();
    }

    private void removeAtPosition(int position) {
        petList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, petList.size());
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    public class PetViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.description)
        TextView description;
        @BindView(R.id.options)
        ImageView options;
        @BindView(R.id.Ageshow)
        TextView time;

        PetViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
