package com.icebear.news.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.icebear.news.NewsApplication;
import com.icebear.news.api.NewsApi;
import com.icebear.news.api.RetrofitInstance;
import com.icebear.news.database.NewsDatabase;
import com.icebear.news.model.Article;
import com.icebear.news.model.NewsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private final NewsApi newsApi;
    private final NewsDatabase database;


    public NewsRepository(){
        newsApi = RetrofitInstance.newInstance().create(NewsApi.class);
        database = NewsApplication.getDatabase();
    }

    public LiveData<NewsResponse> getTopHeadlines(String country) {
        MutableLiveData<NewsResponse> topHeadlinesLiveData = new MutableLiveData<>();
        newsApi.getTopHeadlines(country)
                .enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        if (response.isSuccessful()) {
                            topHeadlinesLiveData.setValue(response.body());
                        } else {
                            topHeadlinesLiveData.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        topHeadlinesLiveData.setValue(null);
                    }
                });
        return topHeadlinesLiveData;
    }

    public LiveData<NewsResponse> searchNews(String query){
        MutableLiveData<NewsResponse> everyThingLiveData = new MutableLiveData<>();
        newsApi.searchNews(query, 40)
                .enqueue(
                        new Callback<NewsResponse>() {
                            @Override
                            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                                if (response.isSuccessful()) {
                                    everyThingLiveData.setValue(response.body());
                                } else {
                                    everyThingLiveData.setValue(null);
                                }
                            }

                            @Override
                            public void onFailure(Call<NewsResponse> call, Throwable t) {
                                everyThingLiveData.setValue(null);
                            }
                        });
        return everyThingLiveData;

    }

    // database query accessing the disk storage can be very slow
    // so we dispatch the query work to a background thread
    private static class FavoriteAsyncTask extends AsyncTask<Article, Void, Boolean> {

        private final NewsDatabase database;
        private final MutableLiveData<Boolean> liveData;

        private FavoriteAsyncTask(NewsDatabase database, MutableLiveData<Boolean> liveData) {
            this.database = database;
            this.liveData = liveData;
        }


        // everything inside doInBackground should be executed on a separate background thread
        @Override
        protected Boolean doInBackground(Article... articles) {
            Article article = articles[0];
            try {
                // save article
                database.articleDao().saveArticle(article);
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        // after executing doInBackground, onPostExecute would be executed back on the main UI thread
        @Override
        protected void onPostExecute(Boolean success) {
            liveData.setValue(success);
        }
    }

    public LiveData<Boolean> favoriteArticle(Article article) {
        MutableLiveData<Boolean> resultLiveData = new MutableLiveData<>();
        new FavoriteAsyncTask(database, resultLiveData).execute(article);
        return resultLiveData;
    }

    public LiveData<List<Article>> getAllSavedArticles() {
        return database.articleDao().getAllArticles();
    }
    public void deleteSavedArticle(Article article){
        AsyncTask.execute(() -> database.articleDao().deleteArticle(article));
    }

}


