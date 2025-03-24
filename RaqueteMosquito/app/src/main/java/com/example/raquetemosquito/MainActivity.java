package com.example.raquetemosquito;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button ligar, usar, carregar;
    MultiAutoCompleteTextView txtResultados;

    private boolean ligado = false;
    private boolean raquetada = false;
    private int bateria = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ligar = findViewById(R.id.ligar);
        usar = findViewById(R.id.usar);
        carregar = findViewById(R.id.carregar);
        txtResultados = findViewById(R.id.txtResultados);

        attSituacao();

        ligar.setOnClickListener(v -> {
            if (bateria > 0){
                ligado = true;
                attSituacao();
            }
        });

        carregar.setOnClickListener(v -> {
            if (bateria <= 100){
                bateria = Math.min(100, bateria + 10);
                attSituacao();
            }
        });

        usar.setOnClickListener(v -> {
            if (bateria > 0 && ligado){
                raquetada = true;
                bateria = Math.max(0, bateria - 10);
                attSituacao();
            } else if(bateria == 0){
                ligado = false;
                raquetada = false;
                attSituacao();
            }
        });

    }

    private void attSituacao(){
        String status = "Raquete: " + (ligado ? "Ligada" : "Desligada") + "\n"
        + "Bateria: " + bateria + "%" + "\n"
        + "Usou raquete: " + (raquetada ? "Usou" : "Não usou");
        txtResultados.setText(status);
    }
}