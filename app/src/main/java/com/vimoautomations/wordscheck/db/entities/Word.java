package com.vimoautomations.wordscheck.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "words_table")
public class Word {


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")  // This is the column
    private String mWord;

    public Word(@NonNull String word){
        this.mWord = word;
    }
    public String getWord(){
        return this.mWord;
    }
}
