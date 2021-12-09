package com.example.petscarenew;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface OnDataBaseAction {

    @Query("SELECT * FROM Pet")
    List<Pet> getAllTasksList();

    @Query("DELETE FROM Pet")
    void truncateTheList();

    @Insert
    void insertDataIntoPetList(Pet pet);

    @Query("DELETE FROM Pet WHERE petID = :petID")
    void deletePetFromId(int petID);

    @Query("SELECT * FROM Pet WHERE petID = :petID")
    Pet selectDataFromAnId(int petID);

    @Query("UPDATE Pet SET Name = :Name, Description = :Description," +
            "Age = :Age WHERE petID = :petID")
    void updateAnExistingRow(int petID, String Name, String Description, String Age);

}
