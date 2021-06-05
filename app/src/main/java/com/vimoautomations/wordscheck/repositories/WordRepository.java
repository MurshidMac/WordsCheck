package com.vimoautomations.wordscheck.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.vimoautomations.wordscheck.db.AppDatabase;
import com.vimoautomations.wordscheck.db.dao.WordDao;
import com.vimoautomations.wordscheck.db.entities.Word;

import java.util.List;

public class WordRepository {
    private WordDao wordDao;
    private LiveData<List<Word>> mAllWords;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public WordRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        wordDao = db.wordDao();
        mAllWords = wordDao.getWordsInAlphabeticalOrder();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Word>> getAllWords(){
        return mAllWords;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Word word){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            wordDao.insert(word);



        });
    }
}
