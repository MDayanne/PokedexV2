package pe.autonoma.pokedexv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import pe.autonoma.pokedexv2.models.PokemonDetalle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PokemonDetalleActivity extends AppCompatActivity {
    TextView tvNombre,tvBase,tvAlto,tvPeso;
    String nameFromLV,types;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detalle);

        Intent intent=getIntent();
        //
        tvNombre = findViewById(R.id.tvNombre);
        tvBase = findViewById(R.id.tvBase);
        tvAlto= findViewById(R.id.tvAlto);
        tvPeso = findViewById(R.id.tvPeso);

        nameFromLV=intent.getStringExtra("nameFromLV");
        // retroit
        Retrofit retrofit = new PokemonAdapter().getAdapter();
        //instanciamos restClient
        PokemonApi pokemonAPI=retrofit.create(PokemonApi.class);
        //
        //cambiar ivysaur x getExtra
        Call<PokemonDetalle> pokemonDetalleCall =
                pokemonAPI.getPokemonDetalle(nameFromLV);

        pokemonDetalleCall.enqueue(new Callback<PokemonDetalle>() {
            @Override
            public void onResponse(Call<PokemonDetalle> call, Response<PokemonDetalle> response) {
                tvNombre.setText("Nombre: " + response.body().getName() );
                tvBase.setText("Base: " + response.body().getBase_experience().toString() );
                tvAlto.setText("Alto: " + response.body().getHeight().toString() );
                tvPeso.setText("Peso: " + response.body().getWeight().toString() );
            }

            @Override
            public void onFailure(Call<PokemonDetalle> call, Throwable t) {

            }
        });


    }
}