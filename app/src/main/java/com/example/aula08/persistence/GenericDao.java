package com.example.aula08.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GenericDao extends SQLiteOpenHelper {

    private static final String DATABASE = "TIMES.DB";
    private static final int DATABASE_VER = 1;
    private static final String CREATE_TABLE_TIME =
            "CREATE TABLE time ( " +
                    "codigo INT NOT NULL PRIMARY KEY, " +
                    "nome VARCHAR(20) NOT NULL," +
                    "cidade VARCHAR(20) NOT NULL);";
    private static final String CREATE_TABLE_JOGADOR =
            "CREATE TABLE jogador ( " +
                    "id INT NOT NULL PRIMARY KEY, " +
                    "nome VARCHAR(20) NOT NULL," +
                    "dataNasc VARCHAR(10) NOT NULL," +
                    "altura REAL NOT NULL, " +
                    "peso REAL NOT NULL, " +
                    "cod_time INT NOT NULL, " +
                    "FOREIGN KEY (cod_time) REFERENCES time(codigo));";

    public GenericDao(Context context) {
        super(context, DATABASE, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_TIME);
        sqLiteDatabase.execSQL(CREATE_TABLE_JOGADOR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int antigaVersao, int novaVersao) {
        if(novaVersao > antigaVersao) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS time");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS jogador");
            onCreate(sqLiteDatabase);
        }
    }
}
