package com.icebear.news.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.icebear.news.model.Article;

// version is used to update the database later on
// exportSchema is for dumping a database schema to file system, we do not need it
// room will implement these abstract classes and methods
@Database(entities = {Article.class}, version = 1, exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {
    public abstract ArticleDao articleDao();

}
