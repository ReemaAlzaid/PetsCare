package com.example.petscarenew;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Pet implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int petID;
    @ColumnInfo(name = "Name")
    String Name;
    @ColumnInfo(name = "Age")
    String Age;
    @ColumnInfo(name = "Description")
    String Description;

    public Pet() {

    }

    public String getAge() {
        return Age;
    }

    public void setAge(String lastAlarm) {
        this.Age = lastAlarm;
    }

    public int getPetID() {
        return petID;
    }

    public void setPetID(int petID) {
        this.petID = petID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }
}
