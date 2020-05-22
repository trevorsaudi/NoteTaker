package com.saudi.cat1_todonotes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "note_table")//creates an sqlite object on runtime, gives the table name note_table
public class Note {

    @PrimaryKey(autoGenerate = true) //with each new row, id is incremented
    private int id; //uniquely identifies each entry
    private String title; //sqlite creates columns for this attrubutes
    private String description;
    private  int priority;


    //need a constructor to create the note objects
    //no constructor for id since it is automatically generated
    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public void setId(int id) { //the only value that is not in the constructor
        this.id = id;
    }

    //to persist this variables, create getters

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }


}
