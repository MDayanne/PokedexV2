package pe.autonoma.pokedexv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pe.autonoma.pokedexv2.models.Pokemon;
import pe.autonoma.pokedexv2.models.Pokemons;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    ListView lvListado;
    Retrofit retrofit;
    PokemonApi pokemonApi;
    String nameFromLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        lvListado = findViewById(R.id.lvListado);
        //obtener el adaptador a la ruta de pokeapi
        retrofit = new PokemonAdapter().getAdapter();
        //inicializamos pokemonApi
        pokemonApi = retrofit.create(PokemonApi.class);
        //
        Call<Pokemons> pokemonsCall = pokemonApi.getDataPokemons();

        pokemonsCall.enqueue(new Callback<Pokemons>() {
            @Override
            public void onResponse(Call<Pokemons> call, Response<Pokemons> response) {
                //Log.d("Poke",response.toString() );

                //almaceno los pokemones objetenidos en Results
                List<Pokemon> pokemons = response.body().getResults();
                //creo un listado del mismo tamaño de la data recibida
                ArrayList<String> stringsList =
                        new ArrayList<>((pokemons.size()));
                //lleno el listado con los nombres de los pokemones
                for (Pokemon pokemon : pokemons) {
                    stringsList.add(pokemon.getName());
                }

                //creo adaptador
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        MainActivity.this,
                        R.layout.support_simple_spinner_dropdown_item,
                        stringsList);

                //asigno adaptador al listado
                lvListado.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Pokemons> call, Throwable t) {

            }
        });//enquenque

        lvListado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                nameFromLV = (((TextView) view).getText()).toString();
                //Toast.makeText(getApplicationContext(), nameFromLV,
                //        Toast.LENGTH_SHORT).show();
                Intent intent =
                        new Intent(
                                MainActivity.this,
                                PokemonDetalleActivity.class);
                intent.putExtra("nameFromLV", nameFromLV);
                //stringsList con la posicion
                //intent.putExtra("nombre",);

                startActivity(intent);
            }
        });


        //Call<Pokemons> pokemonsCall = pokemonAPI.getDataPokemons();

    }
}