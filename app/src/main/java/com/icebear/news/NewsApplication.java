package com.icebear.news;

import android.app.Application;

import androidx.room.Room;

import com.icebear.news.database.NewsDatabase;

public class NewsApplication extends Application {
    private static NewsDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(this, NewsDatabase.class, "news_db").build();
    }

    public static NewsDatabase getDatabase(){
        return database;
    }

}
