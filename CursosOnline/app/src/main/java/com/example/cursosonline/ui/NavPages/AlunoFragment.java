package com.example.cursosonline.ui.NavPages;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cursosonline.ConexaoMysql;
import com.example.cursosonline.R;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AlunoFragment extends Fragment {

    EditText nome, matricula, situacao, id_pessoa, id_curso;
    Button btnCadastro;
    ListView listaAlunos;
    ArrayList<String> dataList;
    ArrayAdapter<String> adapter;

    public AlunoFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aluno, container, false);

        nome = view.findViewById(R.id.nomeProfessor);
        matricula = view.findViewById(R.id.matriculaProfessor);
        situacao = view.findViewById(R.id.situacao);
        id_pessoa = view.findViewById(R.id.id_pessoa);
        id_curso = view.findViewById(R.id.id_curso);
        btnCadastro = view.findViewById(R.id.btnCadastro);
        listaAlunos = view.findViewById(R.id.listaProfessores);

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeAluno = nome.getText().toString().trim();
                String mat = matricula.getText().toString().trim();
                String sit = situacao.getText().toString().trim();
                String idPessoa = id_pessoa.getText().toString().trim();
                String idCurso = id_curso.getText().toString().trim();

                if (nomeAluno.isEmpty() || mat.isEmpty() || sit.isEmpty() || idPessoa.isEmpty() || idCurso.isEmpty()) {
                    Toast.makeText(getContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(getContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                }

                try {
                    Connection con = ConexaoMysql.conectar();
                    String sql = "INSERT INTO aluno(nome, matricula, situacao, id_pessoa, id_curso) VALUES(?,?,?,?,?);";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    stmt.setString(1, nomeAluno);
                    stmt.setString(2, mat);
                    stmt.setString(3, sit);
                    stmt.setInt(4, Integer.parseInt(idPessoa));
                    stmt.setInt(5, Integer.parseInt(idCurso));
                    stmt.execute();

                    String select = "SELECT * FROM aluno;";
                    ResultSet rs = stmt.executeQuery(select);

                    dataList = new ArrayList<>();
                    while(rs.next()){
                        dataList.add("Nome: " + rs.getString("nome") + "\n" +
                                "Matrícula: " + rs.getString("matricula") + "\n" +
                                "Situação: " + rs.getString("situacao") + "\n" +
                                "ID Pessoa: " + rs.getInt("id_pessoa") + "\n" +
                                "ID Curso: " + rs.getInt("id_curso")
                        );
                    }

                    adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataList);
                    listaAlunos.setAdapter(adapter);

                    con.close();
                    stmt.close();
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return view;
    }
}
