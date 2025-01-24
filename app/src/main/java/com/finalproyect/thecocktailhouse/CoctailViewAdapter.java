package com.finalproyect.thecocktailhouse;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

public class CoctailViewAdapter extends RecyclerView.Adapter<CoctailViewAdapter.CocktailViewHolder> {
    @NonNull
    @Override
    public CoctailViewAdapter.CocktailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CoctailViewAdapter.CocktailViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public static class CocktailViewHolder extends RecyclerView.ViewHolder{

        ImageView cocktailImage;
        TextView cocktailName;

        public CocktailViewHolder(@NonNull View itemView) {
            super(itemView);
            cocktailImage = itemView.findViewById(R.id.cocktailImage);
            cocktailName = itemView.findViewById(R.id.cocktailName);
        }
        public void bind(String name, String imageUrl) {
            cocktailName.setText(name);

            Glide.with(itemView)
                    .load(imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade(500))
                    .into(cocktailImage);
        }


    }
}
