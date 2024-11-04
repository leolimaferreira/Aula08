/*
 *@author:<Leonardo Lima 1110482423021>
 */
package com.example.aula08;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aula08.controller.TimeController;
import com.example.aula08.controller.TimeDao;
import com.example.aula08.model.Time;

import java.sql.SQLException;
import java.util.List;

public class TimeFragment extends Fragment {

    private View view;
    private EditText etCodTime;
    private EditText etNomeTime;
    private EditText etCidadeTime;
    private Button btnBuscarTime, btnInserirTime, btnListarTime, btnExcluirTime, btnModificarTime;
    private TextView tvListarTime;
    private TimeController timeController;

    public TimeFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_time, container, false);
        etCodTime = view.findViewById(R.id.etCodTime);
        etNomeTime = view.findViewById(R.id.etNomeTime);
        etCidadeTime = view.findViewById(R.id.etCidadeTime);
        btnBuscarTime = view.findViewById(R.id.btnBuscarTime);
        btnInserirTime = view.findViewById(R.id.btnInserirTime);
        btnListarTime = view.findViewById(R.id.btnListarTime);
        btnModificarTime = view.findViewById(R.id.btnModificarTime);
        btnExcluirTime = view.findViewById(R.id.btnExcluirTime);
        tvListarTime = view.findViewById(R.id.tvListarTime);
        tvListarTime.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tvListarTime.setMovementMethod(new ScrollingMovementMethod());

        timeController = new TimeController(new TimeDao(view.getContext()));
        
        btnInserirTime.setOnClickListener(op -> acaoInserir());
        btnModificarTime.setOnClickListener(op -> acaoModificar());
        btnListarTime.setOnClickListener(op -> acaoListar());
        btnBuscarTime.setOnClickListener(op -> acaoBuscar());
        btnExcluirTime.setOnClickListener(op -> acaoExcluir());
        
        return view;
    }

    private void acaoExcluir() {
        Time time = montaTime();
        try {
            timeController.excluir(time);
            Toast.makeText(view.getContext(), "Time Excluido com Sucesso", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoBuscar() {
        Time time = montaTime();
        try {
            Time timeEncontrado = timeController.buscar(time);
            if(timeEncontrado.getNome() != null) {
                preencheCampos(timeEncontrado);
            } else {
                Toast.makeText(view.getContext(), "Time Não Encontrado", Toast.LENGTH_LONG).show();
                limpaCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoListar() {
        try {
            List<Time> times = timeController.listar();
            StringBuffer stringBuffer = new StringBuffer();
            for(Time t : times) {
                stringBuffer.append(t.toString() + "\n");
            }
            tvListarTime.setText(stringBuffer.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoModificar() {
        Time time = montaTime();
        try {
            timeController.modificar(time);
            Toast.makeText(view.getContext(), "Time Modificado com Sucesso", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoInserir() {
        Time time = montaTime();
        try {
            timeController.inserir(time);
            Toast.makeText(view.getContext(), "Time Inserido com Sucesso", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private Time montaTime() {
        Time time = new Time();
        time.setCodigo(Integer.parseInt(etCodTime.getText().toString()));
        time.setNome(etNomeTime.getText().toString());
        time.setCidade(etCidadeTime.getText().toString());

        return time;
    }

    private void preencheCampos(Time time) {
        etCodTime.setText(String.valueOf(time.getCodigo()));
        etNomeTime.setText(time.getNome());
        etCidadeTime.setText(time.getCidade());
    }

    private void limpaCampos() {
        etCodTime.setText("");
        etNomeTime.setText("");
        etCidadeTime.setText("");
    }
}