package com.example.cursosonline;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText email, senha;
    Button btCadastro, btLogin;

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

        email = findViewById(R.id.email);
        senha = findViewById(R.id.senha);
        btCadastro = findViewById(R.id.btnCadastrar);
        btLogin = findViewById(R.id.btLogin);

        btLogin.setOnClickListener(v -> {
            String userEmail = email.getText().toString();
            String userSenha = senha.getText().toString();
            if (!userEmail.isEmpty() && !userSenha.isEmpty()) {
                Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();

                Intent splashIntent = new Intent(MainActivity.this, SplashPage.class);
                startActivity(splashIntent);

                new Handler().postDelayed(() -> {
                    Intent homeIntent = new Intent(MainActivity.this, HomePage.class);
                    startActivity(homeIntent);
                    finish();
                }, 3000);

            } else {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            }
        });

        btCadastro.setOnClickListener(v ->{
            Intent splashIntent = new Intent(MainActivity.this, SplashPage.class);
            startActivity(splashIntent);

            new Handler().postDelayed(() -> {
                Intent homeIntent = new Intent(MainActivity.this, CadastroPage.class);
                startActivity(homeIntent);
                finish();
            }, 3000);
        });

    }
}