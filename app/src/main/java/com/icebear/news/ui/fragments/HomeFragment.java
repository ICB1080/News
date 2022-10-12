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
import com.icebear.news.adapters.CardSwipeAdapter;
import com.icebear.news.databinding.FragmentHomeBinding;
import com.icebear.news.model.Article;
import com.icebear.news.repository.NewsRepository;
import com.icebear.news.repository.NewsViewModelFactory;
import com.icebear.news.viewmodel.HomeViewModel;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.StackFrom;

import java.util.List;

public class HomeFragment extends Fragment {
    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;
    private CardStackLayoutManager layoutManager;
    private List<Article> articles;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

//    Every time if we switch the interface or screen rotations, the fragment will
//    call back onCreateView() then onViewCreated

//    And we do not want ot lose countryInput during these activities.
//    So a ViewModelProvider appears, it can retain view models and persist them
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Setup CardStackView
        CardSwipeAdapter swipeAdapter = new CardSwipeAdapter();
        layoutManager = new CardStackLayoutManager(requireContext());
        layoutManager.setStackFrom(StackFrom.Top);
        binding.homeCardStackView.setLayoutManager(layoutManager);
        binding.homeCardStackView.setAdapter(swipeAdapter);

        // Handle like unlike button clicks
        // TODO




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
                                articles = newsResponse.articles;
                                swipeAdapter.setArticles(articles);
                            }
                        });
    }



}