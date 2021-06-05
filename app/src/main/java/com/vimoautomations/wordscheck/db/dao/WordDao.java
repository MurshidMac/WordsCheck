package com.vimoautomations.wordscheck.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.vimoautomations.wordscheck.db.entities.Word;

import java.util.List;

@Dao
public interface WordDao {
    // same words can be inserted without any issues
    // conflicting resolution strategy
    @Insert
    void insert(Word word);

    @Query("DELETE FROM words_table")
    void deleteAll();

    // Make it a Lifecycle call so that its safe
    @Query("SELECT * FROM words_table ORDER BY word ASC")
    LiveData<List<Word>> getWordsInAlphabeticalOrder();


}
