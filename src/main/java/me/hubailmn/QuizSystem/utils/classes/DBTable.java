package me.hubailmn.QuizSystem.utils.classes;

import lombok.Getter;
import me.hubailmn.QuizSystem.data.database.DBConnection;

import java.sql.Connection;


@Getter
public abstract class DBTable {

    Connection connection;
    String name;

    public DBTable(String name) {
        this.name = name;
        this.connection = DBConnection.getConnection();
        initTable();
    }

    public abstract void initTable();

}
