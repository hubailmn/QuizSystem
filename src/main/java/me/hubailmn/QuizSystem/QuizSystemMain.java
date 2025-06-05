package me.hubailmn.QuizSystem;

import me.hubailmn.QuizSystem.data.database.DBConnection;
import me.hubailmn.QuizSystem.data.database.tables.QuizzesTable;
import me.hubailmn.QuizSystem.data.managers.Quiz;
import me.hubailmn.QuizSystem.utils.CC;
import me.hubailmn.QuizSystem.utils.QuizzesUtil;
import me.hubailmn.QuizSystem.utils.Registers;

import java.util.Scanner;

public class QuizSystemMain {

    public static void main(String[] args) {

        System.out.println("Loading Quiz System");

        new DBConnection();
        Registers.registerDatabaseTables();

        Scanner scanner = new Scanner(System.in);

        System.out.println("""
                Menu:
                    1. Create new Quiz
                    2. Solve a Quiz
                """);

        System.out.print(CC.BRIGHT_WHITE + "Enter your choice: " + CC.RESET);
        int option = scanner.nextInt();

        switch (option) {
            case 1:
                QuizzesUtil.createQuiz();
                break;
            case 2:
                System.out.print("Enter Quiz ID: ");
                int quizId = scanner.nextInt();

                Quiz quiz = QuizzesTable.getQuiz(quizId);

                if (quiz == null) {
                    System.out.println("Quiz not found");
                    return;
                }

                quiz.startQuiz();
                break;
        }
    }


}
