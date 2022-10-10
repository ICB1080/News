package com.icebear.news.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.icebear.news.R;
import com.icebear.news.databinding.SearchNewsItemBinding;
import com.icebear.news.model.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchNewsAdapter extends RecyclerView.Adapter<SearchNewsAdapter.SearchNewsViewHolder> {
    // 1. Supporting data:
    private List<Article> articles = new ArrayList<>();

    public void setArticles(List<Article> newsList) {
        articles.clear();
        articles.addAll(newsList);
        // Everytime, a newList is set, we call notifyDataSetChanged to let the adapter refresh and re-render
        notifyDataSetChanged();
    }

    // 2. Adapter overrides:

    // provide searchNewsItem and create SearchNewsViewHolder
    @NonNull
    @Override
    public SearchNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_news_item, parent, false);
        return new SearchNewsViewHolder(view);

    }

    // binding the data with a view
    @Override
    public void onBindViewHolder(@NonNull SearchNewsViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.itemTitleTextView.setText(article.title);
        if (article.urlToImage != null){
            Picasso.get().load(article.urlToImage).resize(200, 200).into(holder.itemImageView);
        }

    }

    // provide current data collection's size
    @Override
    public int getItemCount() {
        return articles.size();
    }


    // 3. SearchNewsViewHolder:
    // create an inner class: SearchNewsViewHolder
    // ViewHolder describe an item view and metadata about its place
    public static class SearchNewsViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImageView;
        TextView itemTitleTextView;

        public SearchNewsViewHolder(@NonNull View itemView){
            // itemView is from onCreateViewHolder()
            super(itemView);
            SearchNewsItemBinding binding = SearchNewsItemBinding.bind(itemView);
            itemImageView = binding.searchItemImage;
            itemTitleTextView = binding.searchItemTitle;
        }
    }



}
