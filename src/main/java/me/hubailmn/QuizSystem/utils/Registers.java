package me.hubailmn.QuizSystem.utils;

import me.hubailmn.QuizSystem.utils.classes.DBTable;
import org.reflections.Reflections;

import java.util.Set;

public class Registers {

    public static void registerDatabaseTables() {
        Reflections reflections = new Reflections("me.hubailmn.QuizSystem.data.database.tables");
        Set<Class<? extends DBTable>> databaseTable = reflections.getSubTypesOf(DBTable.class);

        for (Class<? extends DBTable> clazz : databaseTable) {
            try {
                clazz.getDeclaredConstructor().newInstance();
            } catch (Exception ignored) {
            }
        }
    }
}
