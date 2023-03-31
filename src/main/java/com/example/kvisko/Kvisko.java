package com.example.kvisko;

import com.example.kvisko.database.DatabaseConnection;
import com.example.kvisko.database.User;
import com.example.kvisko.quiz.Quiz;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Kvisko extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static DatabaseConnection databaseConnection = new DatabaseConnection();

    public static Parent home;

    public static Parent registerForm;

    public static Parent loginForm;

    private static User currentUser;

    private static Quiz quiz;

    public static Quiz getQuiz() {
        return quiz;
    }

    public static void setQuiz(Quiz quiz) {
        Kvisko.quiz = quiz;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Kvisko.currentUser = currentUser;
    }

    @Override
    public void init() {
        try {
            home=FXMLLoader.load(getClass().getResource("home.fxml"));
            registerForm = FXMLLoader.load(getClass().getResource("register.fxml"));
            loginForm= FXMLLoader.load(getClass().getResource("login.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        databaseConnection.start();
    }

    @Override
    public void start(Stage stage) throws Exception {

//        VBox parent = new VBox(10);
//
//        Label label = new Label("Welcome to KVISKO!");
//
//        Button button = new Button("Click me");
//
//        Timer timer = new Timer(label);
//
//        button.setOnAction(f -> {
//            Timer timer1 = timer;
//            if (!Timer.isStarted) {
//                timer1.start();
//                Timer.isStarted = true;
//            } else {
//                timer1.restart();
//            }
//            System.out.println(databaseConnection.isAlive());
//        });
//
//        parent.getChildren().addAll(label, button);
//
//        parent.setAlignment(Pos.CENTER);

        Scene loginScene = new Scene(loginForm, 620, 400);
        stage.setScene(loginScene);
        stage.setTitle("Kvisko");
        stage.setHeight(stage.getScene().getHeight());
        stage.setWidth(stage.getScene().getWidth());
        stage.setResizable(false);
        stage.show();

    }
}
