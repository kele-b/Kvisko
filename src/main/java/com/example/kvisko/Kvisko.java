package com.example.kvisko;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Kvisko extends Application {

    public static void main(String[] args) {
        launch(args);
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


        button.setOnAction(f -> {
            Timer timer1 = timer;
            if (!Timer.isStarted) {
                timer1.start();
                Timer.isStarted=true;
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
