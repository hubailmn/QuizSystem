package me.hubailmn.QuizSystem.utils;

import me.hubailmn.QuizSystem.data.managers.Question;
import me.hubailmn.QuizSystem.data.managers.Quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuizzesUtil {

    public static void createQuiz() {
        Scanner scanner = new Scanner(System.in);
        Quiz quiz = new Quiz();

        System.out.print("Enter quiz name: ");
        String name = scanner.nextLine();
        quiz.setName(name);

        System.out.print("Enter quiz description: ");
        String description = scanner.nextLine();
        quiz.setDescription(description);

        List<Question> questions = new ArrayList<>();

        String option;
        int counter = 0;

        do {
            System.out.print("Enter question " + (counter + 1) + ": ");
            String questionText = scanner.nextLine();

            System.out.print("Enter the number of answers: ");
            int numAnswers = scanner.nextInt();
            scanner.nextLine();

            List<String> answers = new ArrayList<>();
            for (int j = 0; j < numAnswers; j++) {
                System.out.print("Enter answer " + (j + 1) + ": ");
                answers.add(scanner.nextLine());
            }

            System.out.print("Enter the correct answer: ");
            String correctAnswer = scanner.nextLine();

            while (!answers.contains(correctAnswer)) {
                System.out.println(CC.BRIGHT_RED + "Error: Incorrect answer" + CC.RESET);
                System.out.print("Enter the correct answer: ");
                correctAnswer = scanner.nextLine();
            }

            questions.add(new Question(0, questionText, answers, correctAnswer));

            counter++;

            System.out.println("Do you wish to continue? (y/n)");
            option = scanner.nextLine();

        } while (List.of("yes", "ye", "yea", "yeah", "y", "1").contains(option));

        quiz.setQuestions(questions);
        quiz.save();

        System.out.println("Quiz saved successfully!");
    }
}