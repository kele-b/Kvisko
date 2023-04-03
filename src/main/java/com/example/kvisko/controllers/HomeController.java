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
import javafx.scene.text.Text;

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

    @FXML
    private Button jokerButton;

    @FXML
    private Text kvisko;

    private int jokerCounter = 0;

    private ArrayList<Question> questions;

    private int counter = 1;

    private String correctAnswer;

    private int numberOfCorrectedAnswers = 0;

    private Timer timer;

    private boolean isSuccessfullyCompleted = false;

    public void listAllUsers(ActionEvent actionEvent) {
        synchronized (Kvisko.databaseConnection) {
            Kvisko.databaseConnection.getUsers();
            Kvisko.databaseConnection.notify();
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/kvisko/users.fxml"));
            fxmlLoader.load();
            UsersControllers usersControllers = fxmlLoader.getController();
            usersControllers.getTable();
        } catch (IOException  e) {
            throw new RuntimeException(e);
        }
    }

    public void startQuiz(ActionEvent actionEvent) {
        timer = new Timer(timeLeft);
        timer.start();
        timer.setHomeController(this);

        questions = Kvisko.getQuiz().getQuestions();
        renderQuestions();
        endOfQuiz(false);
    }

    private boolean isCorrectAnswer(Button answer) {
        return correctAnswer == answer.getText();
    }

    private void allJokersUsed(){
        if (jokerCounter == 3) {
            jokerButton.setDisable(true);
        } else {
            jokerButton.setDisable(false);
        }
    }

    public void submitAnswer1(ActionEvent actionEvent) {
        if (isCorrectAnswer(answer1)) {
            timer.restart();
            numberOfCorrectedAnswers++;
            counter++;
            renderQuestions();
            allJokersUsed();
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
            allJokersUsed();
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
            allJokersUsed();
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
            allJokersUsed();
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
            answer1.setDisable(false);
            answer2.setText(answers.get(1));
            answer2.setDisable(false);
            answer3.setText(answers.get(2));
            answer3.setDisable(false);
            answer4.setText(answers.get(3));
            answer4.setDisable(false);
        } catch (IndexOutOfBoundsException e) {
            isSuccessfullyCompleted = true;
            endOfQuiz(true);
        }
    }

    public void endOfQuiz(Boolean end) {
        if (end) {
            Kvisko.getCurrentUser().setPoints(numberOfCorrectedAnswers);
            synchronized (Kvisko.databaseConnection) {
                Kvisko.databaseConnection.savePoints(numberOfCorrectedAnswers);
                Kvisko.databaseConnection.notify();
            }
            timer.setTimeEnd(true);
            jokerCounter=0;
            jokerButton.setDisable(false);
            if (end && isSuccessfullyCompleted) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("info");
                    alert.setHeaderText("ČESTITAMO!");
                    alert.setContentText("Uspješno ste završili kviz!");
                    alert.show();
                    counter = 1;
                    numberOfCorrectedAnswers = 0;
                });
            } else {
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
        }
        quizPane.setVisible(!end);
        timeLeft.setVisible(!end);
        time.setVisible(!end);
        listUsers.setVisible(end);
        startButton.setVisible(end);
        kvisko.setVisible(end);
    }

    public void onJokerButtonAction(ActionEvent actionEvent) {
        List<Button> answers = new ArrayList<>();
        answers.add(answer1);
        answers.add(answer2);
        answers.add(answer3);
        answers.add(answer4);

        Collections.shuffle(answers);
        for (int i = 0; i < 2; i++) {
            if(isCorrectAnswer(answers.get(i)))
                answers.get(i+2).setDisable(true);
            else
                answers.get(i).setDisable(true);
        }
        jokerCounter++;
        jokerButton.setDisable(true);
    }
}
