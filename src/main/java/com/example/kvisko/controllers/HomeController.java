package com.example.kvisko.controllers;

import com.example.kvisko.Kvisko;
import com.example.kvisko.database.Question;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class HomeController {

    public Button listUsers;

    public Button startButton;

    public Pane quizPane;

    public TextArea question;

    public Button answer1;

    public Button answer2;

    public Button answer3;

    public Button answer4;

    public Button nextQuestion;

    private ArrayList<Question> questions;

    private int counter=1;

    private String correctAnswer;

    public void listAllUsers(ActionEvent actionEvent) {
        System.out.println(Kvisko.getQuiz().getQuestions().get(1));
    }

    public void startQuiz(ActionEvent actionEvent) {
        questions = Kvisko.getQuiz().getQuestions();
        correctAnswer = questions.get(0).getCorrectAnswer();
        question.setText(counter+"."+questions.get(0).getQuestionText());
        answer1.setText(questions.get(0).getCorrectAnswer());
        answer2.setText(questions.get(0).getAnswers().get(0));
        answer3.setText(questions.get(0).getAnswers().get(1));
        answer4.setText(questions.get(0).getAnswers().get(2));
        quizPane.setVisible(true);
        listUsers.setVisible(false);
        startButton.setVisible(false);
        nextQuestion.setVisible(true);
    }
}
