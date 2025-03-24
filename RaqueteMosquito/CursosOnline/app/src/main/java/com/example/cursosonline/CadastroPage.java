package com.example.cursosonline;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CadastroPage extends AppCompatActivity {
    EditText email, senha, nome;
    Button btCadastrar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        email = findViewById(R.id.email);
        senha = findViewById(R.id.senha);
        nome = findViewById(R.id.nomeProfessor);
        btCadastrar = findViewById(R.id.btCadastrar);

        btCadastrar.setOnClickListener(v -> {
            try {
                Connection con = ConexaoMysql.conectar();
                String sql = "INSERT INTO cadastro(nome, email, senha) VALUES (?,?,?)";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, nome.getText().toString());
                stmt.setString(2, email.getText().toString());
                stmt.setString(3, senha.getText().toString());
                stmt.execute();
                stmt.close();
                con.close();

                String nomeStr = nome.getText().toString();
                String emailStr = email.getText().toString();
                String senhaStr = senha.getText().toString();
                if (nomeStr.isEmpty() || emailStr.isEmpty() || senhaStr.isEmpty()) {
                    Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(this, "Cadastro concluído!", Toast.LENGTH_SHORT).show();
                    Intent telaLogin = new Intent(CadastroPage.this,MainActivity.class);
                    startActivity(telaLogin);
                    finish();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}