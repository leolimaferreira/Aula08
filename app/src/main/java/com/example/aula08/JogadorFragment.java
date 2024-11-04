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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aula08.controller.JogadorController;
import com.example.aula08.controller.JogadorDao;
import com.example.aula08.controller.TimeController;
import com.example.aula08.controller.TimeDao;
import com.example.aula08.model.Jogador;
import com.example.aula08.model.Time;

import java.sql.SQLException;
import java.util.List;

public class JogadorFragment extends Fragment {

    private View view;
    private EditText etIdJogador, etNomeJogador, etDataJogador, etAlturaJogador, etPesoJogador;
    private Spinner spTimes;
    private Button btnBuscarJogador, btnInserirJogador, btnListarJogador, btnExcluirJogador, btnModificarJogador;
    private TextView tvListarJogador;
    private JogadorController jogadorController;
    private TimeController timeController;
    private List<Time> times;

    public JogadorFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_jogador, container, false);
        etIdJogador = view.findViewById(R.id.etIdJogador);
        etNomeJogador = view.findViewById(R.id.etNomeJogador);
        etDataJogador = view.findViewById(R.id.etDataJogador);
        etAlturaJogador = view.findViewById(R.id.etAlturaJogador);
        etPesoJogador = view.findViewById(R.id.etPesoJogador);
        spTimes = view.findViewById(R.id.spTimes);
        btnBuscarJogador = view.findViewById(R.id.btnBuscarJogador);
        btnInserirJogador = view.findViewById(R.id.btnInserirJogador);
        btnListarJogador = view.findViewById(R.id.btnListarJogador);
        btnModificarJogador = view.findViewById(R.id.btnModificarJogador);
        btnExcluirJogador = view.findViewById(R.id.btnExcluirJogador);
        tvListarJogador = view.findViewById(R.id.tvListarJogador);
        tvListarJogador.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tvListarJogador.setMovementMethod(new ScrollingMovementMethod());

        jogadorController = new JogadorController(new JogadorDao(view.getContext()));
        timeController = new TimeController(new TimeDao(view.getContext()));
        preencheSpinner();

        btnInserirJogador.setOnClickListener(op -> acaoInserir());
        btnModificarJogador.setOnClickListener(op -> acaoModificar());
        btnListarJogador.setOnClickListener(op -> acaoListar());
        btnBuscarJogador.setOnClickListener(op -> acaoBuscar());
        btnExcluirJogador.setOnClickListener(op -> acaoExcluir());

        return view;
    }

    private void acaoExcluir() {
        Jogador jogador = montaJogador();
        try {
            jogadorController.excluir(jogador);
            Toast.makeText(view.getContext(), "Jogador Excluido com Sucesso", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }


    private void acaoBuscar() {
        Jogador jogador = montaJogador();
        try {
            Jogador jogadorEncontrado = jogadorController.buscar(jogador);
            if(jogadorEncontrado.getNome() != null) {
                preencheCampos(jogadorEncontrado);
            } else {
                Toast.makeText(view.getContext(), "Jogador Nao Encontrado", Toast.LENGTH_LONG).show();
                limpaCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoListar() {
        try {
            List<Jogador> jogadores = jogadorController.listar();
            StringBuffer stringBuffer = new StringBuffer();
            for(Jogador j : jogadores) {
                stringBuffer.append(j.toString() + "\n");
            }
            tvListarJogador.setText(stringBuffer.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoModificar() {
        int spPos = spTimes.getSelectedItemPosition();
        if(spPos > 0) {
            Jogador jogador = montaJogador();
            try {
                jogadorController.modificar(jogador);
                Toast.makeText(view.getContext(), "Jogador Modificado com Sucesso", Toast.LENGTH_LONG).show();
                limpaCampos();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(view.getContext(), "Selecione um Time", Toast.LENGTH_LONG).show();
        }
    }

    private void acaoInserir() {
        int spPos = spTimes.getSelectedItemPosition();
        if(spPos > 0) {
            Jogador jogador = montaJogador();
            try {
                jogadorController.inserir(jogador);
                Toast.makeText(view.getContext(), "Jogador Inserido com Sucesso", Toast.LENGTH_LONG).show();
                limpaCampos();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(view.getContext(), "Selecione um Time", Toast.LENGTH_LONG).show();
        }
    }

    private void preencheSpinner() {
        Time t0 = new Time();
        t0.setCodigo(0);
        t0.setNome("Selecione um Time");
        t0.setCidade("");

        try {
            times = timeController.listar();
            times.add(0, t0);

            ArrayAdapter ad = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, times);
            ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spTimes.setAdapter(ad);
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public Jogador montaJogador() {
        Jogador jogador = new Jogador();
        jogador.setId(Integer.parseInt(etIdJogador.getText().toString()));
        jogador.setNome(etNomeJogador.getText().toString());
        jogador.setDataNasc(etDataJogador.getText().toString());
        jogador.setAltura(Float.parseFloat(etAlturaJogador.getText().toString()));
        jogador.setPeso(Float.parseFloat(etPesoJogador.getText().toString()));
        jogador.setTime((Time) spTimes.getSelectedItem());

        return jogador;
    }

    public void limpaCampos() {
        etIdJogador.setText("");
        etNomeJogador.setText("");
        etDataJogador.setText("");
        etAlturaJogador.setText("");
        etPesoJogador.setText("");
        spTimes.setSelection(0);
    }

    public void preencheCampos(Jogador jogador) {
        etIdJogador.setText(String.valueOf(jogador.getId()));
        etNomeJogador.setText(jogador.getNome());
        etDataJogador.setText(jogador.getDataNasc());
        etAlturaJogador.setText(String.valueOf(jogador.getAltura()));
        etPesoJogador.setText(String.valueOf(jogador.getPeso()));

        int cont = 1;
        for(Time t : times) {
            if(t.getCodigo() == jogador.getTime().getCodigo()) {
                spTimes.setSelection(cont);
            } else {
                cont++;
            }
        }
        if(cont > times.size()) {
            spTimes.setSelection(0);
        }
    }
}