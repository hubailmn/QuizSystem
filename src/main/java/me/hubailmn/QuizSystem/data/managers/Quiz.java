package me.hubailmn.QuizSystem.data.managers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import me.hubailmn.QuizSystem.data.database.DBConnection;
import me.hubailmn.QuizSystem.data.database.tables.QuestionsTable;
import me.hubailmn.QuizSystem.utils.CC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quiz {

    int quizID;
    String name;
    String description;
    List<Question> questions;

    public void startQuiz() {
        Scanner scanner = new Scanner(System.in);
        int score = 0;

        System.out.println("Starting Quiz: " + CC.BRIGHT_YELLOW + getName() + CC.RESET);
        System.out.println("description: " + CC.BRIGHT_YELLOW + getDescription() + CC.RESET);
        System.out.println("Number of questions: " + CC.BRIGHT_YELLOW + getQuestions().size() + CC.RESET);
        System.out.println("----------------------------------");

        //Collections.shuffle(questions);

        int questionNumber = 1;

        for (Question question : questions) {
            System.out.println(CC.BRIGHT_BLUE + questionNumber + ": " + question.getQuestion() + CC.RESET);

            for (int i = 0; i < question.getAnswers().size(); i++) {
                System.out.println(CC.BRIGHT_GREEN + (i + 1) + ") " + CC.RESET + question.getAnswers().get(i));
            }

            int userAnswerIndex = -1;
            boolean validInput = false;

            while (!validInput) {
                System.out.print(CC.BRIGHT_WHITE + "Enter your answer: " + CC.RESET);

                if (!scanner.hasNextInt()) {
                    System.out.println(CC.BRIGHT_RED + "Invalid input! Please enter a number." + CC.RESET);
                    scanner.next();
                    continue;
                }

                userAnswerIndex = scanner.nextInt() - 1;

                if (userAnswerIndex < 0 || userAnswerIndex >= question.getAnswers().size()) {
                    System.out.println(CC.BRIGHT_RED + "Invalid choice! Please enter a number between 1 and " + question.getAnswers().size() + "." + CC.RESET);
                    continue;
                }

                validInput = true;
            }


            if (question.getAnswers().get(userAnswerIndex).strip().equalsIgnoreCase(question.getCorrectAnswer().strip())) {
                System.out.println(CC.BRIGHT_GREEN + "Correct! ✅\n" + CC.RESET);
                score++;
            } else {
                System.out.println(CC.BRIGHT_RED + "Incorrect! ❌ The correct answer is: " + question.getCorrectAnswer() + "\n" + CC.RESET);
            }


            questionNumber++;
        }

        System.out.println("----------------------------------");
        System.out.println(CC.BRIGHT_BLUE + "Quiz finished! Your score: " + score + "/" + questions.size() + CC.RESET);
    }


    @SneakyThrows
    public void save() {
        String query = "INSERT INTO `Quizzes` (name, description) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    quizID = generatedKeys.getInt(1);
                }
            }
        }

        for (Question question : questions) {
            question.setQuizID(quizID);
            QuestionsTable.insertQuestion(question.getQuizID(), question.getQuestion(), String.join(",", question.getAnswers()), question.getCorrectAnswer());
        }
    }

}
