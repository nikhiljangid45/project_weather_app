package com.example.projectweatherapp.RoomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Note.class,version = 2)
public abstract class NoteDatabase extends RoomDatabase {


    public static NoteDatabase instance;

    public abstract NoteDao noteDao();

      public static NoteDatabase getInstance(Context context){
          if (instance == null) {
              instance = Room.databaseBuilder(context.getApplicationContext(),
                              NoteDatabase.class, "main_location")
                      .fallbackToDestructiveMigration().build();
          }
          return instance;
      }


}
