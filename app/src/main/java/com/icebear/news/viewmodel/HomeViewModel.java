package com.icebear.news.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.icebear.news.model.NewsResponse;
import com.icebear.news.repository.NewsRepository;

public class HomeViewModel extends ViewModel {

    private final NewsRepository repository;
    private final MutableLiveData<String> countryInput = new MutableLiveData<>();

    public HomeViewModel(NewsRepository repository){
        this.repository = repository;
    }

    public void setCountryInput(String country){
        countryInput.setValue(country);
    }


    public LiveData<NewsResponse> getTopHeadlines(){
        // observe countryInput, if it is changed,
        // apply getTopHeadlines in repository and return the resulting data
        return Transformations.switchMap(countryInput, repository::getTopHeadlines);
    }
}
