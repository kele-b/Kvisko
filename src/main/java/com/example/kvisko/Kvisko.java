package com.example.kvisko;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Kvisko extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        //Establishing a connection with the database
        String url = "jdbc:mysql://localhost:3306/mydatabase?createDatabaseIfNotExist=true";
        String username = "root";
        String password = "admin123";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Kvisko");
        stage.setHeight(600);
        stage.setWidth(800);
        stage.setResizable(false);


        VBox parent = new VBox(10);

        Label label = new Label("Welcome to KVISKO!");

        Button button = new Button("Click me");

        Timer timer = new Timer(label);
        List<Question> questions = ReadXMLFile.getQuestions();

        button.setOnAction(f -> {
            for (int i = 0; i < 5; i++) {
                System.out.println(questions.get(i));
            }
            Timer timer1 = timer;
            if (!Timer.isStarted) {
                timer1.start();
                Timer.isStarted = true;
            } else {
                timer1.restart();
            }

        });

        parent.getChildren().addAll(label, button);

        parent.setAlignment(Pos.CENTER);


        Scene scene = new Scene(parent);
        stage.setScene(scene);

        stage.show();


    }
}
