package com.saudi.cat1_todonotes;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Update;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    //the constructor where we assign the application
    public NoteRepository(Application application){
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
    }
//Room doesn't allow main database operations on the main thread. Happens in the background thread
    public void insert (Note note){
        new InsertNoteAsyncTask(noteDao).execute(note);

    }

    public void update(Note note){
        new UpdateNoteAsyncTask(noteDao).execute(note);

    }
    public void delete(Note note){
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes(){
        new DeleteAllNoteAsyncTask(noteDao).execute();

    }
    public LiveData<List<Note>> getAllNotes (){
        return allNotes;
    }

    //create multiple async tasks

    //has to be static so it doesnt have a reference to the repository
    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void>{
        private NoteDao noteDao; //this is required for database operations

        private InsertNoteAsyncTask(NoteDao noteDao){ //pass notedao since we cant access it from outside
            this.noteDao = noteDao;
        }

        @Override //you have to override this method to overcome the error
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]); //since we only pass one node we pass it at index 0
            return null;
        }
    }


    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void>{
        private NoteDao noteDao; //this is required for database operations

        private UpdateNoteAsyncTask(NoteDao noteDao){ //pass notedao since we cant access it from outside
            this.noteDao = noteDao;
        }

        @Override //you have to override this method to overcome the error
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]); //since we only pass one node we pass it at index 0
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void>{
        private NoteDao noteDao; //this is required for database operations

        private DeleteNoteAsyncTask(NoteDao noteDao){ //pass notedao since we cant access it from outside
            this.noteDao = noteDao;
        }

        @Override //you have to override this method to overcome the error
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]); //since we only pass one node we pass it at index 0
            return null;
        }
    }

    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void>{
        private NoteDao noteDao; //this is required for database operations

        private DeleteAllNoteAsyncTask(NoteDao noteDao){ //pass notedao since we cant access it from outside
            this.noteDao = noteDao;
        }

        @Override //you have to override this method to overcome the error
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes(); //since we only pass one node we pass it at index 0
            return null;
        }
    }
}
