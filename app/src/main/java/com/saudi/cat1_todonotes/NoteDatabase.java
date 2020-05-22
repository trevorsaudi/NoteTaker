package com.saudi.cat1_todonotes;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance; //turns the class into a singleton which means we cannot create multiple instances of the database, instead we reuse the instance
    public abstract NoteDao noteDao(); //no body since room takes care of the code

    public static synchronized NoteDatabase getInstance(Context context){
        //synchronized means only one thread can access the database at a time. it prevents multiple instance when more than one thread access the database
        if(instance == null){ //only instantiate the db if there is no existing instance
        instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_database").fallbackToDestructiveMigration().addCallback(roomCallback).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //execute the oncreate method once the database is created
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    //create an async task for populating the database
    private  static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private NoteDao noteDao;

        //pass the variable into a constructor

        private PopulateDbAsyncTask(NoteDatabase db){
            noteDao = db.noteDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
           noteDao.insert(new Note("Title 1","Description 1",1));
           noteDao.insert(new Note("Title 2","Description 2",2));
           noteDao.insert(new Note("Title 3","Description 3",3));
           return null;
        }
    }


}
