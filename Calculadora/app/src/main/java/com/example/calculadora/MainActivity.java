package com.example.calculadora;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnSoma, btnSubtracao, btnMultiplicacao, btnDivisao;
    TextView idResultado;
    EditText num1, num2;

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

        btnSoma = findViewById(R.id.btnSoma);
        btnSubtracao = findViewById(R.id.btnSubtracao);
        btnMultiplicacao = findViewById(R.id.btnMultiplicacao);
        btnDivisao = findViewById(R.id.btnDivisao);
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        idResultado = findViewById(R.id.idResultado);

        btnSoma.setOnClickListener(v -> calcular('+'));
        btnSubtracao.setOnClickListener(v -> calcular('-'));
        btnMultiplicacao.setOnClickListener(v -> calcular('*'));
        btnDivisao.setOnClickListener(v -> calcular('/'));



    }

    private void calcular(char operador){
        try {
            double n1 = Double.parseDouble(num1.getText().toString());
            double n2 = Double.parseDouble(num2.getText().toString());
            double resultado = 0;

            switch (operador) {
                case '+':
                    resultado = n1 +n2;
                    break;
                case '-':
                    resultado = n1 - n2;
                    break;

                case '*':
                    resultado = n1 * n2;
                    break;

                case '/':
                    if (n2 != 0) resultado = n1 / n2;
                    else {
                        idResultado.setText("Erro: Divisão por zero");
                        return;
                    }
                    break;
            }
            idResultado.setText(""+resultado);
        } catch (NumberFormatException e) {
            idResultado.setText("Erro: Insira números válidos");
        }

    }
}