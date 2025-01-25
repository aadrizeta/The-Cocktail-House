package com.finalproyect.thecocktailhouse;

import android.content.Context;
import android.content.DialogInterface;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CocktailViewAdapter extends RecyclerView.Adapter<CocktailViewAdapter.CocktailViewHolder> {

    View view;
    List<Drinks.Cocktail> listaCocktails;
    List<Drinks.Cocktail> detalleCocktail;
    Context context;
    ViewGroup parent;

    public CocktailViewAdapter(List<Drinks.Cocktail>listaCocktails, Context context) {
        this.listaCocktails = listaCocktails;
        this.context = context;
    }

    @NonNull
    @Override
    public CocktailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent= parent;
        this.view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cocktail_cardview, parent, false);
        return new CocktailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CocktailViewHolder holder, int position) {
        holder.bind(listaCocktails.get(position).getCocktailName(),
                listaCocktails.get(position).getCocktailImageUrl());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View detailsCocktailView =LayoutInflater.from(context).inflate(R.layout.cocktail_details_layout, parent, false);

                ImageView imageView = detailsCocktailView.findViewById(R.id.imageView2);
                TextView idCocktail = detailsCocktailView.findViewById(R.id.idCocktail);
                TextView nombreCocktail = detailsCocktailView.findViewById(R.id.nombreCocktail);

                Glide.with(view)
                        .load(listaCocktails.get(holder.getAdapterPosition()).getCocktailImageUrl())
                        .transition(DrawableTransitionOptions.withCrossFade(500))
                        .into(imageView);

                idCocktail.setText("ID: " + listaCocktails.get(holder.getAdapterPosition()).cocktailId);
                nombreCocktail.setText(listaCocktails.get(holder.getAdapterPosition()).cocktailName);

                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<Drinks> call = apiInterface.getDrinksById(listaCocktails.get(holder.getAdapterPosition()).cocktailId);
                call.enqueue(new Callback<Drinks>() {
                    @Override
                    public void onResponse(Call<Drinks> call, Response<Drinks> response) {
                        Drinks drinks = response.body();

                        if (drinks != null){
                            detalleCocktail = drinks.getDrinks();
                        }
                        String instructions =detalleCocktail.get(0).getCocktailInstructionsES();
                        TextView cocktailDescription = detailsCocktailView.findViewById(R.id.cocktailDescription);
                        cocktailDescription.setText(instructions);
                        cocktailDescription.setMovementMethod(new ScrollingMovementMethod());
                    }

                    @Override
                    public void onFailure(Call<Drinks> call, Throwable throwable) {

                    }
                });
                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(context)
                        .setView(detailsCocktailView)
                        .setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {}
                        });
                materialAlertDialogBuilder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaCocktails.size();
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
