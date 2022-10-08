package com.icebear.news.repository;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.icebear.news.viewmodel.HomeViewModel;
import com.icebear.news.viewmodel.SearchViewModel;


public class NewsViewModelFactory implements ViewModelProvider.Factory {

    private final NewsRepository repository;

    public NewsViewModelFactory(NewsRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(repository);
        } else if (modelClass.isAssignableFrom(SearchViewModel.class)) {
            return (T) new SearchViewModel(repository);
        } else {
            throw new IllegalStateException("Unknown ViewModel");
        }
    }
}


