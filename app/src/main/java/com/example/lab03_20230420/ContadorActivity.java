package com.example.lab03_20230420;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.lab03_20230420.databinding.ActivityContadorBinding;

public class ContadorActivity extends AppCompatActivity {

    private ActivityContadorBinding binding;
    private ContadorViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContadorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Instanciar el ViewModel
        viewModel = new ViewModelProvider(this).get(ContadorViewModel.class);

        // Observar los cambios en el número para actualizar el TextView automáticamente
        viewModel.getContador().observe(this, valor -> {
            binding.tvNumeroContador.setText(String.valueOf(valor));
        });

        // Observar si está contando para deshabilitar el botón y evitar múltiples clicks
        viewModel.getIsCounting().observe(this, isCounting -> {
            binding.btnIniciarContador.setEnabled(!isCounting);
        });

        // Botón Iniciar
        binding.btnIniciarContador.setOnClickListener(v -> {
            viewModel.iniciarContador();
        });

        // Botón Regresar (simplemente cierra el activity y vuelve al menú)
        binding.btnRegresar.setOnClickListener(v -> {
            finish();
        });
    }
}