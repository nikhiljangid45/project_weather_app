package com.example.projectweatherapp.RoomDatabase;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mainProject")
public class Note {
        private String name;
        @PrimaryKey(autoGenerate = true)
        private int id;
    public Note(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
