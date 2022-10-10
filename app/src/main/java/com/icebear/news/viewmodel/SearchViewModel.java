package com.icebear.news.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.icebear.news.model.NewsResponse;
import com.icebear.news.repository.NewsRepository;

public class SearchViewModel extends ViewModel {
    private final NewsRepository repository;

    // MutableLiveData can change its value by setValue
    private final MutableLiveData<String> searchInput = new MutableLiveData<>();

    public SearchViewModel(NewsRepository repository) {
        this.repository = repository;
    }

    public void setSearchInput(String query) {
        searchInput.setValue(query);
    }

    public LiveData<NewsResponse> searchNews() {
        // if searchInput change, applies function searchNews to new value of searchInput
        // and return sets new resulting LiveData as a result
        return Transformations.switchMap(searchInput, repository::searchNews);
    }

}
