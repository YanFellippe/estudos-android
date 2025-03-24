package com.example.appaviao;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnLigar, btnAttSubir, btnChecklist, btnSubir, btnAttDescer, btnDescer, btnDesligar;
    TextView situacaoAviao;

    private boolean ligado = false;
    private boolean checklistFeito = false;
    private boolean autorizacaoTorre = false;
    private int altitude = 0; // Começa no solo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLigar = findViewById(R.id.btnLigar);
        btnChecklist = findViewById(R.id.btnChecklist);
        btnAttSubir = findViewById(R.id.btnAttSubir);
        btnSubir = findViewById(R.id.btnSubir);
        btnAttDescer = findViewById(R.id.btnAttDescer);
        btnDescer = findViewById(R.id.btnDescer);
        btnDesligar = findViewById(R.id.btnDesligar);
        situacaoAviao = findViewById(R.id.situacaoAviao);

        atualizarSituacao();

        btnLigar.setOnClickListener(v -> {
            ligado = true;
            atualizarSituacao();
        });

        btnChecklist.setOnClickListener(v -> {
            if (ligado) {
                checklistFeito = true;
                atualizarSituacao();
            }
        });

        btnAttSubir.setOnClickListener(v -> {
            if (ligado && checklistFeito) {
                autorizacaoTorre = true;
                atualizarSituacao();
            }
        });

        btnSubir.setOnClickListener(v -> {
            if (ligado && checklistFeito && autorizacaoTorre) {
                if (altitude < 40000) {
                    altitude += 10000;
                    atualizarSituacao();
                }
            }
        });

        btnAttDescer.setOnClickListener(v -> {
            if (altitude == 40000) {
                autorizacaoTorre = true;
                atualizarSituacao();
            }
        });

        btnDescer.setOnClickListener(v -> {
            if (altitude > 0) {
                altitude -= 10000;
                atualizarSituacao();
            }
        });

        btnDesligar.setOnClickListener(v -> {
            if (altitude == 0) {
                ligado = false;
                checklistFeito = false;
                autorizacaoTorre = false;
                atualizarSituacao();
            }
        });
    }

    private void atualizarSituacao() {
        String status = "Aeronave " + (ligado ? "Ligada" : "Desligada") + "\n";
        status += checklistFeito ? "✔️ Checklist Feito\n" : "❌ Checklist Pendente\n";
        status += autorizacaoTorre ? "✔️ Autorização da Torre OK\n" : "❌ Sem Autorização\n";
        status += "Altura Atual: " + altitude + " pés";
        situacaoAviao.setText(status);
    }
}