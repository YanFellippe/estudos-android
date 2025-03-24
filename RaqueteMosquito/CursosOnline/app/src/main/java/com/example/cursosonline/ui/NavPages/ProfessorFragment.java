package com.example.cursosonline.ui.NavPages;

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

public class ProfessorFragment extends Fragment {

    EditText nomeProfessor, matriculaProfessor, situacaoProfessor, id_pessoaP, id_cursoP;
    Button btnCadastro;
    ListView listaProfessores;
    ArrayList<String> dataList;
    ArrayAdapter<String> adapter;

    public ProfessorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_professor, container, false);

        nomeProfessor = view.findViewById(R.id.nomeProfessor);
        matriculaProfessor = view.findViewById(R.id.matriculaProfessor);
        situacaoProfessor = view.findViewById(R.id.situacaoProfessor);
        id_pessoaP = view.findViewById(R.id.id_pessoaP);
        id_cursoP = view.findViewById(R.id.id_cursoP);
        btnCadastro = view.findViewById(R.id.btnCadastro);
        listaProfessores = view.findViewById(R.id.listaProfessores);

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeProf = nomeProfessor.getText().toString().trim();
                String matProf = matriculaProfessor.getText().toString().trim();
                String sitProf = situacaoProfessor.getText().toString().trim();
                String idPessoaProf = id_pessoaP.getText().toString().trim();
                String idCursoProf = id_cursoP.getText().toString().trim();

                if (nomeProf.isEmpty() || matProf.isEmpty() || sitProf.isEmpty() || idPessoaProf.isEmpty() || idCursoProf.isEmpty()) {
                    Toast.makeText(getContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(getContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                }

                try {
                    Connection con = ConexaoMysql.conectar();
                    String sql = "INSERT INTO professor(nome, matricula, situacao, id_pessoa, id_curso) VALUES(?,?,?,?,?);";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    stmt.setString(1, nomeProf);
                    stmt.setString(2, matProf);
                    stmt.setString(3, sitProf);
                    stmt.setInt(4, Integer.parseInt(idPessoaProf));
                    stmt.setInt(5, Integer.parseInt(idCursoProf));
                    stmt.execute();

                    String select = "SELECT * FROM professor;";
                    ResultSet rs = stmt.executeQuery(select);

                    dataList = new ArrayList<>();
                    while (rs.next()) {
                        dataList.add("Nome: " + rs.getString("nome") + "\n" +
                                "Matrícula: " + rs.getString("matricula") + "\n" +
                                "Situação: " + rs.getString("situacao") + "\n" +
                                "ID Pessoa: " + rs.getInt("id_pessoa") + "\n" +
                                "ID Curso: " + rs.getInt("id_curso")
                        );
                    }

                    adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataList);
                    listaProfessores.setAdapter(adapter);

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
