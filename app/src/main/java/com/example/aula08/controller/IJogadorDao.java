/*
 *@author:<Leonardo Lima 1110482423021>
 */
package com.example.aula08.controller;

import java.sql.SQLException;

public interface IJogadorDao {
    public JogadorDao open() throws SQLException;
    public void close();
}
