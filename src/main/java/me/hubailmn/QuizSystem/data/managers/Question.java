package me.hubailmn.QuizSystem.data.managers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    int quizID;
    String question;
    List<String> answers;
    String correctAnswer;

}
