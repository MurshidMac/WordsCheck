package com.vimoautomations.wordscheck.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.vimoautomations.wordscheck.config.AppConfig;
import com.vimoautomations.wordscheck.db.dao.WordDao;
import com.vimoautomations.wordscheck.db.entities.Word;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {Word.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WordDao wordDao();

    // MAKE IT VOLATILE SO IT STAYS IN THE MAIN MEMORY AND NOT IN CPU CACHE
    // ALSO TO SYNCHRONIZATION
    private static volatile AppDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (AppDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, AppConfig.DATABASE_NAME
                            ).addCallback(sRoomDatabaseCallback).build();

                }
            }
        }
        return INSTANCE;
    }

    // This is for static data
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                WordDao dao = INSTANCE.wordDao();
                dao.deleteAll();

                Word word = new Word("Hello");
                dao.insert(word);
                word = new Word("World");
                dao.insert(word);
            });
        }
    };
}
