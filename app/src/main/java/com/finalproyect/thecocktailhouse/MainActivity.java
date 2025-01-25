package com.finalproyect.thecocktailhouse;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    List<Drinks.Cocktail> listaCocktails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        CocktailViewAdapter cocktailViewAdapter = new CocktailViewAdapter(listaCocktails, MainActivity.this);
        RecyclerView recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Button botonBuscar = findViewById(R.id.button);
        TextInputLayout barraBusqueda = findViewById(R.id.textInputLayout);
        EditText editText = barraBusqueda.getEditText();


        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alcohol = editText.getText().toString();

                Call<Drinks> call = apiInterface.getDrinksByLicour(alcohol);
                call.enqueue(new Callback<Drinks>() {
                    @Override
                    public void onResponse(Call<Drinks> call, Response<Drinks> response) {
                        Drinks drinks = response.body();

                        if (drinks != null) {
                            listaCocktails = drinks.getDrinks();
                        }
                        CocktailViewAdapter adapter= new CocktailViewAdapter(listaCocktails, MainActivity.this);
                        recyclerView.setAdapter(adapter);

                    }

                    @Override
                    public void onFailure(Call<Drinks> call, Throwable throwable) {
                        Log.d("CALL -> mal", throwable.toString());
                    }
                });
            }
        });

    }
}