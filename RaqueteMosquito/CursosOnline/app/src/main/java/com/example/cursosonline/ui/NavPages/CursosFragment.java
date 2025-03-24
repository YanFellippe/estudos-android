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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CursosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CursosFragment extends Fragment {
    EditText descricao, turma, qtd_alunos, carga_horaria, data_fim, situacao;
    Button btnCadastrar;
    ListView listaCursos;
    ArrayList<String> dataList;
    ArrayAdapter<String> adapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public CursosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CursosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CursosFragment newInstance(String param1, String param2) {
        CursosFragment fragment = new CursosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cursos, container, false);

        descricao = view.findViewById(R.id.descricao);
        turma = view.findViewById(R.id.turma);
        qtd_alunos = view.findViewById(R.id.qtd_alunos);
        carga_horaria = view.findViewById(R.id.carga_horaria);
        data_fim = view.findViewById(R.id.data_fim);
        situacao = view.findViewById(R.id.situacao);
        btnCadastrar = view.findViewById(R.id.btnCadastrar);
        listaCursos = view.findViewById(R.id.listaProfessores);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String desc = descricao.getText().toString().trim();
                String turm = turma.getText().toString().trim();
                String qtd = qtd_alunos.getText().toString().trim();
                String carga = carga_horaria.getText().toString().trim();
                String data = data_fim.getText().toString().trim();
                String sit = situacao.getText().toString().trim();

                if (desc.isEmpty() || turm.isEmpty() || qtd.isEmpty() || carga.isEmpty() || data.isEmpty() || sit.isEmpty()) {
                    Toast.makeText(getContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(getContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                }

                try {
                    Connection con = ConexaoMysql.conectar();
                    String sql = "INSERT INTO curso(descricao, turma, qtd_alunos, carga_horaria, data_fim, situacao) VALUES(?,?,?,?,?,?);";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    stmt.setString(1, desc);
                    stmt.setString(2, turm);
                    stmt.setString(3, qtd);
                    stmt.setString(4, carga);
                    stmt.setString(5, data);
                    stmt.setString(6, sit);
                    stmt.execute();

                    String select = "SELECT * FROM curso;";
                    ResultSet rs = stmt.executeQuery(select);

                    while(rs.next()){
                        dataList = new ArrayList<>();
                        dataList.add("Nome do Curso: " + desc + "\n" +
                                "Turma: " + turm + "\n" +
                                "Quanntidade de Alunos: " + qtd + "\n" +
                                "Carga Horária: " + carga + "\n" +
                                "Previsão de término: " + data + "\n" +
                                "Situação: " + sit
                        );

                        dataList = new ArrayList<>();
                        listaCursos.setAdapter(adapter);
                    }
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