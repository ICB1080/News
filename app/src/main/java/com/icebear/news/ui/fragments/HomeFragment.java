package com.icebear.news.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.icebear.news.R;
import com.icebear.news.repository.NewsRepository;
import com.icebear.news.repository.NewsViewModelFactory;
import com.icebear.news.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {
    private HomeViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

//    Every time if we switch the interface or screen rotations, the fragment will
//    call back onCreateView() then onViewCreated

//    And we do not want ot lose countryInput during these activities.
//    So a ViewModelProvider can retain view models and persist them
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NewsRepository repository = new NewsRepository();
//        viewModel = new HomeViewModel(repository);
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(repository)).get(HomeViewModel.class);
        viewModel.setCountryInput("us");
        viewModel
                .getTopHeadlines()
                .observe(
                        getViewLifecycleOwner(),
                        newsResponse -> {
                            if (newsResponse != null) {
                                Log.d("HomeFragment", newsResponse.toString());
                            }
                        });
    }



}