package com.example.lab03_20230420;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ContadorViewModel extends ViewModel {

    // LiveData para que la UI se actualice automáticamente y sobreviva rotaciones
    private final MutableLiveData<Integer> contador = new MutableLiveData<>(0);
    private final MutableLiveData<Boolean> isCounting = new MutableLiveData<>(false);
    private ExecutorService executorService;

    public MutableLiveData<Integer> getContador() { return contador; }
    public MutableLiveData<Boolean> getIsCounting() { return isCounting; }

    public void iniciarContador() {
        // Evitar que inicie si ya está contando
        if (Boolean.TRUE.equals(isCounting.getValue())) return;

        isCounting.setValue(true);
        contador.setValue(0); // Reinicia visualmente antes de arrancar

        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
        }
        executorService = Executors.newSingleThreadExecutor();

        // Hilo de trabajo en background
        executorService.execute(() -> {
            for (int i = 1; i <= 20; i++) {
                if (Thread.currentThread().isInterrupted()) break;

                contador.postValue(i); // postValue porque estamos en background
                try {
                    Thread.sleep(1000); // Pausa de 1 segundo
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            isCounting.postValue(false); // Termina de contar
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }
}