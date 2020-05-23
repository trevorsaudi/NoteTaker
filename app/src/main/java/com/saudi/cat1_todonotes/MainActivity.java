package com.saudi.cat1_todonotes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //creating a reference to the viewmodel so we can pass it to our activity
        private NoteViewModel noteViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //adds notes below each other
        recyclerView.setHasFixedSize(true); //the recycler view size does not change

        final NoteAdapter adapter = new NoteAdapter(); //we use final since we access it in an inner class
        recyclerView.setAdapter(adapter);
        /*we dont call noteViewModel since it will create another instance of viewmodel
        with every new activity, so instead we get it from the android system
           */
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        //livedata
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) { //triggered everytime the livedata changes
                //update RecyclerView
                //the adapter is updated everytime obChanged is called, when new data comes
             adapter.setNotes(notes);
            }
        });
    }
}
