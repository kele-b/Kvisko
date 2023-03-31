package com.example.kvisko.controllers;

import com.example.kvisko.Kvisko;
import com.example.kvisko.database.Question;
import com.example.kvisko.timer.Timer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeController {

    @FXML
    private Button listUsers;

    @FXML
    private Button startButton;

    @FXML
    private Pane quizPane;

    @FXML
    private TextArea question;

    @FXML
    private Button answer1;

    @FXML
    private Button answer2;

    @FXML
    private Button answer3;

    @FXML
    private Button answer4;

    @FXML
    private Label timeLeft;

    @FXML
    private Label time;

    private ArrayList<Question> questions;

    private int counter = 1;

    private String correctAnswer;

    private int numberOfCorrectedAnswers = 0;

    private Timer timer;

    public void listAllUsers(ActionEvent actionEvent) {
        Kvisko.databaseConnection.getUsers();

        try {
            Thread.sleep(500);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/kvisko/users.fxml"));
            fxmlLoader.load();
            UsersControllers usersControllers = fxmlLoader.getController();
            usersControllers.getTable();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void startQuiz(ActionEvent actionEvent) {
        timer = new Timer(timeLeft);
        timer.start();

        timeLeft.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("isteklo!")) {
                timer.setTimeIsUp(true);
                endOfQuiz(true);
            }
        });

        questions = Kvisko.getQuiz().getQuestions();
        renderQuestions();
        endOfQuiz(false);
    }

    private boolean isCorrectAnswer(Button answer) {
        return correctAnswer == answer.getText();
    }

    public void submitAnswer1(ActionEvent actionEvent) {
        if (isCorrectAnswer(answer1)) {
            timer.restart();
            numberOfCorrectedAnswers++;
            counter++;
            renderQuestions();
        } else {
            endOfQuiz(true);
        }
    }

    public void submitAnswer2(ActionEvent actionEvent) {
        if (isCorrectAnswer(answer2)) {
            timer.restart();
            numberOfCorrectedAnswers++;
            counter++;
            renderQuestions();
        } else {
            endOfQuiz(true);
        }
    }

    public void submitAnswer3(ActionEvent actionEvent) {
        if (isCorrectAnswer(answer3)) {
            timer.restart();
            numberOfCorrectedAnswers++;
            counter++;
            renderQuestions();
        } else {
            endOfQuiz(true);
        }
    }

    public void submitAnswer4(ActionEvent actionEvent) {
        if (isCorrectAnswer(answer4)) {
            timer.restart();
            numberOfCorrectedAnswers++;
            counter++;
            renderQuestions();
        } else {
            endOfQuiz(true);
        }
    }

    private void renderQuestions() {
        try {
            correctAnswer = questions.get(counter - 1).getCorrectAnswer();
            question.setText(counter + "." + questions.get(counter - 1).getQuestionText());

            List<String> answers = new ArrayList<>();
            answers.add(questions.get(counter - 1).getCorrectAnswer());
            answers.addAll(questions.get(counter - 1).getAnswers());
            Collections.shuffle(answers);

            answer1.setText(answers.get(0));
            answer2.setText(answers.get(1));
            answer3.setText(answers.get(2));
            answer4.setText(answers.get(3));
        } catch (IndexOutOfBoundsException e) {
            Platform.runLater(()->{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("ČESTITAMO!");
                alert.setContentText("Uspješno ste završili kviz!");
                alert.show();
            });
            endOfQuiz(true);
        }
    }

    private void endOfQuiz(Boolean end) {
        if (end) {
            Kvisko.getCurrentUser().setPoints(numberOfCorrectedAnswers);
            Kvisko.databaseConnection.savePoints(numberOfCorrectedAnswers);
            timer.setTimeEnd(true);
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setHeaderText("Pogrešan odgovor!");
                if (timer.isTimeIsUp()) {
                    alert.setHeaderText("Vrijeme je isteklo!");
                }
                alert.setContentText("Ukupno osvojenih bodova: " + numberOfCorrectedAnswers);
                alert.show();
                counter = 1;
                numberOfCorrectedAnswers = 0;
            });
        }
        quizPane.setVisible(!end);
        timeLeft.setVisible(!end);
        time.setVisible(!end);
        listUsers.setVisible(end);
        startButton.setVisible(end);
    }
}
