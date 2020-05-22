package com.saudi.cat1_todonotes;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance; //turns the class into a singleton which means we cannot create multiple instances of the database, instead we reuse the instance
    public abstract NoteDao noteDao(); //no body since room takes care of the code
    public static synchronized NoteDatabase getInstance(Context context){
        //synchronized means only one thread can access the database at a time. it prevents multiple instance when more than one thread access the database
        if(instance == null){ //only instantiate the db if there is no existing instance
        instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_database").fallbackToDestructiveMigration().build();
        }
        return instance;
    }



}
