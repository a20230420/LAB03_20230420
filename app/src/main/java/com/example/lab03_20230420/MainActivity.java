package com.example.lab03_20230420;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab03_20230420.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. Navegación al Contador
        binding.btnIrContador.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ContadorActivity.class);
            startActivity(intent);
        });

        // 2. Comprobar Conexión a Internet
        binding.btnComprobarConexion.setOnClickListener(v -> {
            if (tengoInternet()) {
                Toast.makeText(this, "Success Toast: Tienes conexión a internet", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error Toast: No hay conexión a internet", Toast.LENGTH_LONG).show();
            }
        });

        // 3. Buscar Película y pasar ID
        binding.btnBuscar.setOnClickListener(v -> {
            String idPelicula = binding.etIdPelicula.getText().toString().trim();
            if (idPelicula.isEmpty()) {
                Toast.makeText(this, "Por favor ingrese un ID (ej. tt3896198)", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(MainActivity.this, DetallePeliculaActivity.class);
            intent.putExtra("ID_PELICULA", idPelicula);
            startActivity(intent);
        });
    }

    // --- MÉTODOS DE VERIFICACIÓN DE RED---
    public boolean tengoConexion() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean tieneConexion = false;
        if (cm != null) {
            NetworkCapabilities cap = cm.getNetworkCapabilities(cm.getActiveNetwork());
            if (cap != null) {
                if (cap.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) tieneConexion = true;
                else if (cap.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) tieneConexion = true;
                else if (cap.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) tieneConexion = true;
            }
        }
        return tieneConexion;
    }

    public boolean tengoInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return false;
        NetworkCapabilities cap = cm.getNetworkCapabilities(cm.getActiveNetwork());
        if (cap == null) return false;
        return cap.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                && cap.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
    }
}