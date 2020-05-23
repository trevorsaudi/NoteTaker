package com.saudi.cat1_todonotes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

//androidviewmodel is a subclass of the view model
public class NoteViewModel extends AndroidViewModel {
    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;


    public NoteViewModel(@NonNull Application application) {
        //never store context of an activity here, it will create a memory leak
        super(application);
        //instantiate the variable and assign the nodes to allNodes
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(Note note){
        repository.insert(note);
    }
    public void update(Note note){
        repository.update(note);
    }

    public void delete(Note note){
        repository.delete(note);
    }

    public void deleteAllNotes(){
        repository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes(){
        return  allNotes;
    }
}
