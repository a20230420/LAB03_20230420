package com.example.lab03_20230420;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab03_20230420.databinding.ActivityDetallePeliculaBinding;
import com.example.lab03_20230420.dto.Movie;
import com.example.lab03_20230420.services.OmdbService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetallePeliculaActivity extends AppCompatActivity {

    private ActivityDetallePeliculaBinding binding;
    // API KEY proporcionada en el enunciado
    private final String API_KEY = "bf81d461";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetallePeliculaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String idPelicula = getIntent().getStringExtra("ID_PELICULA");

        if (idPelicula != null && !idPelicula.isEmpty()) {
            buscarPelicula(idPelicula);
        }

        binding.btnRegresarDetalle.setOnClickListener(v -> mostrarDialogoRegresar());
    }

    private void buscarPelicula(String imdbId) {
        OmdbService service = new Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OmdbService.class);

        service.getMovieById(API_KEY, imdbId).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Movie movie = response.body();
                    binding.tvTituloPelicula.setText(movie.getTitle());
                    binding.tvAnioPelicula.setText(movie.getYear());
                } else {
                    Toast.makeText(DetallePeliculaActivity.this, "Error al obtener datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(DetallePeliculaActivity.this, "Fallo de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarDialogoRegresar() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Confirmación")
                .setMessage("¿Desea volver al menú principal?")
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    finish();
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }
}