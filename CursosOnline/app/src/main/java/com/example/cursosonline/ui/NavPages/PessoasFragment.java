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
 * Use the {@link PessoasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PessoasFragment extends Fragment {

    EditText cpf_cnpj, email, idade, telefone, endereco, situacao;
    Button btCadastrar;
    ListView listaPessoas;
    ArrayList<String> dataList;
    ArrayAdapter<String> adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PessoasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PessoasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PessoasFragment newInstance(String param1, String param2) {
        PessoasFragment fragment = new PessoasFragment();
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
        View view = inflater.inflate(R.layout.fragment_pessoas, container, false);

        cpf_cnpj = view.findViewById(R.id.cpf_cnpj);
        email = view.findViewById(R.id.email);
        idade = view.findViewById(R.id.idade);
        telefone = view.findViewById(R.id.telefone);
        endereco = view.findViewById(R.id.endereco);
        situacao = view.findViewById(R.id.situacao);
        btCadastrar = view.findViewById(R.id.btCadastrar);
        listaPessoas = view.findViewById(R.id.listaPessoas);

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cpfCnpj = cpf_cnpj.getText().toString();
                String userEmail = email.getText().toString();
                String userIdade = idade.getText().toString();
                String userTelefone = telefone.getText().toString();
                String userEndereco = endereco.getText().toString();
                String userSituacao = situacao.getText().toString();

                if (cpfCnpj.isEmpty() || userEmail.isEmpty() || userIdade.isEmpty() || userTelefone.isEmpty() || userEndereco.isEmpty() || userSituacao.isEmpty()) {
                    Toast.makeText(getContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Connection con = null;
                PreparedStatement stmt = null;

                try {
                    con = ConexaoMysql.conectar();
                    String sql = "INSERT INTO pessoa (cpf_cnpj, email, idade, telefone, endereco, situacao) VALUES (?, ?, ?, ?, ?, ?)";
                    stmt = con.prepareStatement(sql);
                    stmt.setString(1, cpfCnpj);
                    stmt.setString(2, userEmail);
                    stmt.setString(3, userIdade);
                    stmt.setString(4, userTelefone);
                    stmt.setString(5, userEndereco);
                    stmt.setString(6, userSituacao);
                    stmt.execute();
                    int rowsAffected = stmt.executeUpdate();

                    if (rowsAffected > 0) {
                        Toast.makeText(getContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                        dataList = new ArrayList<>();
                        dataList.add("Documento: " + cpfCnpj + "\n" +
                                "Email: " + userEmail + "\n" +
                                "Idade: " + userIdade + "\n" +
                                "Telefone: " + userTelefone + "\n" +
                                "Endereço: " + userEndereco + "\n" +
                                "Situação: " + userSituacao
                        );

                        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, dataList);
                        listaPessoas.setAdapter(adapter);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Erro ao cadastrar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                } finally {
                    try {
                        if (stmt != null) stmt.close();
                        if (con != null) con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return view;
    }
}