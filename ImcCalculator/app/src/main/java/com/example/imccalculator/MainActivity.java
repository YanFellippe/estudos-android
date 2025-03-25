package com.example.imccalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText nome, idade, peso, altura;
    Button btnCalcular;
    TextView resultado;

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

        nome = findViewById(R.id.nome);
        idade = findViewById(R.id.idade);
        peso = findViewById(R.id.peso);
        altura = findViewById(R.id.altura);
        btnCalcular = findViewById(R.id.btnCalcular);
        resultado = findViewById(R.id.resultado);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double pesoo = Double.parseDouble(peso.getText().toString());
                    double alturaa = Double.parseDouble(altura.getText().toString());

                    if (alturaa == 0) {
                        resultado.setText("Erro: A altura não pode ser zero.");
                        return;
                    }

                    double calculo = pesoo / (alturaa * alturaa);
                    resultado.setText(nome.getText().toString() + ", você tem " + idade.getText().toString() +
                            " anos e o cálculo do seu IMC é: " + String.format("%.2f", calculo));

                } catch (NumberFormatException e) {
                    resultado.setText("Erro: Certifique-se de preencher todos os campos corretamente.");
                }

            }
        });

    }
}