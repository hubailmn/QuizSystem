package me.hubailmn.QuizSystem.data.database.tables;

import lombok.SneakyThrows;
import me.hubailmn.QuizSystem.data.database.DBConnection;
import me.hubailmn.QuizSystem.data.managers.Quiz;
import me.hubailmn.QuizSystem.utils.classes.DBTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class QuizzesTable extends DBTable {
    public QuizzesTable() {
        super("Quizzes");
    }

    @SneakyThrows
    @Override
    public void initTable() {
        String query = "CREATE TABLE IF NOT EXISTS `" + getName() + "` (" +
                "quizID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "description TEXT DEFAULT NULL" +
                ")";

        try (Statement statement = getConnection().createStatement()) {
            statement.execute(query);
        }
    }

    @SneakyThrows
    public static Quiz getQuiz(int quizID) {
        String query = "SELECT * FROM `Quizzes` WHERE quizID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, quizID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Quiz quiz = new Quiz();
                    quiz.setQuizID(rs.getInt("quizID"));
                    quiz.setName(rs.getString("name"));
                    quiz.setDescription(rs.getString("description"));
                    quiz.setQuestions(QuestionsTable.getQuizQuestions(quizID));
                    return quiz;
                }
            }
        }
        return null;
    }
}
