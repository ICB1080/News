package com.icebear.news.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.icebear.news.R;
import com.icebear.news.adapters.SearchNewsAdapter;
import com.icebear.news.databinding.FragmentSearchBinding;
import com.icebear.news.repository.NewsRepository;
import com.icebear.news.repository.NewsViewModelFactory;
import com.icebear.news.viewmodel.SearchViewModel;

public class SearchFragment extends Fragment {
    private SearchViewModel viewModel;
    private FragmentSearchBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SearchNewsAdapter newsAdapter = new SearchNewsAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 1);
        binding.newsResultsRecyclerView.setLayoutManager(gridLayoutManager);
        binding.newsResultsRecyclerView.setAdapter(newsAdapter);


//        set a listener at search bar, if xxx is entered, set searchInput to be xxx
        binding.newsSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    viewModel.setSearchInput(query);
                }
                binding.newsSearchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



        NewsRepository repository = new NewsRepository();
//        viewModel = new SearchViewModel(repository);
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(repository)).get(SearchViewModel.class);

//        viewModel.setSearchInput("Covid-19");
        viewModel
                .searchNews()
                .observe(
                        getViewLifecycleOwner(),
                        newsResponse -> {
                            if (newsResponse != null) {
                                Log.d("SearchFragment", newsResponse.toString());
                                newsAdapter.setArticles(newsResponse.articles);
                            }
                        });

        // get the navigation direction, provide argument: article and navigate to the direction detail page
        newsAdapter.setItemCallback(article -> {
            SearchFragmentDirections.ActionNavigationSearchToNavigationDetails direction =
                    SearchFragmentDirections.actionNavigationSearchToNavigationDetails(article);
            NavHostFragment.findNavController(SearchFragment.this).navigate(direction);
        });
    }




}