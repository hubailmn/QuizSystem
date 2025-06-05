package me.hubailmn.QuizSystem.data.database.tables;

import lombok.SneakyThrows;
import me.hubailmn.QuizSystem.data.database.DBConnection;
import me.hubailmn.QuizSystem.data.managers.Question;
import me.hubailmn.QuizSystem.utils.classes.DBTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuestionsTable extends DBTable {

    public QuestionsTable() {
        super("Questions");
    }

    @SneakyThrows
    @Override
    public void initTable() {
        String query = "CREATE TABLE IF NOT EXISTS `" + getName() + "` (" +
                "questionID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "quizID INTEGER NOT NULL," +
                "question TEXT NOT NULL," +
                "answers TEXT NOt NULL," +
                "correctAnswer TEXT NOT NULL," +
                "FOREIGN KEY (quizID) REFERENCES Quizzes(quizID) ON DELETE CASCADE" +
                ")";

        try (Statement statement = getConnection().createStatement()) {
            statement.execute(query);
        }
    }

    @SneakyThrows
    public static List<Question> getQuizQuestions(int quizID) {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM `Questions` WHERE quizID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, quizID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Question question = new Question();
                    question.setQuizID(rs.getInt("quizID"));
                    question.setQuestion(rs.getString("question"));
                    question.setAnswers(List.of(rs.getString("answers").split("\\|")));
                    question.setCorrectAnswer(rs.getString("correctAnswer"));

                    questions.add(question);
                }
            }
        }
        return questions;
    }

    @SneakyThrows
    public static void insertQuestion(int quizID, String question, String answers, String correctAnswer) {
        String query = "INSERT INTO `Questions` (quizID, question, answers, correctAnswer) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, quizID);
            stmt.setString(2, question);
            stmt.setString(3, answers);
            stmt.setString(4, correctAnswer);

            stmt.executeUpdate();
        }
    }

}
