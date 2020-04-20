package com.bsrakdg.recipes.adapters;

import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bsrakdg.recipes.R;
import com.bsrakdg.recipes.models.Recipe;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private CircleImageView imageView;
    private TextView categoryTitle;
    private OnRecipeListener listener;
    private Recipe recipe;

    CategoryViewHolder(@NonNull View itemView, OnRecipeListener listener) {
        super(itemView);
        imageView = itemView.findViewById(R.id.category_image);
        categoryTitle = itemView.findViewById(R.id.category_title);
        this.listener = listener;

        // add onClickListener
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onCategoryClick(recipe.getTitle());
        }
    }

    void onBind(Recipe recipe) {
        this.recipe = recipe;

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Uri uri = Uri
                .parse("android.resource://com.bsrakdg.recipes/drawable/" + recipe.getImage_url());
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(uri)
                .into(imageView);

        categoryTitle.setText(recipe.getTitle());
    }
}
