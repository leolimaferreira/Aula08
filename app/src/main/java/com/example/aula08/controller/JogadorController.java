/*
 *@author:<Leonardo Lima 1110482423021>
 */
package com.example.aula08.controller;

import com.example.aula08.model.Jogador;

import java.sql.SQLException;
import java.util.List;

public class JogadorController implements IController<Jogador> {

    private final JogadorDao jDao;

    public JogadorController(JogadorDao jDao) {
        this.jDao = jDao;
    }

    @Override
    public void inserir(Jogador jogador) throws SQLException {
        if(jDao.open() == null) {
            jDao.open();
        }
        jDao.insert(jogador);
        jDao.close();
    }

    @Override
    public void modificar(Jogador jogador) throws SQLException {
        if(jDao.open() == null) {
            jDao.open();
        }
        jDao.update(jogador);
        jDao.close();
    }

    @Override
    public void excluir(Jogador jogador) throws SQLException {
        if(jDao.open() == null) {
            jDao.open();
        }
        jDao.delete(jogador);
        jDao.close();
    }

    @Override
    public Jogador buscar(Jogador jogador) throws SQLException {
        if(jDao.open() == null) {
            jDao.open();
        }
        return jDao.findOne(jogador);
    }

    @Override
    public List<Jogador> listar() throws SQLException {
        if(jDao.open() == null) {
            jDao.open();
        }
        return jDao.findAll();
    }
}
