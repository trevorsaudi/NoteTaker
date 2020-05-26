package com.saudi.cat1_todonotes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
public static final int  ADD_NOTE_REQUEST =1;
    //creating a reference to the viewmodel so we can pass it to our activity
        private NoteViewModel noteViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivityForResult(intent, ADD_NOTE_REQUEST);

            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //adds notes below each other
        recyclerView.setHasFixedSize(true); //the recycler view size does not change

        final NoteAdapter adapter = new NoteAdapter(); //we use final since we access it in an inner class
        recyclerView.setAdapter(adapter);
        /*we dont call noteViewModel since it will create another instance of viewmodel
        with every new activity, so instead we get it from the android system
           */
        //viewmodelproviders is used to pass the fragment by using its context
        //the view model gets scoped to to the context of the fragment

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_NOTE_REQUEST && resultCode== RESULT_OK){
            String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY,1);

            Note note = new Note(title, description, priority);
            noteViewModel.insert(note);
            Toast.makeText(this,"Note saved", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this,"Note not saved", Toast.LENGTH_SHORT).show();
        }
    }
}
