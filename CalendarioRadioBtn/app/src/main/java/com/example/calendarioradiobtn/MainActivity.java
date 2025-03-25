package com.example.calendarioradiobtn;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    ImageButton btCalendario;
    TextView resultado, opcao;
    CalendarView calendario;
    String dia1, mes1, ano1;

    RadioButton rdAno, rdDia, rdMes;
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

        btCalendario = findViewById(R.id.btCalendario);
        calendario = findViewById(R.id.calendario);
        resultado = findViewById(R.id.resultado);
        opcao = findViewById(R.id.opcao);
        rdAno = findViewById(R.id.rdAno);
        rdDia = findViewById(R.id.rdDia);
        rdMes = findViewById(R.id.rdMes);

        calendario.setVisibility(GONE);

        btCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btCalendario.setVisibility(GONE);
                resultado.setVisibility(GONE);
                calendario.setVisibility(VISIBLE);
            }
        });

        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int ano, int meses, int dia) {
                int mes = meses + 1;
                resultado.setVisibility(VISIBLE);
                btCalendario.setVisibility(VISIBLE);
                calendario.setVisibility(GONE);
                dia1 = String.valueOf(dia);
                mes1 = String.valueOf(mes);
                ano1 = String.valueOf(ano);
                resultado.setText(dia+"/"+mes+"/"+ano);
            }
        });

        rdMes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opcao.setText("Opção: " + mes1);
            }
        });

        rdDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opcao.setText("Opção: " + dia1);
            }
        });

        rdAno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opcao.setText("Opção: " + ano1);
            }
        });
    }
}