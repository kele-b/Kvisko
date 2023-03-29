package com.example.kvisko;

import com.example.kvisko.database.DatabaseConnection;
import com.example.kvisko.timer.Timer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Kvisko extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private DatabaseConnection databaseConnection = new DatabaseConnection();

    public DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }

    @Override
    public void init() {

        databaseConnection.start();
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent loginForm = FXMLLoader.load(getClass().getResource("login.fxml"));

        Parent registerForm = FXMLLoader.load(getClass().getResource("register.fxml"));

        VBox parent = new VBox(10);

        Label label = new Label("Welcome to KVISKO!");

        Button button = new Button("Click me");

        Timer timer = new Timer(label);

        button.setOnAction(f -> {
            Timer timer1 = timer;
            if (!Timer.isStarted) {
                timer1.start();
                Timer.isStarted = true;
            } else {
                timer1.restart();
            }
            System.out.println(databaseConnection.isAlive());
        });

        parent.getChildren().addAll(label, button);

        parent.setAlignment(Pos.CENTER);

        Scene loginScene = new Scene(loginForm, 700, 400);
        Scene registerScene = new Scene(registerForm, 700, 450);

        stage.setScene(registerScene);
        stage.setTitle("Kvisko");
        stage.setHeight(stage.getScene().getHeight());
        stage.setWidth(stage.getScene().getWidth());
        stage.setResizable(false);
        stage.show();


    }
}
