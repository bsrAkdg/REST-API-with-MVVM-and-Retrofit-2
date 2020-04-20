package com.bsrakdg.recipes.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bsrakdg.recipes.R;
import com.bsrakdg.recipes.models.Recipe;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView title, publisher, socialScore;
    private AppCompatImageView imageView;
    private OnRecipeListener listener;

    RecipeViewHolder(@NonNull View itemView, OnRecipeListener listener) {
        super(itemView);
        title = itemView.findViewById(R.id.recipe_title);
        publisher = itemView.findViewById(R.id.recipe_publisher);
        socialScore = itemView.findViewById(R.id.recipe_social_score);
        imageView = itemView.findViewById(R.id.recipe_image);
        this.listener = listener;

        // add onClickListener
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        listener.onRecipeClick(getAdapterPosition());
    }

    void onBind(Recipe recipe) {
        title.setText(recipe.getTitle());
        publisher.setText(recipe.getPublisher());
        socialScore.setText(String.valueOf(Math.round(recipe.getSocial_rank())));

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(recipe.getImage_url())
                .into(imageView);
    }
}
