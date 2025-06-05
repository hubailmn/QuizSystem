package me.hubailmn.QuizSystem.data.database;

import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static Connection connection;

    @SneakyThrows
    public DBConnection() {
        connectToSQLite();
    }

    @SneakyThrows
    public static void close() {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }


    @SneakyThrows
    private void connectToSQLite() {
        connection = DriverManager.getConnection(initSQLite());
    }

    private String initSQLite() {
        String databasePath = "database/QuizSystem.db";
        File databaseFile = new File(databasePath);

        if (!databaseFile.exists()) {
            File parentDirectory = databaseFile.getParentFile();
            if (!parentDirectory.exists() && !parentDirectory.mkdirs()) {
                throw new RuntimeException("Failed to create the parent directory for the database file");
            }

            try {
                if (!databaseFile.createNewFile()) {
                    throw new RuntimeException("Failed to create the database file");
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to create the database file", e);
            }
        }

        return "jdbc:sqlite:" + databaseFile.getAbsolutePath();
    }

    @SneakyThrows
    public static Connection getConnection() {
        if (connection == null || connection.isClosed()) {
            new DBConnection().connectToSQLite();
        }
        return connection;
    }
}
