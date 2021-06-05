package com.vimoautomations.wordscheck;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.vimoautomations.wordscheck.db.entities.Word;
import com.vimoautomations.wordscheck.repositories.WordRepository;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

    private WordRepository wordRepository;

    private final LiveData<List<Word>> mAllWords;
    public WordViewModel(@NonNull Application application) {
        super(application);
        wordRepository = new WordRepository(application);
        mAllWords = wordRepository.getAllWords();
    }
    LiveData<List<Word>> getAllWords(){return mAllWords;}
    void insert(Word word){
        wordRepository.insert(word);
    }
}
